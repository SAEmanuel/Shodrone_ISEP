package ui.showproposal;

import controller.service.ServiceForValidSequenceFiguresForShow;
import controller.showproposal.AddFiguresToProposalController;
import domain.entity.Figure;
import domain.entity.ShowProposal;
import utils.Utils;

import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.*;

/**
 * UI class responsible for interacting with the user to add new Figures
 * to an existing {@link ShowProposal}.
 *
 * <p>This interface allows the user to:
 * <ul>
 *   <li>See all current proposals</li>
 *   <li>Select a specific proposal</li>
 *   <li>Review the existing figures in the proposal</li>
 *   <li>Add new figures from a valid list</li>
 *   <li>Confirm and save the updated proposal</li>
 * </ul>
 *
 * <p>The logic is delegated to {@link AddFiguresToProposalController} and
 * {@link ServiceForValidSequenceFiguresForShow}.
 *
 * @author Catarina
 */
public class AddFiguresToProposalUI implements Runnable {
    /** Controller for adding figures to a proposal. */
    private final AddFiguresToProposalController controller;

    /**
     * Constructs the UI instance and initializes the controller.
     */
    public AddFiguresToProposalUI() {
        controller = new AddFiguresToProposalController();
    }

    /**
     * Entry point to the UI. Interacts with the user to:
     * <ol>
     *   <li>Select a proposal</li>
     *   <li>View existing figures</li>
     *   <li>Choose whether to add more</li>
     *   <li>Select new figures to add</li>
     *   <li>Persist the changes</li>
     * </ol>
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("ADD FIGURE TO SHOW PROPOSAL");

        try {
            // Step 1: Show and select proposal
            Utils.printCenteredSubtitle("Show Proposal Information");
            List<ShowProposal> listOfProposals = controller.getAllProposals();
            int selectedProposalIndex = Utils.showAndSelectIndexCustomOptions(listOfProposals, "Select one Show Proposal");
            ShowProposal selectedProposal = listOfProposals.get(selectedProposalIndex);
            Utils.printSuccessMessage("\nSelected Proposal with ID : " + selectedProposal.identity());

            // Step 2: Show existing figures
            Utils.printCenteredSubtitle("Figures of Selected Proposal");
            List<Figure> listFigures = selectedProposal.getSequenceFigues();

            if(listFigures.isEmpty()){
                Utils.printAlterMessage("No Figures was found in the selected proposal.");
            } else {
                Utils.printAlterMessage("A total of " + listFigures.size() + " Figures were found in the selected proposal:");
                for (Figure figure : listFigures) {
                    System.out.printf("    %s%s %s %s%n", ANSI_BRIGHT_BLACK, "•", ANSI_RESET, figure);
                }
                Utils.dropLines(1);
            }

            // Step 3: Confirm addition of new figures
            boolean continueAdd = Utils.confirm(ANSI_BOLD + "Do you wish to add 'Figures' [y/n]" + ANSI_RESET);
            if(!continueAdd){
                Utils.exitImmediatelyWithThrow("\n✖️ Exiting add Figures to Show Proposal UI.");
            }

            // Step 4: Get new figures and persist update
            Utils.printCenteredSubtitle("Selection of New Figures");
            List<Figure> sequenceOfFigures = ServiceForValidSequenceFiguresForShow
                    .getListFiguresUIWithRepetitions(selectedProposal.getShowRequest().getCostumer(), listFigures);
            Optional<ShowProposal> optionalResult = controller.saveNewImagesInProposal(selectedProposal, sequenceOfFigures);

            // Step 5: Feedback to user
            if(optionalResult.isEmpty()){
                Utils.printFailMessage("\n✖️ Something went wrong saving the Show Proposal!");
            } else {
                Utils.printSuccessMessage("\n✔️ Figures added successfully to proposal!");
                for (Figure figure : optionalResult.get().getSequenceFigues()) {
                    System.out.println(figure.toString());
                }
            }

            Utils.waitForUser();

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }
}
