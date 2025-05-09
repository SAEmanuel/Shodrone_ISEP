package domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneNameTest {

    @Test
    void validDroneNameShouldBeCreated() {
        DroneName name = new DroneName("Phantom 4 Pro");
        assertEquals("Phantom 4 Pro", name.name());
        assertEquals("Phantom 4 Pro", name.toString());
    }

    @Test
    void valueOfShouldReturnEquivalentInstance() {
        DroneName name1 = DroneName.valueOf("Inspire 2");
        DroneName name2 = new DroneName("Inspire 2");

        assertEquals(name1, name2);
    }

    @Test
    void nullNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneName(null));
    }

    @Test
    void emptyNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneName(""));
    }

    @Test
    void whitespaceOnlyNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneName("   "));
    }

    @Test
    void tooShortNameShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneName("DJ"));
    }

    @Test
    void tooLongNameShouldThrow() {
        String longName = "X".repeat(81);
        assertThrows(IllegalArgumentException.class, () -> new DroneName(longName));
    }

    @Test
    void invalidCharactersShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneName("Drone@123"));
    }

    @Test
    void nameWithUnicodeLettersShouldBeAccepted() {
        DroneName name = new DroneName("Droné Número Único");
        assertEquals("Droné Número Único", name.name());
    }

    @Test
    void equalsShouldWorkCorrectly() {
        DroneName name1 = new DroneName("Mavic Air 2");
        DroneName name2 = new DroneName("Mavic Air 2");
        DroneName name3 = new DroneName("Skydio 2");

        assertEquals(name1, name2);
        assertNotEquals(name1, name3);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        DroneName name1 = new DroneName("Anafi USA");
        DroneName name2 = new DroneName("Anafi USA");

        assertEquals(name1.hashCode(), name2.hashCode());
    }
}
