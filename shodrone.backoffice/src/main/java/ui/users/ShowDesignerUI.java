package ui.users;


import ui.category.AddFigureCategoryUI;
import ui.category.ChangeFigureCategoryStatusUI;
import ui.category.EditFigureCategoryUI;
import ui.category.ListAllFigureCategoriesUI;
import ui.figure.AddFigureUI;
import ui.figure.DecommissionFigureUI;
import ui.figure.ListPublicFiguresUI;
import ui.figure.SearchFigureUI;
import utils.MenuItem;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

/**
 * UI class representing the Show Designer menu.
 * <p>
 * Provides a console-based menu interface for show designers to manage figure categories
 * and figures, including adding, editing, listing, activating/deactivating categories,
 * searching, adding new figures, and decommissioning figures.
 * </p>
 */
public class ShowDesignerUI implements Runnable {

    /**
     * Default constructor for ShowDesignerUI.
     * No special initialization needed here.
     */
    public ShowDesignerUI() {
    }

    /**
     * Displays the Show Designer menu and handles user interaction.
     * <p>
     * The menu provides the following options:
     * <ul>
     *     <li>Add a new Figure Category</li>
     *     <li>Edit an existing Figure Category</li>
     *     <li>List all Figure Categories</li>
     *     <li>Activate or Inactivate a Figure Category</li>
     *     <li>List all active and public Figures</li>
     *     <li>Search for Figures</li>
     *     <li>Add new Figures</li>
     *     <li>Decommission a Figure</li>
     * </ul>
     * The menu will keep displaying until the user chooses to exit by selecting an invalid option or canceling.
     * Selecting an option executes the corresponding UI action.
     * </p>
     */
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Add Figure Category", new AddFigureCategoryUI()));
        options.add(new MenuItem("Edit Figure Category", new EditFigureCategoryUI()));
        options.add(new MenuItem("List Figure Categories", new ListAllFigureCategoriesUI()));
        options.add(new MenuItem("Inactivate/Activate a Figure Category", new ChangeFigureCategoryStatusUI()));
        options.add(new MenuItem("List All Active & Public Figures", new ListPublicFiguresUI()) );
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