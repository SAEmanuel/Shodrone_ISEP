package controller.show;

import domain.entity.Show;
import domain.entity.ShowProposal;
import domain.entity.ShowRequest;
import domain.valueObjects.ShowStatus;
import persistence.RepositoryProvider;
import persistence.ShowRepository;
import persistence.ShowRequestRepository;

import java.util.Optional;

public class CreateShowController {

    private final ShowRepository showRepository = RepositoryProvider.showRepository();
    private final ShowRequestRepository showRequestRepository = RepositoryProvider.showRequestRepository();

    public Optional<Show> createShowFromProposal(ShowProposal proposal) {
        Optional<ShowRequest> optionalShowRequest = showRequestRepository.findById(proposal.getShowRequest().identity());

        if (optionalShowRequest.isEmpty()) {
            throw new IllegalStateException("Could not find the ShowRequest for the given ShowProposal.");
        }

        ShowRequest showRequest = optionalShowRequest.get();
        Long customerID = showRequest.getCostumer().identity();

        Optional<Show> duplicateShow = showRepository.findDuplicateShow(proposal.getLocation(), proposal.getShowDate(), customerID);

        if (duplicateShow.isPresent()) {
            throw new IllegalStateException("❌ A Show already exists for this customer in this location and date.");
        }

        ShowProposal managedProposal = RepositoryProvider.showProposalRepository()
                .findByID(proposal.identity())
                .orElseThrow(() -> new IllegalStateException("Proposal not found anymore"));

        Show show = new Show(
                managedProposal,
                proposal.getLocation(),
                proposal.getShowDate(),
                proposal.getNumberOfDrones(),
                proposal.getShowDuration(),
                ShowStatus.PLANNED,
                customerID
        );

        show.getSequenceFigures().addAll(managedProposal.getSequenceFigues());

        return showRepository.saveInStore(show);
    }

}


