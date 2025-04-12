package app;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppSettings {

    private final Properties props = new Properties();

    public AppSettings() {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (stream != null) {
                props.load(stream);
            } else {
                throw new RuntimeException("application.properties not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public String persistenceUnitName() {
        return props.getProperty("persistence.persistenceUnit", "shodronePU");
    }

    public Map<String, String> extendedPersistenceProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("jakarta.persistence.schema-generation.database.action",
                props.getProperty("jakarta.persistence.schema-generation.database.action", "none"));
        return map;
    }
}