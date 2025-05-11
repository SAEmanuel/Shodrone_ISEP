package controller.drone;

import domain.entity.Drone;
import domain.entity.DroneModel;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneRepository;

import java.util.List;
import java.util.Optional;

/**
 * A controller class responsible for retrieving drones from the repository.
 */
public class GetDronesController {

    /**
     * The repository used to access Drone entities.
     */
    private final DroneRepository repository;

    /**
     * Initializes a new GetDronesController with a repository instance.
     *
     * @return void
     */
    public GetDronesController() {
        repository = RepositoryProvider.droneRepository();
    }

    /**
     * Retrieves all drones from the repository.
     *
     * @return an Optional containing a list of all drones, or empty if none found
     */
    public Optional<List<Drone>> getAllDrones() {
        List<Drone> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }

    /**
     * Retrieves a drone by its serial number.
     *
     * @param droneSN the serial number of the drone to find
     * @return an Optional containing the found drone, or empty if not found
     */
    public Optional<Drone> getDroneBySN(String droneSN) {
        return repository.findByDroneSN(droneSN);
    }

    /**
     * Retrieves all drones that match the given drone model.
     *
     * @param droneModel the model of drones to search for
     * @return an Optional containing a list of matching drones, or empty if none found
     */
    public Optional<List<Drone>> getDroneByModel(DroneModel droneModel) {
        return repository.findByDroneModel(droneModel);
    }
}