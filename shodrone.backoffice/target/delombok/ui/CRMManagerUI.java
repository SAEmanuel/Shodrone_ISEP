package ui;


import ui.menu.MenuItem;
import ui.menu.ShowTextUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;


public class CRMManagerUI implements Runnable {
    public CRMManagerUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("List 'Show Request' of Clients", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Change Client Status", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Manage Proposal Templates", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Manage Clients", new ShowTextUI("Not implemented yet.")));

        int option = 0;
        do {
            String menu = "\n╔══════════ "+ANSI_BRIGHT_WHITE+" CRM MANAGER MENU "+ANSI_RESET+" ══════════╗";
            option = Utils.showAndSelectIndex(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}