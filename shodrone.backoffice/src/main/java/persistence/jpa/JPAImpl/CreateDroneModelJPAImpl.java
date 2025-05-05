package persistence.jpa.JPAImpl;

import domain.entity.DroneModel;
import persistence.interfaces.DroneModelRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class CreateDroneModelJPAImpl extends JpaBaseRepository<DroneModel, Long> implements DroneModelRepository {

    public Optional<DroneModel> save(DroneModel category) {
        Optional<DroneModel> checkExistence = findByDroneModelID(category.identity());
        if (checkExistence.isEmpty()) {
            DroneModel saved = this.add(category);
            return Optional.ofNullable(saved);
        }
        return Optional.empty();
    }

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
