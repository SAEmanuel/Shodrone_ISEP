package controller.drone;

import domain.entity.DroneModel;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneModelRepository;

import java.util.List;
import java.util.Optional;

/**
 * A controller class responsible for retrieving drone models from the repository.
 */
public class GetDroneModelsController {

    /**
     * The repository used to access DroneModel entities.
     */
    private final DroneModelRepository repository;

    /**
     * Initializes a new GetDroneModelsController with a repository instance.
     *
     * @return void
     */
    public GetDroneModelsController() {
        repository = RepositoryProvider.droneModelRepository();
    }

    /**
     * Retrieves all drone models from the repository.
     *
     * @return an Optional containing a list of all drone models, or empty if none found
     */
    public Optional<List<DroneModel>> getAllModels() {
        List<DroneModel> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }

    /**
     * Retrieves a drone model by its ID.
     *
     * @param droneModelId the ID of the drone model to find
     * @return an Optional containing the found drone model, or empty if not found
     */
    public Optional<DroneModel> getModelById(String droneModelId) {
        return repository.findByDroneModelID(droneModelId);
    }
}