package ui.users;

import ui.drone.*;
import ui.menu.ShowTextUI;
import utils.MenuItem;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

public class DroneTechUI implements Runnable {

    public DroneTechUI() {

    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create Drone Model", new CreateDroneModelUI()));
        options.add(new MenuItem("Add Drone to the Inventory", new AddDroneInventoryUI()));
        options.add(new MenuItem("Remove Drone from the Inventory", new RemoveDroneInventoryUI()));
        options.add(new MenuItem("List Drone in the Inventory", new ListActiveDronesUI()));
        options.add(new MenuItem("Add Maintenance Type", new AddMaintenanceTypeUI()));
        options.add(new MenuItem("List Maintenance Type", new ShowTextUI("Not implemented yet.")));
        options.add(new MenuItem("Edit Maintenance Type", new ShowTextUI("Not implemented yet.")));

        int option = 0;

        do {
            String menu = "\n╔═══════════════" + ANSI_BRIGHT_WHITE + " DRONE TECH MENU " + ANSI_RESET + "═════════════════╗";
            option = Utils.showAndSelectIndexBigger(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
