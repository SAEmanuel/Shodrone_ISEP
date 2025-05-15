package ui.figure;

import controller.figure.DecommissionFigureController;
import controller.figure.GetAllActiveFiguresController;
import domain.entity.Figure;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class DecommissionFigureUI implements Runnable {
    private final DecommissionFigureController controller = new DecommissionFigureController();
    private final GetAllActiveFiguresController getAllFiguresController = new GetAllActiveFiguresController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Decommission Figure");

        Optional<Figure> result = Optional.empty();

        Optional<List<Figure>> listOfFigures = getAllFiguresController.getAllActiveFigures();
        if (listOfFigures.isPresent() && !listOfFigures.get().isEmpty()) {
            Optional<Figure> figure = (Optional<Figure>) Utils.showAndSelectObjectFromListStartingOnOne((Optional<List<?>>) (Optional<?>) listOfFigures, "Figure");

            result = controller.editChosenFigure(figure.get());
        }else{
            Utils.printFailMessage("Error: No figure found to decommission! Added a figure first and try again!");
            return;
        }

        if ( result.isPresent() && !result.isEmpty() ) {
            Utils.printSuccessMessage("Decommission Figure successfully");
        } else {
            Utils.printFailMessage("Error: Decommitting failed! Figure already decommissioned!");
        }
    }
}
