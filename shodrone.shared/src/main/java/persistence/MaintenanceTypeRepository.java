package persistence;

import domain.entity.MaintenanceType;

import java.util.List;
import java.util.Optional;

public interface MaintenanceTypeRepository {

    Optional<MaintenanceType> save(MaintenanceType maintenanceType);

    Optional<MaintenanceType> findByName(String name);

    List<MaintenanceType> findAll();

    Optional<MaintenanceType> updateMaintenanceType(MaintenanceType maintenanceType, String oldKey);
}
