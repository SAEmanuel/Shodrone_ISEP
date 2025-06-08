package controller.show;

import controller.showproposal.VerifyShowProposalStatusController;
import domain.entity.Show;
import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;
import persistence.ShowRepository;

import java.util.Optional;

public class AcceptProposalAndCreateShowController {

    private final VerifyShowProposalStatusController verifyShowProposalStatusController = new VerifyShowProposalStatusController();
    private final CreateShowController createShowController = new CreateShowController();
    private final ShowProposalRepository showProposalRepository = RepositoryProvider.showProposalRepository();

    public Optional<Show> acceptProposalAndCreateShow(ShowProposal proposal) {

        if (!verifyShowProposalStatusController.wasShowProposalSent(proposal)) {
            throw new IllegalStateException("❌ Cannot accept the proposal because it was not sent.");
        }

        proposal.setStatus(ShowProposalStatus.COLLABORATOR_APPROVED);

        Optional<Show> show = createShowController.createShowFromProposal(proposal);

        if (show.isPresent()) {
            showProposalRepository.updateInStoreProposal(proposal);
            return show;
        } else {
            throw new IllegalStateException("❌ Show creation failed. Proposal was not persisted as accepted.");
        }
    }
}
