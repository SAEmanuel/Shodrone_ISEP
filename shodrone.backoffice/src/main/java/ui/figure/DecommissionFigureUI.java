package ui.figure;

import controller.figure.DecommissionFigureController;
import controller.figure.GetAllActiveFiguresController;
import domain.entity.Figure;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * User Interface class responsible for decommissioning an active Figure.
 * Implements Runnable to be executed as a standalone UI operation or in a thread.
 * Retrieves the list of active figures, allows user to select one,
 * and delegates the decommission operation to the controller.
 */
public class DecommissionFigureUI implements Runnable {
    private final DecommissionFigureController controller = new DecommissionFigureController();
    private final GetAllActiveFiguresController getAllFiguresController = new GetAllActiveFiguresController();
    private static final int EXIT = -1;
    /**
     * Runs the interactive console UI flow to decommission a Figure.
     * Displays active figures, prompts the user to select one,
     * then attempts to decommission the selected figure.
     * Provides success or failure feedback based on the operation result.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("Decommission Figure");

        Optional<Figure> result = Optional.empty();

        Optional<List<Figure>> listOfFigures = getAllFiguresController.getAllActiveFigures();
        if (listOfFigures.isPresent() && !listOfFigures.get().isEmpty()) {
            int index = Utils.showAndSelectIndexPartially(listOfFigures.get(), "Figure");

            if (index == EXIT) {
                Utils.printFailMessage("No figure selected...");
                return;
            }

            Optional<Figure> figure = Optional.ofNullable(listOfFigures.get().get(index));

            result = controller.editChosenFigure(figure.orElse(null));
        }else{
            Utils.printFailMessage("Error: No figure found to decommission! Added a figure first and try again!");
            return;
        }

        if (result.isPresent() && !result.isEmpty()) {
            Utils.printSuccessMessage("Decommission Figure successfully");
        } else {
            Utils.printFailMessage("Error: Decommitting failed! Figure already decommissioned!");
        }
    }
}
