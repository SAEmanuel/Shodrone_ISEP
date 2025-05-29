package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DroneModelTest {

    @Test
    void validConstructionWithAllFields() {
        DroneModel model = new DroneModel(
                new DroneModelID("DM_001"),
                new DroneName("Falcon"),
                new Description("Fast drone"),
                20
        );
        assertEquals("Falcon", model.droneName().toString());
        assertEquals("Fast drone", model.description().toString());
        assertEquals(20, model.maxWindSpeed());
    }

    @Test
    void validConstructionWithDefaultDescription() {
        DroneModel model = new DroneModel(
                new DroneModelID("DM_002"),
                new DroneName("Hawk"),
                15
        );
        assertEquals("Hawk", model.droneName().toString());
        assertEquals("Description not provided!", model.description().toString());
        assertEquals(15, model.maxWindSpeed());
    }

    @Test
    void nullDroneModelIDShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DroneModel(null, new DroneName("Sky"), new Description("desc"), 10);
        });
    }

    @Test
    void nullDroneNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DroneModel(new DroneModelID("DM_003"), null, new Description("desc"), 10);
        });
    }

    @Test
    void nullDescriptionShouldThrow() {
        assertThrows(NullPointerException.class, () -> {
            new DroneModel(new DroneModelID("DM_004"), new DroneName("Wind"), null, 10);
        });
    }

    @Test
    void negativeMaxWindSpeedShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DroneModel(new DroneModelID("DM_005"), new DroneName("Storm"), new Description("desc"), -5);
        });
    }

    @Test
    void negativeMaxWindSpeedWithDefaultConstructorShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DroneModel(new DroneModelID("DM_006"), new DroneName("Breeze"), -1);
        });
    }
}
