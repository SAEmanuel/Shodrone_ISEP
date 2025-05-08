package controller;

import domain.entity.Drone;
import domain.valueObjects.DroneRemovalLog;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneRepository;

import java.util.Optional;

public class RemoveDroneInventoryController {
    private DroneRepository repository;

    public RemoveDroneInventoryController() {
        repository = RepositoryProvider.droneRepository();
    }

    public Optional<Drone> removeDrone(Drone drone, DroneRemovalLog removalLog) {
        return repository.removeDrone(drone, removalLog);
    }


}
