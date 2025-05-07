package persistence.inmemory;

import domain.entity.DroneModel;
import persistence.interfaces.DroneModelRepository;

import java.util.*;

public class InMemoryDroneModelRepository implements DroneModelRepository {
    private final Map<String, DroneModel> store = new HashMap<>();

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

    @Override
    public Optional<DroneModel> findByDroneModelID(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }

    @Override
    public List<DroneModel> findAll() {
        return new ArrayList<>(store.values());
    }
}
