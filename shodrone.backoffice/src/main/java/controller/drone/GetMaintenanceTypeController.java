package controller.drone;

import domain.entity.MaintenanceType;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

public class GetMaintenanceTypeController {

    private final MaintenanceTypeRepository repository;

    public GetMaintenanceTypeController() {
        repository = RepositoryProvider.maintenanceTypeRepository();
    }

    public Optional<List<MaintenanceType>> getAllMaintenanceTypes(){
        List<MaintenanceType> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }

}
