package persistence.jpa.JPAImpl;

import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.DroneRemovalLog;
import domain.valueObjects.DroneStatus;
import persistence.interfaces.DroneRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the DroneRepository interface.
 * Handles persistence operations for Drone entities using JPA.
 */
public class DroneJPAImpl extends JpaBaseRepository<Drone, Long> implements DroneRepository {

    /**
     * Saves a new Drone in the database if it doesn't already exist.
     *
     * @param drone the Drone entity to be saved
     * @return an Optional containing the saved Drone, or empty if it already exists
     */
    public Optional<Drone> save(Drone drone) {
        Optional<Drone> checkExistence = findByDroneSN(drone.identity());
        if (checkExistence.isEmpty()) {
            Drone saved = this.add(drone);
            return Optional.ofNullable(saved);
        }
        return Optional.empty();
    }

    /**
     * Finds a Drone by its serial number (case-insensitive).
     *
     * @param serialNumber the serial number to search for
     * @return an Optional containing the found Drone, or empty if none found
     */
    public Optional<Drone> findByDroneSN(String serialNumber) {
        List<Drone> result = entityManager()
                .createQuery("SELECT d FROM Drone d WHERE LOWER(d.serialNumber.serialNumber) = :serialNumber", Drone.class)
                .setParameter("serialNumber", serialNumber.toLowerCase())
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    /**
     * Marks a drone as unavailable and adds a removal log.
     *
     * @param drone the drone to be removed
     * @param log the removal log to be added
     * @return an Optional containing the updated drone, or empty if invalid input
     */
    @Override
    public Optional<Drone> removeDrone(Drone drone, DroneRemovalLog log) {
        if (drone == null || log == null) {
            return Optional.empty();
        }

        drone.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
        drone.addDroneRemovalLog(log);

        Drone updatedDrone = update(drone);
        return Optional.of(updatedDrone);
    }

    /**
     * Marks an existing drone as available (adds it to the inventory).
     *
     * @param drone the existing drone
     * @return an Optional containing the updated drone, or empty if input is null
     */
    @Override
    public Optional<Drone> addExistingDroneInventory(Drone drone) {
        if (drone == null) {
            return Optional.empty();
        }

        drone.changeDroneStatusTo(DroneStatus.AVAILABLE);
        Drone updatedDrone = update(drone);
        return Optional.of(updatedDrone);
    }

    /**
     * Finds all drones that match the given drone model and are AVAILABLE.
     *
     * @param droneModel the model of drones to search for
     * @return an Optional containing a list of matching drones, or empty if none found
     */
    @Override
    public Optional<List<Drone>> findByDroneModel(DroneModel droneModel) {
        if (droneModel == null) return Optional.empty();

        List<Drone> result = entityManager()
                .createQuery("SELECT d FROM Drone d WHERE d.droneModel = :model AND d.droneStatus = :status", Drone.class)
                .setParameter("model", droneModel)
                .setParameter("status", DroneStatus.AVAILABLE)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }
}
