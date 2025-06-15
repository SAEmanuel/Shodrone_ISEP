package controller.drone;

import domain.entity.MaintenanceType;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for retrieving maintenance types from the repository.
 */
public class GetMaintenanceTypeController {

    private final MaintenanceTypeRepository repository;

    /**
     * Initializes the controller with the appropriate repository implementation.
     */
    public GetMaintenanceTypeController() {
        repository = RepositoryProvider.maintenanceTypeRepository();
    }

    /**
     * Retrieves all maintenance types from the repository.
     *
     * @return an Optional containing the list of maintenance types, or empty if none exist
     */
    public Optional<List<MaintenanceType>> getAllMaintenanceTypes() {
        List<MaintenanceType> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }
}
