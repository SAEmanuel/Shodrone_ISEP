package persistence.inmemory;

import domain.entity.DroneModel;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryDroneModelRepositoryTest {

    private InMemoryDroneModelRepository repository;
    private DroneModel model1;
    private DroneModel model2;

    @BeforeEach
    void setUp() {
        repository = new InMemoryDroneModelRepository();

        model1 = new DroneModel(new DroneModelID("DJI_PRO"),
                new DroneName("Model Alpha"),
                15
        );

        model2 = new DroneModel(new DroneModelID("DJI_PRO_2" ),
                new DroneName("Model Beta"),
                18
        );
    }

    @Test
    void testSaveReturnsOptionalOfModelWhenNotExists() {
        Optional<DroneModel> saved = repository.save(model1);
        assertTrue(saved.isPresent());
        assertEquals(model1, saved.get());
    }

    @Test
    void testSaveReturnsEmptyWhenModelAlreadyExists() {
        repository.save(model1);
        Optional<DroneModel> result = repository.save(model1);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByDroneModelIDReturnsCorrectModel() {
        repository.save(model1);
        Optional<DroneModel> found = repository.findByDroneModelID("DJI_PRO");
        assertTrue(found.isPresent());
        assertEquals(model1, found.get());
    }

    @Test
    void testFindByDroneModelIDIsCaseInsensitive() {
        repository.save(model1);
        Optional<DroneModel> found = repository.findByDroneModelID("dji_pro");
        assertTrue(found.isPresent());
        assertEquals(model1, found.get());
    }

    @Test
    void testFindByDroneModelIDReturnsEmptyIfNotFound() {
        Optional<DroneModel> found = repository.findByDroneModelID("SN-99999");
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAllReturnsAllModels() {
        repository.save(model1);
        repository.save(model2);
        List<DroneModel> all = repository.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(model1));
        assertTrue(all.contains(model2));
    }

    @Test
    void testFindAllReturnsEmptyListWhenNoModelsSaved() {
        List<DroneModel> all = repository.findAll();
        assertTrue(all.isEmpty());
    }
}
