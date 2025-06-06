package controller.showproposal;

import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;

public class VerifyShowProposalStatusController {

    public VerifyShowProposalStatusController() {
    }

    public boolean wasShowProposalSent(ShowProposal showProposal) {
        return showProposal.getStatus().equals(ShowProposalStatus.CUSTOMER_APPROVED);
    }
}
