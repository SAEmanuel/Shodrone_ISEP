package ui.drone;

import controller.drone.AddMaintenanceTypeController;
import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import utils.Utils;

import java.util.Optional;

/**
 * User interface for adding a new maintenance type.
 */
public class AddMaintenanceTypeUI implements Runnable {

    private final AddMaintenanceTypeController controller = new AddMaintenanceTypeController();

    /**
     * Executes the maintenance type creation process.
     * Prompts the user for a name, optionally a description, and creates the maintenance type.
     */
    @Override
    public void run() {
        Utils.printDroneCenteredTitle("Add Maintenance Type");

        Utils.printCenteredSubtitleV2("Maintenance Type Name");
        Utils.showDroneNameRules();
        MaintenanceTypeName maintenanceTypeName = Utils.rePromptWhileInvalid(
                "Enter the Maintenance Type Name", MaintenanceTypeName::new
        );

        Optional<MaintenanceType> result;
        Description description = null;

        boolean hasDescription = Utils.confirm("Do you want to add a description? (y/n)");

        if (hasDescription) {
            Utils.printCenteredSubtitleV2("Maintenance Type Description");
            Utils.showDescriptionRules();
            description = Utils.rePromptWhileInvalid(
                    "Enter the Maintenance Type Description", Description::new
            );
        } else {
            Utils.printAlterMessage("Description skipped...");
        }

        if (hasDescription) {
            result = controller.createMaintenanceType(maintenanceTypeName, description);
        } else {
            result = controller.createMaintenanceTypeWithoutDescription(maintenanceTypeName);
        }

        System.out.println();

        if (result.isPresent()) {
            Utils.printSuccessMessage("Maintenance Type created successfully!");
        } else {
            Utils.printFailMessage("A Maintenance Type with that name already exists!");
        }
    }
}
