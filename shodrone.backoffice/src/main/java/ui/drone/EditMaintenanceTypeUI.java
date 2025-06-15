package ui.drone;

import controller.drone.EditMaintenanceTypeController;
import controller.drone.GetMaintenanceTypeController;
import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * User interface for editing an existing maintenance type.
 */
public class EditMaintenanceTypeUI implements Runnable {

    private final GetMaintenanceTypeController getMaintenanceTypeController = new GetMaintenanceTypeController();
    private final EditMaintenanceTypeController editMaintenanceTypeController = new EditMaintenanceTypeController();

    /**
     * Executes the maintenance type editing process.
     * Allows the user to select a maintenance type and optionally edit its name and/or description.
     */
    @Override
    public void run() {
        try {
            Utils.printDroneCenteredTitle("Edit Maintenance Type");

            Optional<List<MaintenanceType>> maintenanceTypes = getMaintenanceTypeController.getAllMaintenanceTypes();

            if (maintenanceTypes.isEmpty()) {
                Utils.printFailMessage("No maintenance types in the system yet...");
                Utils.dropLines(1);
                return;
            }

            int typeIndex = Utils.selectMaintenanceTypeIndex(maintenanceTypes.get());
            MaintenanceType maintenanceType = maintenanceTypes.get().get(typeIndex);

            MaintenanceTypeName newMaintenanceName = newMaintenanceName(maintenanceType);
            Description newMaintenanceDescription = newMaintenanceDescription(maintenanceType);

            if (newMaintenanceName == null && newMaintenanceDescription == null) {
                Utils.printFailMessage("Nothing has changed...");
                return;
            }

            Optional<MaintenanceType> editedMaintenanceType = editMaintenanceTypeController.editMaintenanceType(
                    maintenanceType, newMaintenanceName, newMaintenanceDescription);

            if (editedMaintenanceType.isPresent()) {
                Utils.printSuccessMessage("Maintenance Type updated successfully!");
            } else {
                Utils.printFailMessage("Failed to update maintenance type!");
            }

        } catch (RuntimeException ignored) {}
    }

    /**
     * Prompts the user to edit the name of a maintenance type.
     *
     * @param maintenanceType the current MaintenanceType
     * @return the new MaintenanceTypeName if changed, or null if unchanged
     */
    private MaintenanceTypeName newMaintenanceName(MaintenanceType maintenanceType) {
        Utils.printCenteredSubtitleV2("Maintenance Type Name");
        Utils.printAlterMessage("Current name: " + maintenanceType.name() + "\n");

        boolean editName = Utils.confirm("Do you want to edit the name? (y/n)");

        if (editName) {
            Utils.showDroneNameRules();
            return Utils.rePromptWhileInvalid("Enter the new Maintenance Type Name", MaintenanceTypeName::new);
        } else {
            Utils.printAlterMessage("Name maintained unchanged...");
            Utils.dropLines(1);
        }

        return null;
    }

    /**
     * Prompts the user to edit the description of a maintenance type.
     *
     * @param maintenanceType the current MaintenanceType
     * @return the new Description if changed, or null if unchanged
     */
    private Description newMaintenanceDescription(MaintenanceType maintenanceType) {
        Utils.printCenteredSubtitleV2("Maintenance Type Description");
        Utils.printAlterMessage("Current description: " + maintenanceType.description() + "\n");

        boolean editDescription = Utils.confirm("Do you want to edit the description? (y/n)");

        if (editDescription) {
            Utils.showDescriptionRules();
            return Utils.rePromptWhileInvalid("Enter the new Maintenance Type Description", Description::new);
        } else {
            Utils.printAlterMessage("Description maintained unchanged...");
            Utils.dropLines(1);
        }

        return null;
    }
}
