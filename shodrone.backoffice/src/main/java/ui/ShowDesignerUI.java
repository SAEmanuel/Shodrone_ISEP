package ui;


import ui.menu.MenuItem;
import ui.menu.ShowTextUI;
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
        options.add(new MenuItem("Option 4", new ShowTextUI("You have chosen Option 4.")));

        int option = 0;
        do {
            String menu = "\n╔══════════"+ANSI_BRIGHT_WHITE+" SHOW DESIGNER MENU "+ANSI_RESET+"══════════╗";
            option = Utils.showAndSelectIndex(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}