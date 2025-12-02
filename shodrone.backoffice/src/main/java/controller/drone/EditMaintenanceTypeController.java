package controller.drone;

import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.Optional;

/**
 * Controller responsible for editing an existing maintenance type.
 */
public class EditMaintenanceTypeController {

    private final MaintenanceTypeRepository repository;

    /**
     * Initializes the controller with the appropriate repository implementation.
     */
    public EditMaintenanceTypeController() {
        repository = RepositoryProvider.maintenanceTypeRepository();
    }

    /**
     * Edits a maintenance type by updating its name and/or description.
     *
     * @param maintenanceType the original MaintenanceType to be edited
     * @param newName the new name to apply (or null to keep unchanged)
     * @param newDescription the new description to apply (or null to keep unchanged)
     * @return an Optional containing the updated MaintenanceType, or empty if the update failed
     */
    public Optional<MaintenanceType> editMaintenanceType(MaintenanceType maintenanceType, MaintenanceTypeName newName, Description newDescription) {
        String oldName = maintenanceType.identity();

        if (newName != null) {
            maintenanceType.changeNameTo(newName);
        }
        if (newDescription != null) {
            maintenanceType.changeDescriptionTo(newDescription);
        }

        return repository.updateMaintenanceType(maintenanceType, oldName);
    }
}
