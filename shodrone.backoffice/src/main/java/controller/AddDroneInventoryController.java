package controller;

import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.SerialNumber;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneRepository;

import java.util.Optional;

/**
 * A controller class responsible for adding drones to the inventory.
 */
public class AddDroneInventoryController {

    /**
     * The repository used to manage Drone entities.
     */
    private final DroneRepository repository;

    /**
     * Initializes a new AddDroneInventoryController with a repository instance.
     *
     * @return void
     */
    public AddDroneInventoryController() {
        repository = RepositoryProvider.droneRepository();
    }

    /**
     * Adds a new drone to the inventory with the specified model and serial number.
     *
     * @param droneModel the drone model of the new drone
     * @param serialNumber the serial number of the new drone
     * @return an Optional containing the added drone, or empty if the serial number already exists
     */
    public Optional<Drone> addDroneInventory(DroneModel droneModel, SerialNumber serialNumber) {
        Drone drone = new Drone(serialNumber, droneModel);
        return repository.save(drone);
    }

    /**
     * Adds an existing drone to the inventory.
     *
     * @param selectedDrone the Optional containing the drone to add
     * @return an Optional containing the added drone, or empty if the operation fails
     */
    public Optional<Drone> addExistingDroneInventory(Optional<Drone> selectedDrone) {
        return repository.addExistingDroneInventory(selectedDrone.get());
    }
}