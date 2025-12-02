package ui.showproposal;

import controller.show.AcceptProposalAndCreateShowController;
import controller.showproposal.ListShowProposalController;
import domain.entity.Show;
import domain.entity.ShowProposal;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class AcceptShowProposalUI implements Runnable {

    public AcceptProposalAndCreateShowController acceptProposalAndCreateShowController = new AcceptProposalAndCreateShowController();
    public ListShowProposalController listShowProposalController = new ListShowProposalController();

    @Override
    public void run() {
        Utils.printCenteredTitle("ACCEPT SHOW PROPOSAL");

        try {
            Utils.printCenteredSubtitle("Show Proposal Information");
            List<ShowProposal> showProposalList = listShowProposalController.getAllSentAcceptedProposals();
            int selectedProposalIndex = Utils.showAndSelectIndexWithoutBox(showProposalList, "Select one Show Proposal");

            if (selectedProposalIndex == -1) {
                return;
            }

            ShowProposal selectedProposal = showProposalList.get(selectedProposalIndex);
            Optional<Show> show = acceptProposalAndCreateShowController.acceptProposalAndCreateShow(selectedProposal);
            Utils.printSuccessMessage("\n✔️ Show Proposal Accepted Successfully!");
            Utils.printSuccessMessage("\n✔️ Show created Successfully! ID: " + show.get().identity());

            Utils.waitForUser();

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }
}
