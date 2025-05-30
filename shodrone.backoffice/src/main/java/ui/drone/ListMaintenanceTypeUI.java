package ui.drone;

import controller.drone.GetMaintenanceTypeController;
import domain.entity.MaintenanceType;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class ListMaintenanceTypeUI implements Runnable {

    private final GetMaintenanceTypeController controller = new GetMaintenanceTypeController();

    public void run() {
        try {
            Utils.printDroneCenteredTitle("List Maintenance Types");

            Optional<List<MaintenanceType>> maintenanceTypes = controller.getAllMaintenanceTypes();

            if (maintenanceTypes.isEmpty()) {
                Utils.printFailMessage("No maintenance types in the system yet...");
                Utils.dropLines(1);
                return;
            } else {
                Utils.showListPartially(maintenanceTypes.get(), "");
            }

        } catch (RuntimeException ignored) {}
    }
}