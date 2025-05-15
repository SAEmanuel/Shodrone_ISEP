package ui.users;


import ui.category.AddFigureCategoryUI;
import ui.category.ChangeFigureCategoryStatusUI;
import ui.category.EditFigureCategoryUI;
import ui.category.ListAllFigureCategoriesUI;
import ui.figure.AddFigureUI;
import ui.figure.DecommissionFigureUI;
import ui.figure.ListPublicFiguresUI;
import ui.figure.SearchFigureUI;
import ui.menu.MenuItem;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;


public class ShowDesignerUI implements Runnable {
    public ShowDesignerUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Add Figure Category", new AddFigureCategoryUI()));
        options.add(new MenuItem("Edit Figure Category", new EditFigureCategoryUI()));
        options.add(new MenuItem("List Figure Categories", new ListAllFigureCategoriesUI()));
        options.add(new MenuItem("Inactivate/Activate a Figure Category", new ChangeFigureCategoryStatusUI()));
        options.add(new MenuItem("List All Public Figures", new ListPublicFiguresUI()) );
        options.add(new MenuItem("Search Figures", new SearchFigureUI()) );
        options.add(new MenuItem("Add Figures", new AddFigureUI()) );
        options.add(new MenuItem("Decommission Figure", new DecommissionFigureUI()) );

        int option = 0;
        do {
            String menu = "\n╔═══════════════" + ANSI_BRIGHT_WHITE + " SHOW DESIGNER MENU " + ANSI_RESET + "══════════════╗";
            option = Utils.showAndSelectIndexBigger(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}