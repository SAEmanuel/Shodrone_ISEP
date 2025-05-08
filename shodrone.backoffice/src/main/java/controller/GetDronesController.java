package controller;

import domain.entity.Drone;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneRepository;

import java.util.List;
import java.util.Optional;

public class GetDronesController {
    private final DroneRepository repository;

    public GetDronesController() {
        repository = RepositoryProvider.droneRepository();
    }

    public Optional<List<Drone>> getAllDrones() {
        List<Drone> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }

    public Optional<Drone> getDroneBySN(String droneSN) {
        return repository.findByDroneSN(droneSN);
    }

}
