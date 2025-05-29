package persistence.inmemory;

import domain.entity.DroneModel;
import persistence.DroneModelRepository;

import java.util.*;

/**
 * An in-memory implementation of the DroneModelRepository interface for storing DroneModel entities.
 */
public class InMemoryDroneModelRepository implements DroneModelRepository {

    /**
     * The in-memory store for DroneModel entities, mapping drone model IDs to DroneModel objects.
     */
    private final Map<String, DroneModel> store = new HashMap<>();

    /**
     * Saves a DroneModel to the in-memory store.
     *
     * @param droneModel the DroneModel to save
     * @return an Optional containing the saved DroneModel, or empty if the ID already exists
     */
    @Override
    public Optional<DroneModel> save(DroneModel droneModel) {
        String key = droneModel.identity().toLowerCase();
        if (store.containsKey(key)) {
            return Optional.empty();
        } else {
            store.put(key, droneModel);
            return Optional.of(droneModel);
        }
    }

    /**
     * Finds a DroneModel by its ID in the in-memory store.
     *
     * @param name the ID of the DroneModel to find
     * @return an Optional containing the found DroneModel, or empty if not found
     */
    @Override
    public Optional<DroneModel> findByDroneModelID(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }

    /**
     * Retrieves all DroneModels stored in the in-memory store.
     *
     * @return a List containing all DroneModels in the store
     */
    @Override
    public List<DroneModel> findAll() {
        return new ArrayList<>(store.values());
    }
}