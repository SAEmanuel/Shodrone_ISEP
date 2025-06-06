package ui.showproposal;

import controller.showproposal.AcceptShowProposalController;
import controller.showproposal.ListShowProposalController;
import domain.entity.ShowProposal;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class AcceptShowProposalUI implements Runnable{

    public AcceptShowProposalController acceptShowProposalController = new AcceptShowProposalController();
    public ListShowProposalController listShowProposalController = new ListShowProposalController();

    @Override
    public void run() {
        Utils.printCenteredTitle("ACCEPT SHOW PROPOSAL");

        try {
            Utils.printCenteredSubtitle("Show Proposal Information");
            List<ShowProposal> showProposalList = listShowProposalController.getAllSentAcceptedProposals();
            int selectedProposalIndex = Utils.showAndSelectIndex(showProposalList, "Select one Show Proposal");

            if (selectedProposalIndex == -1) {
                return;
            }

            ShowProposal selectedProposal = showProposalList.get(selectedProposalIndex);

            Optional<ShowProposal> result = acceptShowProposalController.markShowProposalAsAccepted(selectedProposal);

            if (result.isEmpty()) {
                Utils.printFailMessage("\n✖️ Something went wrong accepting the Show Proposal!");
            } else {
                Utils.printSuccessMessage("\n✔️ Show Proposal Accepted Successfully!");
            }

            Utils.waitForUser();

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }
}
