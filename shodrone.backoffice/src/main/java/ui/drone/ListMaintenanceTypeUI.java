package ui.drone;

import controller.drone.GetMaintenanceTypeController;
import domain.entity.MaintenanceType;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * User interface for listing all maintenance types in the system.
 */
public class ListMaintenanceTypeUI implements Runnable {

    private final GetMaintenanceTypeController controller = new GetMaintenanceTypeController();

    /**
     * Executes the process of retrieving and displaying all maintenance types.
     * Shows an error message if no types are found.
     */
    @Override
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
