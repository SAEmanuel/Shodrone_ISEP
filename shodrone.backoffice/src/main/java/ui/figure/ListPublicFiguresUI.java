package ui.figure;

import controller.figure.ListPublicFiguresController;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * User Interface class responsible for listing public Figures.
 * Implements Runnable for standalone execution or threading.
 * Prompts the user for pagination preferences (page number and page size),
 * retrieves public figures through the controller,
 * and displays them in a paginated format.
 */
public class ListPublicFiguresUI implements Runnable {
    private final ListPublicFiguresController controller = new ListPublicFiguresController();
    private static final int EXIT = -1;

    /**
     * Runs the UI flow to display all public & active figures.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("All Active & Public Figures");

        Optional<List<Figure>> allPublicFigures = controller.listPublicFigures();

        Utils.printCenteredSubtitleV2("Figure");
        if (allPublicFigures.isEmpty()) {
            Utils.printFailMessage("No figures in the system yet...");
        } else {
            List<Figure> allCategories = allPublicFigures.get();
            Utils.showListPartially(allCategories, "");
        }
    }
}
