package persistence.inmemory;

import domain.entity.Drone;
import persistence.interfaces.DroneRepository;

import java.util.*;

public class InMemoryDroneRepository implements DroneRepository {
    private final Map<String, Drone> store = new HashMap<>();

    @Override
    public Optional<Drone> save (Drone drone) {
        String key = drone.identity().toLowerCase();

        if(store.containsKey(key)) {
            return Optional.empty();
        }

        store.put(key, drone);
        return Optional.of(drone);
    }


    public Optional<Drone> findByDroneSN(String SN) {
        return Optional.ofNullable(store.get(SN.toLowerCase()));
    }

    @Override
    public List<Drone> findAll() {
        return new ArrayList<>(store.values());
    }
}
