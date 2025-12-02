package persistence;

import domain.entity.DroneLanguageConfiguration;
import domain.entity.DroneModel;

import java.util.List;
import java.util.Optional;

public interface DroneLanguageConfigurationRepository {

    /**
     * Saves or updates a DroneLanguageConfiguration.
     */
    DroneLanguageConfiguration save(DroneLanguageConfiguration config);


    /**
     * Finds a DroneLanguageConfiguration for a given DroneModel.
     */
    Optional<DroneLanguageConfiguration> findByDroneModel(DroneModel droneModel);

    /**
     * Returns all DroneLanguageConfigurations.
     */
    List<DroneLanguageConfiguration> findAll();
}
