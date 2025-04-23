package ui;


import ui.menu.MenuItem;
import ui.menu.ShowTextUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;


public class CRMCollaboratorUI implements Runnable {
    public CRMCollaboratorUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Manage Users", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Option 3", new ShowTextUI("You have chosen Option 3.")));
        options.add(new MenuItem("Option 4", new ShowTextUI("You have chosen Option 4.")));

        int option = 0;
        do {
            String menu = "\n╔════════"+ANSI_BRIGHT_WHITE+" CRM COLLABORATOR MENU "+ANSI_RESET+"═════════╗";
            option = Utils.showAndSelectIndex(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}