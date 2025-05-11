package ui;

import controller.ListPublicFiguresController;
import domain.entity.Figure;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class ListPublicFiguresUI implements Runnable {
    private final ListPublicFiguresController controller = new ListPublicFiguresController();

    @Override
    public void run() {
        Utils.printCenteredTitle("List Public Figures");

        boolean page = Utils.confirm("Do you want to select a page number? (y/n)");
        int newpage = page ? Utils.readIntegerFromConsole("Enter the page number: ") : 1;

        Utils.dropLines(1);

        boolean pageSize = Utils.confirm("Do you want to select a page size? (y/n)");
        int newPageSize = pageSize ? Utils.readIntegerFromConsole("Enter the page size: ") : 20;

        Utils.dropLines(2);


        Optional<List<Figure>> allPublicFigures = controller.listPublicFigures();
        if (allPublicFigures.isPresent()) {
            Utils.showPagedElementListByStartingPage(allPublicFigures.get(), newpage, newPageSize, "List Public Figures");

            Utils.dropLines(1);
            System.out.println("....... Paused to read press ENTER key to continue ........");
            new java.util.Scanner(System.in).nextLine();
        } else {
            Utils.printFailMessage("Error: No public figures found!");
        }
    }
}
