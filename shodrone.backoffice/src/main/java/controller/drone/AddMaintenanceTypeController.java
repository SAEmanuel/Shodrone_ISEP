package controller.drone;

import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.Optional;

public class AddMaintenanceTypeController {

    private final MaintenanceTypeRepository repository;

    public AddMaintenanceTypeController() {
        repository = RepositoryProvider.maintenanceTypeRepository();
    }

    public Optional<MaintenanceType> createMaintenanceType(MaintenanceTypeName name, Description description){
        MaintenanceType maintenanceType = new MaintenanceType(name, description);
        return repository.save(maintenanceType);
    }

    public Optional<MaintenanceType> createMaintenanceTypeWithoutDescription(MaintenanceTypeName name){
        MaintenanceType maintenanceType = new MaintenanceType(name);
        return repository.save(maintenanceType);
    }

}
