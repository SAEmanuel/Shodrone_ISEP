package controller.drone;

import domain.entity.Drone;
import domain.valueObjects.DroneRemovalLog;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneRepository;

import java.util.Optional;

/**
 * Controls the removal of drones from inventory.
 */

public class RemoveDroneInventoryController {
    private DroneRepository repository;

    /**
     * Initializes a new controller with the drone repository.
     */
    public RemoveDroneInventoryController() {
        repository = RepositoryProvider.droneRepository();
    }

    /**
     * Removes a drone from inventory and logs the removal reason.
     *
     * @param drone the drone to remove
     * @param removalLog the reason and details for removal
     * @return an Optional containing the removed drone if successful, empty otherwise
     */
    public Optional<Drone> removeDrone(Drone drone, DroneRemovalLog removalLog) {
        return repository.removeDrone(drone, removalLog);
    }
}