package controller;

import domain.entity.DroneModel;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneModelRepository;

import java.util.List;
import java.util.Optional;

public class GetDroneModelsController {
    private final DroneModelRepository repository;

    public GetDroneModelsController() {
        repository = RepositoryProvider.droneModelRepository();
    }

    public Optional<List<DroneModel>> getAllModels() {
        List<DroneModel> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }
}
