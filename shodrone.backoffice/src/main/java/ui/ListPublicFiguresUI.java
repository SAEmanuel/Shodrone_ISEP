package ui;

import controller.ListPublicFiguresController;
import domain.entity.Figure;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class ListPublicFiguresUI implements Runnable {
    private final ListPublicFiguresController listPublicFiguresController = new ListPublicFiguresController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Not implemented yet");

        Utils.printCenteredTitle("List Public Figures");

        List<Figure> allPublicFigures = listPublicFiguresController.listPublicFigures();
        if (!allPublicFigures.isEmpty()) {

            boolean page = Utils.confirm("Do you want to select a page number? (y/n)");
            int newpage = page ? Utils.readIntegerFromConsole("Enter the page number: ") : 0;

            boolean pageSize = Utils.confirm("Do you want to select a page size? (y/n)");
            int newPageSize = pageSize ? Utils.readIntegerFromConsole("Enter the page size: ") : 20;

            Utils.showPagedElementList(allPublicFigures, newPageSize, "List Public Figures");

        } else {
            Utils.printFailMessage("No figures in the system yet.");
        }
    }
}
