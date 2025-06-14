package persistence.inmemory;

import domain.entity.DroneLanguageConfiguration;
import domain.entity.DroneModel;
import persistence.DroneLanguageConfigurationRepository;

import java.util.*;

public class InMemoryDroneLanguageConfigurationRepository implements DroneLanguageConfigurationRepository {

    private final Map<Long, DroneLanguageConfiguration> store = new HashMap<>();
    private long nextId = 1;

    @Override
    public DroneLanguageConfiguration save(DroneLanguageConfiguration config) {
        if (config.identity() == null) {
            // Gerar um ID simples
            try {
                java.lang.reflect.Field field = DroneLanguageConfiguration.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(config, nextId++);
            } catch (Exception e) {
                throw new RuntimeException("Failed to set ID on DroneLanguageConfiguration", e);
            }
        }
        store.put(config.identity(), config);
        return config;
    }

    @Override
    public Optional<DroneLanguageConfiguration> findByDroneModel(DroneModel droneModel) {
        return store.values().stream()
                .filter(config -> config.getDroneModel().equals(droneModel))
                .findFirst();
    }

    @Override
    public List<DroneLanguageConfiguration> findAll() {
        return new ArrayList<>(store.values());
    }
}
