package persistence.inmemory;

import domain.entity.MaintenanceType;
import persistence.MaintenanceTypeRepository;

import java.util.*;

/**
 * In-memory implementation of the MaintenanceTypeRepository interface.
 * Used for testing or scenarios where persistence is not required.
 */
public class InMemoryMaintenanceTypeRepository implements MaintenanceTypeRepository {

    private final Map<String, MaintenanceType> store = new HashMap<>();

    /**
     * Saves a new MaintenanceType if the name is not already in use.
     *
     * @param maintenanceType the MaintenanceType to save
     * @return an Optional containing the saved MaintenanceType, or empty if a duplicate exists
     */
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

    /**
     * Finds a MaintenanceType by its name.
     *
     * @param name the maintenance type name
     * @return an Optional containing the MaintenanceType, or empty if not found
     */
    @Override
    public Optional<MaintenanceType> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }

    /**
     * Returns all stored MaintenanceTypes.
     *
     * @return a list of all MaintenanceTypes
     */
    @Override
    public List<MaintenanceType> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * Updates a MaintenanceType by replacing the old entry with the new one.
     *
     * @param maintenanceType the updated MaintenanceType object
     * @param oldKey the previous key used to identify the maintenance type
     * @return an Optional containing the updated MaintenanceType
     */
    @Override
    public Optional<MaintenanceType> updateMaintenanceType(MaintenanceType maintenanceType, String oldKey) {
        String newKey = maintenanceType.identity().toLowerCase();
        store.remove(oldKey.toLowerCase());
        store.put(newKey, maintenanceType);
        return Optional.of(maintenanceType);
    }
}
