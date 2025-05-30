package persistence.inmemory;

import domain.entity.MaintenanceType;
import persistence.MaintenanceTypeRepository;

import java.util.*;

public class InMemoryMaintenanceTypeRepository implements MaintenanceTypeRepository {

    private final Map<String, MaintenanceType> store = new HashMap<>();

    @Override
    public Optional<MaintenanceType> save(MaintenanceType maintenanceType) {
        String key = maintenanceType.identity().toLowerCase();
        if (store.containsKey(key)) {
            return Optional.empty();
        } else {
            store.put(key, maintenanceType);
            return Optional.of(maintenanceType);
        }
    }

    @Override
    public Optional<MaintenanceType> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }

    @Override
    public List<MaintenanceType> findAll() {
        return new ArrayList<>(store.values());
    }

}
