package authz.UI;

import authz.UI.menu.MenuItem;
import authz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static authz.more.ColorfulOutput.*;

public class ProductionManagerUI implements Runnable {

    public ProductionManagerUI() {
    }


    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
//        options.add(new MenuItem("| Extras UI", new ExtrasUI()));
//        options.add(new MenuItem("| Database UI", new DataBaseUI()));
//        options.add(new MenuItem("| PERT/CPM UI", new PERTCPMUI()));
//
//        options.add(new MenuItem("Process Orders", new ProcessOrdersUI()));
//        //options.add(new MenuItem("Simulation", new SimulatorUI()));




        int option = 0;
        do {
            System.out.println("\n\n╔════════════════════════════════════════╗");
            option = Utils.showAndSelectIndex(options, "║" + ANSI_BRIGHT_WHITE + "        PRODUCTION MANAGER MENU   " + ANSI_RESET + "      ║");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}

