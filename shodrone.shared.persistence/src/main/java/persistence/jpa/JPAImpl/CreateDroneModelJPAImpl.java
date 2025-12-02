package persistence.jpa.JPAImpl;

import domain.entity.DroneModel;
import persistence.DroneModelRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * A JPA-based implementation of the DroneModelRepository interface for persisting DroneModel entities.
 */
public class CreateDroneModelJPAImpl extends JpaBaseRepository<DroneModel, Long> implements DroneModelRepository {

    /**
     * Saves a DroneModel to the JPA persistence layer.
     *
     * @param category the DroneModel to save
     * @return an Optional containing the saved DroneModel, or empty if the ID already exists
     */
    @Override
    public Optional<DroneModel> save(DroneModel category) {
        Optional<DroneModel> checkExistence = findByDroneModelID(category.identity());
        if (checkExistence.isEmpty()) {
            DroneModel saved = this.add(category);
            return Optional.ofNullable(saved);
        }
        return Optional.empty();
    }

    /**
     * Finds a DroneModel by its ID in the JPA persistence layer.
     *
     * @param name the ID of the DroneModel to find
     * @return an Optional containing the found DroneModel, or empty if not found
     */
    @Override
    public Optional<DroneModel> findByDroneModelID(String name) {
        List<DroneModel> results = entityManager()
                .createQuery("SELECT c FROM DroneModel c WHERE LOWER(c.droneModelID.modelID) = :name", DroneModel.class)
                .setParameter("name", name.toLowerCase())
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }
}