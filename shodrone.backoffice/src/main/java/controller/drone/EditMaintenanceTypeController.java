package controller.drone;

import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.Optional;

public class EditMaintenanceTypeController {

    private final MaintenanceTypeRepository repository;

    public EditMaintenanceTypeController() {
        repository = RepositoryProvider.maintenanceTypeRepository();
    }

    public Optional<MaintenanceType> editMaintenanceType(MaintenanceType maintenanceType, MaintenanceTypeName newName, Description newDescription){
        String oldName = maintenanceType.identity();

        if (newName != null){
            maintenanceType.changeNameTo(newName);
        }
        if (newDescription != null){
            maintenanceType.changeDescriptionTo(newDescription);
        }

        return repository.updateMaintenanceType(maintenanceType, oldName);
    }




}
