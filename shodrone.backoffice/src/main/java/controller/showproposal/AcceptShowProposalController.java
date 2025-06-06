package controller.showproposal;

import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import persistence.RepositoryProvider;

import java.util.Optional;

public class AcceptShowProposalController {

    public VerifyShowProposalStatusController showProposalStatusController = new VerifyShowProposalStatusController();

    public AcceptShowProposalController(){
    }

    public Optional<ShowProposal> markShowProposalAsAccepted(ShowProposal showProposal) {
        if (!showProposalStatusController.wasShowProposalSent(showProposal)) {
            throw new IllegalStateException("❌ Cannot accept the proposal because it was not sent.");
        }

        showProposal.setStatus(ShowProposalStatus.COLLABORATOR_APPROVED);
        return RepositoryProvider.showProposalRepository().updateInStoreProposal(showProposal);
    }
}
