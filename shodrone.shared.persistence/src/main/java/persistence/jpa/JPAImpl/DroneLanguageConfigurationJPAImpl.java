package persistence.jpa.JPAImpl;

import domain.entity.DroneLanguageConfiguration;
import domain.entity.DroneModel;
import persistence.DroneLanguageConfigurationRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class DroneLanguageConfigurationJPAImpl
        extends JpaBaseRepository<DroneLanguageConfiguration, Long>
        implements DroneLanguageConfigurationRepository {

    @Override
    public DroneLanguageConfiguration save(DroneLanguageConfiguration config) {
        if (config.identity() == null) {
            return add(config);
        } else {
            return update(config);
        }
    }

    @Override
    public Optional<DroneLanguageConfiguration> findByDroneModel(DroneModel droneModel) {
        List<DroneLanguageConfiguration> results = entityManager()
                .createQuery("SELECT e FROM DroneLanguageConfiguration e WHERE e.droneModel = :droneModel", DroneLanguageConfiguration.class)
                .setParameter("droneModel", droneModel)
                .getResultList();

        if (results.isEmpty()) return Optional.empty();
        return Optional.of(results.get(0));
    }

    @Override
    public List<DroneLanguageConfiguration> findAll() {
        return super.findAll();
    }
}
