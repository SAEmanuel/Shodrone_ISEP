package persistence.inmemory;

import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.DroneRemovalLog;
import domain.valueObjects.DroneStatus;
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

    @Override
    public Optional<Drone> removeDrone(Drone drone, DroneRemovalLog log) {

        if (drone == null || log == null) {
            return Optional.empty();
        }

        drone.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
        drone.addDroneRemovalLog(log);

        return Optional.of(drone);
    }

    @Override
    public Optional<Drone> addExistingDroneInventory(Drone drone) {
        if (drone == null) {
            return Optional.empty();
        }

        drone.changeDroneStatusTo(DroneStatus.AVAILABLE);
        return Optional.of(drone);
    }

    @Override
    public Optional<List<Drone>> findByDroneModel(DroneModel droneModel) {
        if (droneModel == null) return Optional.empty();

        List<Drone> droneList = new ArrayList<>();
        for (Drone drone : store.values()) {
            if (droneModel.equals(drone.droneModel()) && DroneStatus.AVAILABLE.equals(drone.droneStatus())) {
                droneList.add(drone);
            }
        }

        return droneList.isEmpty() ? Optional.empty() : Optional.of(droneList);
    }

}


