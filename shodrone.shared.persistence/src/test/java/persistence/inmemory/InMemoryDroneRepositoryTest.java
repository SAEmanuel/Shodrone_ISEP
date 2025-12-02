package persistence.inmemory;

import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDroneRepositoryTest {

    private InMemoryDroneRepository repository;
    private Drone drone1;
    private Drone drone2;
    private DroneModel modelA;

    @BeforeEach
    void setUp() {
        repository = new InMemoryDroneRepository();

        modelA = new DroneModel(new DroneModelID("Phantom_X"), new DroneName("Phantom X"), 20);

        drone1 = new Drone(new SerialNumber("SN-00001"), modelA);
        drone2 = new Drone(new SerialNumber("SN-00002"), modelA);
    }

    @Test
    void testSaveNewDroneSuccessfully() {
        Optional<Drone> saved = repository.save(drone1);
        assertTrue(saved.isPresent());
        assertEquals("SN-00001", saved.get().identity());
    }

    @Test
    void testSaveDuplicateDroneReturnsEmpty() {
        repository.save(drone1);
        Optional<Drone> result = repository.save(drone1);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByDroneSNReturnsCorrectDrone() {
        repository.save(drone2);
        Optional<Drone> found = repository.findByDroneSN("SN-00002");
        assertTrue(found.isPresent());
        assertEquals("SN-00002", found.get().identity());
    }

    @Test
    void testFindByDroneSNReturnsEmptyIfNotFound() {
        Optional<Drone> found = repository.findByDroneSN("SN-99999");
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAllReturnsAllDrones() {
        repository.save(drone1);
        repository.save(drone2);
        List<Drone> all = repository.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(drone1));
        assertTrue(all.contains(drone2));
    }

    @Test
    void testRemoveDroneUpdatesStatusAndLogs() {
        repository.save(drone1);
        DroneRemovalLog log = new DroneRemovalLog("Broken motor");

        Optional<Drone> removed = repository.removeDrone(drone1, log);
        assertTrue(removed.isPresent());
        assertEquals(DroneStatus.UNAVAILABLE, removed.get().droneStatus());
        assertFalse(removed.get().droneRemovalLogs().isEmpty());
    }

    @Test
    void testRemoveDroneReturnsEmptyForNullInput() {
        Optional<Drone> result = repository.removeDrone(null, null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testAddExistingDroneInventoryChangesStatusToAvailable() {
        drone1.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
        Optional<Drone> updated = repository.addExistingDroneInventory(drone1);
        assertTrue(updated.isPresent());
        assertEquals(DroneStatus.AVAILABLE, updated.get().droneStatus());
    }

    @Test
    void testAddExistingDroneInventoryReturnsEmptyForNullDrone() {
        Optional<Drone> result = repository.addExistingDroneInventory(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByDroneModelReturnsAvailableDronesOnly() {
        drone1.changeDroneStatusTo(DroneStatus.AVAILABLE);
        drone2.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
        repository.save(drone1);
        repository.save(drone2);

        Optional<List<Drone>> found = repository.findByDroneModel(modelA);
        assertTrue(found.isPresent());
        assertEquals(1, found.get().size());
        assertEquals(drone1.identity(), found.get().get(0).identity());
    }

    @Test
    void testFindByDroneModelReturnsEmptyIfModelIsNull() {
        Optional<List<Drone>> result = repository.findByDroneModel(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByDroneModelReturnsEmptyIfNoneAvailable() {
        drone1.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
        repository.save(drone1);

        Optional<List<Drone>> result = repository.findByDroneModel(modelA);
        assertTrue(result.isEmpty());
    }
}
