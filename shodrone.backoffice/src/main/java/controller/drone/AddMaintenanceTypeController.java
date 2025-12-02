package controller.drone;

import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.Optional;

/**
 * Controller responsible for creating new maintenance types.
 */
public class AddMaintenanceTypeController {

    private final MaintenanceTypeRepository repository;

    /**
     * Initializes the controller with the maintenance type repository.
     */
    public AddMaintenanceTypeController() {
        repository = RepositoryProvider.maintenanceTypeRepository();
    }

    /**
     * Creates a new maintenance type with a name and description.
     *
     * @param name the maintenance type name
     * @param description the maintenance type description
     * @return an Optional containing the created MaintenanceType, or empty if it already exists
     */
    public Optional<MaintenanceType> createMaintenanceType(MaintenanceTypeName name, Description description) {
        MaintenanceType maintenanceType = new MaintenanceType(name, description);
        return repository.save(maintenanceType);
    }

    /**
     * Creates a new maintenance type with a name only.
     *
     * @param name the maintenance type name
     * @return an Optional containing the created MaintenanceType, or empty if it already exists
     */
    public Optional<MaintenanceType> createMaintenanceTypeWithoutDescription(MaintenanceTypeName name) {
        MaintenanceType maintenanceType = new MaintenanceType(name);
        return repository.save(maintenanceType);
    }
}
