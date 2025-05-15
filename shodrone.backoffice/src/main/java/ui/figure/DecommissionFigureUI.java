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
            Optional<Figure> figure = (Optional<Figure>) Utils.showAndSelectObjectFromList((Optional<List<?>>) (Optional<?>) listOfFigures, "Figure");
            if (figure.isPresent())
                result = controller.editChosenFigure(figure.get());
        }

        if (result.isPresent()) {
            Utils.printSuccessMessage("Decommission Figure successfully");
        } else {
            Utils.printFailMessage("Error: may haven't found any figure to decommission or decommitting failed!");
        }
    }
}
