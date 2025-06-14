package controller.drone;

import domain.entity.DroneLanguageConfiguration;
import domain.entity.DroneModel;
import persistence.DroneLanguageConfigurationRepository;
import persistence.RepositoryProvider;

import java.util.Optional;

public class DroneLanguageConfigurationController {

    private static final String DEFAULT_LANGUAGE = "DroneOne 0.86";
    private final DroneLanguageConfigurationRepository repo;

    public DroneLanguageConfigurationController() {
        this.repo = RepositoryProvider.droneLanguageConfigurationRepository();
    }

    /**
     * Registers a DroneLanguageConfiguration for a given DroneModel.
     * If one already exists, updates it.
     */
    public DroneLanguageConfiguration registerDroneLanguageConfiguration(DroneModel droneModel, String droneLanguage) {
        Optional<DroneLanguageConfiguration> existingConfig = repo.findByDroneModel(droneModel);

        if (existingConfig.isPresent()) {
            DroneLanguageConfiguration config = existingConfig.get();
            config.setDroneLanguage(droneLanguage);
            return repo.save(config);
        } else {
            DroneLanguageConfiguration newConfig = new DroneLanguageConfiguration(droneModel, droneLanguage);
            return repo.save(newConfig);
        }
    }

    /**
     * Gets the configured DroneLanguage for a given DroneModel.
     * If no configuration exists, returns a default language.
     */
    public String getDroneLanguageForModel(DroneModel droneModel) {
        return repo.findByDroneModel(droneModel)
                .map(DroneLanguageConfiguration::getDroneLanguage)
                .orElse(DEFAULT_LANGUAGE);
    }
}
