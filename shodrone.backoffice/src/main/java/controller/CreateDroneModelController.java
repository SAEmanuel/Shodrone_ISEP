package controller;

import domain.entity.DroneModel;
import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import domain.valueObjects.Name;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneModelRepository;

import java.util.Optional;

/**
 * A controller class responsible for handling the creation of Drone Models.
 */
public class CreateDroneModelController {

    /**
     * The repository used to persist DroneModel entities.
     */
    private final DroneModelRepository repository;

    /**
     * Initializes a new CreateDroneModelController with a repository instance.
     *
     * @return void
     */
    public CreateDroneModelController() {
        repository = RepositoryProvider.droneModelRepository();
    }

    /**
     * Creates a Drone Model with a description and saves it to the repository.
     *
     * @param droneModelID the ID of the drone model
     * @param name the name of the drone model
     * @param description the description of the drone model
     * @param maxWindSpeed the maximum wind speed the drone model can handle (in m/s)
     * @return an Optional containing the created DroneModel, or empty if the ID already exists
     */
    public Optional<DroneModel> createDroneModelWithDescription(DroneModelID droneModelID, DroneName name, Description description, double maxWindSpeed) {
        DroneModel model = new DroneModel(droneModelID, name, description, maxWindSpeed);
        return repository.save(model);
    }

    /**
     * Creates a Drone Model without a description and saves it to the repository.
     *
     * @param droneModelID the ID of the drone model
     * @param name the name of the drone model
     * @param maxWindSpeed the maximum wind speed the drone model can handle (in m/s)
     * @return an Optional containing the created DroneModel, or empty if the ID already exists
     */
    public Optional<DroneModel> createDroneModelNoDescription(DroneModelID droneModelID, DroneName name, double maxWindSpeed) {
        DroneModel model = new DroneModel(droneModelID, name, maxWindSpeed);
        return repository.save(model);
    }
}