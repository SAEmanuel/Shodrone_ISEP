package persistence.inmemory;

import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertTrue;

class InMemoryMaintenanceTypeRepositoryTest {

    private InMemoryMaintenanceTypeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMaintenanceTypeRepository();
    }

    MaintenanceType createType(String name, String description) {
        return new MaintenanceType(new MaintenanceTypeName(name), new Description(description));
    }

    @Test
    void saveNewMaintenanceTypeSuccessfully() {
        MaintenanceType mt = createType("Battery", "Battery replacement");
        Optional<MaintenanceType> saved = repository.save(mt);
        assertTrue(saved.isPresent());
        assertEquals(mt, saved.get());
    }

    @Test
    void saveDuplicateMaintenanceTypeShouldFail() {
        MaintenanceType mt1 = createType("Camera", "Fix camera issues");
        MaintenanceType mt2 = createType("CAMERA", "Another camera fix");
        repository.save(mt1);
        Optional<MaintenanceType> result = repository.save(mt2);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByNameShouldReturnSavedType() {
        MaintenanceType mt = createType("Sensor", "Sensor adjustment");
        repository.save(mt);
        Optional<MaintenanceType> found = repository.findByName("SENSOR");
        assertTrue(found.isPresent());
        assertEquals("Sensor", found.get().name().name());
    }

    @Test
    void findByNameShouldReturnEmptyIfNotFound() {
        Optional<MaintenanceType> result = repository.findByName("Unknown");
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllShouldReturnAllSavedTypes() {
        repository.save(createType("Type1", "Desc1"));
        repository.save(createType("Type2", "Desc2"));
        List<MaintenanceType> all = repository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void updateMaintenanceTypeWithSameKeyShouldReplace() {
        MaintenanceType original = createType("GPS", "GPS calibration");
        repository.save(original);

        MaintenanceType updated = createType("GPS", "Updated GPS calibration");
        repository.updateMaintenanceType(updated, "GPS");

        Optional<MaintenanceType> result = repository.findByName("gps");
        assertTrue(result.isPresent());
        assertEquals("Updated GPS calibration", result.get().description().toString());
    }

    @Test
    void updateMaintenanceTypeWithNewKeyShouldChangeKey() {
        MaintenanceType original = createType("Motor", "Motor check");
        repository.save(original);

        MaintenanceType renamed = createType("Engine", "Motor check");
        repository.updateMaintenanceType(renamed, "Motor");

        assertTrue(repository.findByName("Motor").isEmpty());
        assertTrue(repository.findByName("Engine").isPresent());
    }
}
