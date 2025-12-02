package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaintenanceTypeTest {

    MaintenanceTypeName validName(String name) {
        return new MaintenanceTypeName(name);
    }

    Description validDesc(String desc) {
        return new Description(desc);
    }

    @Test
    void constructorWithValidArgsShouldSucceed() {
        MaintenanceType type = new MaintenanceType(validName("Camera"), validDesc("Lens fix"));
        assertEquals("Camera", type.name().name());
        assertEquals("Lens fix", type.description().toString());
    }

    @Test
    void constructorWithNameOnlyShouldUseDefaultDescription() {
        MaintenanceType type = new MaintenanceType(validName("Sensor"));
        assertEquals("Sensor", type.name().name());
        assertEquals("Not Provided!", type.description().toString());
    }

    @Test
    void constructorWithEmptyNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceType(validName(""), validDesc("Something")));
    }

    @Test
    void constructorWithEmptyDescriptionShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceType(validName("GPS"), new Description("")));
    }

    @Test
    void changeDescriptionShouldUpdateValue() {
        MaintenanceType type = new MaintenanceType(validName("Motor"), validDesc("Old desc"));
        type.changeDescriptionTo(validDesc("New desc"));
        assertEquals("New desc", type.description().toString());
    }

    @Test
    void changeDescriptionToNullShouldThrow() {
        MaintenanceType type = new MaintenanceType(validName("Motor"), validDesc("Old desc"));
        assertThrows(IllegalArgumentException.class, () -> type.changeDescriptionTo(null));
    }

    @Test
    void changeNameShouldUpdateValue() {
        MaintenanceType type = new MaintenanceType(validName("OldName"), validDesc("Something"));
        type.changeNameTo(validName("NewName"));
        assertEquals("NewName", type.name().name());
    }

    @Test
    void changeNameToNullShouldThrow() {
        MaintenanceType type = new MaintenanceType(validName("OldName"), validDesc("Something"));
        assertThrows(IllegalArgumentException.class, () -> type.changeNameTo(null));
    }

    @Test
    void identityShouldReturnName() {
        MaintenanceType type = new MaintenanceType(validName("TestName"), validDesc("Not Provided!"));
        assertEquals("TestName", type.identity());
    }

    @Test
    void hasIdentityShouldIgnoreCase() {
        MaintenanceType type = new MaintenanceType(validName("Drone"), validDesc("Fix blades"));
        assertTrue(type.hasIdentity("drone"));
        assertTrue(type.hasIdentity("DRONE"));
    }

    @Test
    void sameAsShouldReturnTrueForSameData() {
        MaintenanceType type1 = new MaintenanceType(validName("XXX"), validDesc("Descc"));
        MaintenanceType type2 = new MaintenanceType(validName("XXX"), validDesc("Descc"));
        assertTrue(type1.sameAs(type2));
    }

    @Test
    void sameAsShouldReturnFalseForDifferentNameOrDescription() {
        MaintenanceType a = new MaintenanceType(validName("AAA"), validDesc("XXXXX"));
        MaintenanceType b = new MaintenanceType(validName("BBB"), validDesc("XXXXX"));
        MaintenanceType c = new MaintenanceType(validName("AAA"), validDesc("YYYYY"));
        assertFalse(a.sameAs(b));
        assertFalse(a.sameAs(c));
    }

    @Test
    void copyShouldCreateEqualButDifferentObject() {
        MaintenanceType original = new MaintenanceType(validName("Rotor"), validDesc("Stabilization"));
        MaintenanceType copy = original.copy();

        assertNotSame(original, copy);
        assertEquals(original.name(), copy.name());
        assertEquals(original.description(), copy.description());
    }

}
