package persistence.inmemory;

import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.DroneRemovalLog;
import domain.valueObjects.DroneStatus;
import persistence.interfaces.DroneRepository;

import java.util.*;

public class InMemoryDroneRepository implements DroneRepository {

    /**
     * The in-memory store for Drone entities, mapping serial numbers to Drone objects.
     */
    private final Map<String, Drone> store = new HashMap<>();

    /**
     * Saves a drone to the in-memory store.
     *
     * @param drone the drone to save
     * @return an Optional containing the saved drone, or empty if the serial number already exists
     */
    @Override
    public Optional<Drone> save(Drone drone) {
        String key = drone.identity().toLowerCase();

        if (store.containsKey(key)) {
            return Optional.empty();
        }

        store.put(key, drone);
        return Optional.of(drone);
    }

    /**
     * Finds a drone by its serial number in the in-memory store.
     *
     * @param SN the serial number of the drone to find
     * @return an Optional containing the found drone, or empty if not found
     */
    public Optional<Drone> findByDroneSN(String SN) {
        return Optional.ofNullable(store.get(SN.toLowerCase()));
    }

    /**
     * Retrieves all drones from the in-memory store.
     *
     * @return a List containing all drones in the store
     */
    @Override
    public List<Drone> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * Removes a drone and logs the removal in the in-memory store.
     *
     * @param drone the drone to remove
     * @param log the removal log to add
     * @return an Optional containing the updated drone, or empty if the operation fails
     */
    @Override
    public Optional<Drone> removeDrone(Drone drone, DroneRemovalLog log) {
        if (drone == null || log == null) {
            return Optional.empty();
        }

        drone.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
        drone.addDroneRemovalLog(log);

        return Optional.of(drone);
    }

    /**
     * Adds an existing drone to the inventory in the in-memory store.
     *
     * @param drone the drone to add to the inventory
     * @return an Optional containing the updated drone, or empty if the operation fails
     */
    @Override
    public Optional<Drone> addExistingDroneInventory(Drone drone) {
        if (drone == null) {
            return Optional.empty();
        }

        drone.changeDroneStatusTo(DroneStatus.AVAILABLE);
        return Optional.of(drone);
    }

    /**
     * Finds all drones that match the given drone model and are AVAILABLE in the in-memory store.
     *
     * @param droneModel the model of drones to search for
     * @return an Optional containing a list of matching drones, or empty if none found
     */
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