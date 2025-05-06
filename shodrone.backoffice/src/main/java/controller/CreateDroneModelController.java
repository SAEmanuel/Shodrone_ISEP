package controller;

import domain.entity.DroneModel;
import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import domain.valueObjects.Name;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneModelRepository;

import java.util.Optional;

public class CreateDroneModelController {
    private final DroneModelRepository repository;

    public CreateDroneModelController() {
        repository = RepositoryProvider.droneModelRepository();
    }

    public Optional<DroneModel> createDroneModelWithDescription(DroneModelID droneModelID, DroneName name, Description description, int maxWindSpeed) {
        DroneModel category = new DroneModel(droneModelID, name, description, maxWindSpeed);
        return repository.save(category);
    }

    public Optional<DroneModel> createDroneModelNoDescription(DroneModelID droneModelID, DroneName name, int maxWindSpeed) {
        DroneModel category = new DroneModel(droneModelID ,name, maxWindSpeed);
        return repository.save(category);
    }
}
