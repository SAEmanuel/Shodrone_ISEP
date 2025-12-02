package controller.showproposal;

import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

public class ListShowProposalController {

    public ListShowProposalController() {
    }

    public List<ShowProposal> getAllProposals(){
        Optional<List<ShowProposal>> optionalResult = RepositoryProvider.showProposalRepository().getAllProposals();
        if (optionalResult.isEmpty()){
            throw new RuntimeException("No Show Proposal's were found on the system.");
        }

        return optionalResult.get();
    }

    public List<ShowProposal> getAllSentAcceptedProposals(){
        Optional<List<ShowProposal>> optionalResult = RepositoryProvider.showProposalRepository().getUpdatedProposals();
        if (optionalResult.isEmpty()){
            throw new RuntimeException("No Show Proposal's were found on the system.");
        }

        if (optionalResult.get().stream()
                .filter(p -> p.getStatus().equals(ShowProposalStatus.CUSTOMER_APPROVED))
                .toList().isEmpty()){
            throw new RuntimeException("No Show Proposal's in CUSTOMER APPROVED status were found on the system.");
        }

        return optionalResult.get().stream()
                .filter(p -> p.getStatus().equals(ShowProposalStatus.CUSTOMER_APPROVED))
                .toList();
    }
}
