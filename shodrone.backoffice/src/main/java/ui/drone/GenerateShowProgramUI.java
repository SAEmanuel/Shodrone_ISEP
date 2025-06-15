package ui.drone;

import controller.drone.GenerateShowProgramController;
import domain.entity.ShowProposal;
import persistence.RepositoryProvider;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class GenerateShowProgramUI implements Runnable {

    private final GenerateShowProgramController controller = new GenerateShowProgramController();

    @Override
    public void run() {

        Optional<List<ShowProposal>> proposals = RepositoryProvider.showProposalRepository().getCreatedProposals();

        if (proposals.isEmpty()) {
            System.out.println("No ShowProposals available.");
            return;
        }

        List<ShowProposal> showProposals = proposals.get();

        int selectedIndex = Utils.showAndSelectIndexPartially(showProposals, "Select a ShowProposal:");

        if (selectedIndex < 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        ShowProposal selectedProposal = showProposals.get(selectedIndex);

        Optional<ShowProposal> result = controller.generateProgramsForShow(selectedProposal);

        if (result.isEmpty()){
            Utils.printFailMessage("Failed to generate program.");
        } else {
            Utils.printSuccessMessage("Successfully generated program.");
        }
    }
}
