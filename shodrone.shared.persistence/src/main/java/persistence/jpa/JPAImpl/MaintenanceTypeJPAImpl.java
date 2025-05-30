package persistence.jpa.JPAImpl;

import domain.entity.MaintenanceType;
import persistence.MaintenanceTypeRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class MaintenanceTypeJPAImpl extends JpaBaseRepository<MaintenanceType, Long> implements MaintenanceTypeRepository {

    @Override
    public Optional<MaintenanceType> save(MaintenanceType maintenanceType) {
        Optional<MaintenanceType> checkExistence = findByName(maintenanceType.identity());
        if (checkExistence.isEmpty()) {
            MaintenanceType saved = this.add(maintenanceType);
            return Optional.of(saved);
        }
        return Optional.empty();

    }

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
}
