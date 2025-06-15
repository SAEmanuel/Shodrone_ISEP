package persistence.jpa.JPAImpl;

import domain.entity.MaintenanceType;
import persistence.MaintenanceTypeRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the MaintenanceTypeRepository interface.
 */
public class MaintenanceTypeJPAImpl extends JpaBaseRepository<MaintenanceType, Long> implements MaintenanceTypeRepository {

    /**
     * Saves a new MaintenanceType if it doesn't already exist.
     *
     * @param maintenanceType the MaintenanceType to save
     * @return an Optional containing the saved MaintenanceType, or empty if one with the same name exists
     */
    @Override
    public Optional<MaintenanceType> save(MaintenanceType maintenanceType) {
        Optional<MaintenanceType> checkExistence = findByName(maintenanceType.identity());
        if (checkExistence.isEmpty()) {
            MaintenanceType saved = this.add(maintenanceType);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    /**
     * Finds a MaintenanceType by its name.
     *
     * @param name the name of the maintenance type
     * @return an Optional containing the MaintenanceType, or empty if not found
     */
    @Override
    public Optional<MaintenanceType> findByName(String name) {
        List<MaintenanceType> results = entityManager()
                .createQuery("SELECT c FROM MaintenanceType c WHERE LOWER(c.name.name) = :name", MaintenanceType.class)
                .setParameter("name", name.toLowerCase())
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    /**
     * Updates an existing MaintenanceType.
     *
     * @param maintenanceType the updated MaintenanceType object
     * @param oldKey the old key (not used in this implementation)
     * @return an Optional containing the updated MaintenanceType, or empty if the entity was not found
     */
    @Override
    public Optional<MaintenanceType> updateMaintenanceType(MaintenanceType maintenanceType, String oldKey) {
        if (maintenanceType == null || maintenanceType.identity() == null) {
            return Optional.empty();
        }

        Optional<MaintenanceType> existing = Optional.ofNullable(findById(maintenanceType.id()));
        if (existing.isEmpty()) {
            return Optional.empty();
        }

        MaintenanceType updated = update(maintenanceType);
        return Optional.ofNullable(updated);
    }
}
