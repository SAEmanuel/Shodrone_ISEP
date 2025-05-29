package ui;


import utils.MenuItem;
import ui.menu.ShowTextUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;


public class RepresentativeUI implements Runnable {
    public RepresentativeUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Analyse Proposal", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Approve/Reject Proposal", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Consult Shows Agenda", new ShowTextUI("Not implemented yet.")));

        int option = 0;
        do {
            String menu = "\n╔═════════" + ANSI_BRIGHT_WHITE + " REPRESENTATIVE MENU " + ANSI_RESET + "══════════╗";
            option = Utils.showAndSelectIndex(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}