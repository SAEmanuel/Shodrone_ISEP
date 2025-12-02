package domain.entity;

import domain.valueObjects.DroneRemovalLog;
import domain.valueObjects.DroneStatus;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import domain.valueObjects.SerialNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneTest {

    private static final DroneModelID VALID_MODEL_ID = new DroneModelID("DM_001");
    private static final DroneName VALID_DRONE_NAME = new DroneName("Phantom");
    private static final int VALID_MAX_WIND_SPEED = 25;

    private DroneModel validModel;
    private SerialNumber validSerial;

    @BeforeEach
    void setUp() {
        validModel = new DroneModel(VALID_MODEL_ID, VALID_DRONE_NAME, VALID_MAX_WIND_SPEED);
        validSerial = new SerialNumber("SN-12345");
    }

    @Test
    void validDroneShouldBeCreated() {
        Drone drone = new Drone(validSerial, validModel);
        assertEquals(validSerial.toString(), drone.identity());
        assertEquals(validModel, drone.droneModel());
        assertEquals(DroneStatus.AVAILABLE, drone.droneStatus());
        assertTrue(drone.droneRemovalLogs().isEmpty());
    }

    @Test
    void nullSerialNumberShouldThrow() {
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
            new Drone(null, validModel);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void nullDroneModelShouldThrow() {
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
            new Drone(validSerial, null);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void changeDroneStatusShouldUpdateStatus() {
        Drone drone = new Drone(validSerial, validModel);
        drone.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
        assertEquals(DroneStatus.UNAVAILABLE, drone.droneStatus());
    }

    @Test
    void addDroneRemovalLogShouldIncreaseLogListSize() {
        Drone drone = new Drone(validSerial, validModel);
        DroneRemovalLog log = new DroneRemovalLog("Removed for maintenance");
        drone.addDroneRemovalLog(log);
        assertEquals(1, drone.droneRemovalLogs().size());
        assertTrue(drone.droneRemovalLogs().contains(log));
    }

    @Test
    void dronesWithSameIdentityShouldBeEqual() {
        Drone drone1 = new Drone(validSerial, validModel);
        Drone drone2 = new Drone(validSerial, validModel);
        assertEquals(drone1, drone2);
        assertEquals(drone1.hashCode(), drone2.hashCode());
    }

    @Test
    void dronesWithDifferentSerialsShouldNotBeEqual() {
        Drone drone1 = new Drone(new SerialNumber("SN-11111"), validModel);
        Drone drone2 = new Drone(new SerialNumber("SN-22222"), validModel);
        assertNotEquals(drone1, drone2);
    }

    @Test
    void hasIdentityShouldReturnTrueForMatchingSerial() {
        Drone drone = new Drone(validSerial, validModel);
        assertTrue(drone.hasIdentity("SN-12345"));
    }

    @Test
    void hasIdentityShouldReturnFalseForDifferentSerial() {
        Drone drone = new Drone(validSerial, validModel);
        assertFalse(drone.hasIdentity("SN-00000"));
    }

    @Test
    void toListStringShouldReturnSummary() {
        Drone drone = new Drone(validSerial, validModel);
        String listString = drone.toListString();
        assertTrue(listString.contains("SN-12345"));
        assertTrue(listString.contains("Phantom"));
        assertTrue(listString.contains("DM_001"));
    }
}

