package ui.figure;

import controller.figure.ListPublicFiguresController;
import domain.entity.Figure;
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

    /**
     * Runs the interactive console UI to list public figures.
     * Asks the user if they want to specify page number and size,
     * validates inputs, retrieves the list of public figures,
     * then shows the figures with pagination and waits for user input to finish.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("List Public Figures");

        boolean page = Utils.confirm("Do you want to select a page number? (y/n)");
        int newpage = 0;
        while(newpage <= 0) {
            newpage = page ? Utils.readIntegerFromConsole("Enter the page number: ") : 1;
            if(newpage <= 0){
                Utils.printFailMessage("Page number below 1!");
            }
        }
        Utils.dropLines(1);

        boolean pageSize = Utils.confirm("Do you want to select a page size? (y/n)");
        int newPageSize = 0;
        while(newPageSize <= 0) {
            newPageSize = pageSize ? Utils.readIntegerFromConsole("Enter the page size: ") : 20;
            if(newPageSize <= 0){
                Utils.printFailMessage("Page number below 1!");
            }
    }
        Utils.dropLines(2);


        Optional<List<Figure>> allPublicFigures = controller.listPublicFigures();
        if (allPublicFigures.isPresent()) {
            Utils.showPagedElementListByStartingPage(allPublicFigures.get(), newpage, newPageSize, "List Public Figures");

            Utils.dropLines(1);
            Utils.waitForUser();
        } else {
            Utils.printFailMessage("Error: No public figures found!");
        }
    }
}
