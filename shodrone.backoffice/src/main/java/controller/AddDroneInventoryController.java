package controller;

import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.SerialNumber;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneRepository;

import java.util.Optional;

public class AddDroneInventoryController {
    private final DroneRepository repository;

    public AddDroneInventoryController() {
        repository = RepositoryProvider.droneRepository();
    }

    public Optional<Drone> addDroneInventory(DroneModel droneModel, SerialNumber serialNumber) {
        Drone drone = new Drone(serialNumber, droneModel);
        return repository.save(drone);
    }
}
