package domain.valueObjects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LocationTest {

    private Address dummyAddress() {
        return new Address("Street", "City", "1234-567", "Country");
    }

    @Test
    public void validLocationShouldBeCreated() {
        Location loc = new Location(dummyAddress(), 40.0, -8.0, "Main entrance");
        assertNotNull(loc);
        assertEquals("Main entrance", loc.description());
        assertEquals(40.0, loc.latitude());
        assertEquals(-8.0, loc.longitude());
        assertEquals(dummyAddress(), loc.address());
    }

    @Test
    public void nullAddressShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(null, 40.0, -8.0, "Desc");
        });
    }

    @Test
    public void invalidLatitudeShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(dummyAddress(), -91.0, 0, "Desc");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(dummyAddress(), 91.0, 0, "Desc");
        });
    }

    @Test
    public void invalidLongitudeShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(dummyAddress(), 0, -181.0, "Desc");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(dummyAddress(), 0, 181.0, "Desc");
        });
    }

    @Test
    public void nullDescriptionShouldBeAllowed() {
        Location loc = new Location(dummyAddress(), 0, 0, null);
        assertNull(loc.description());
    }

    @Test
    public void blankDescriptionShouldBeTrimmedToNull() {
        Location loc = new Location(dummyAddress(), 0, 0, "   ");
        assertNull(loc.description());
    }

    @Test
    public void descriptionShouldBeTrimmed() {
        Location loc = new Location(dummyAddress(), 0, 0, "   Test Location   ");
        assertEquals("Test Location", loc.description());
    }

    @Test
    public void equalsAndHashCodeShouldWork() {
        Location l1 = new Location(dummyAddress(), 40.0, -8.0, "Description");
        Location l2 = new Location(dummyAddress(), 40.0, -8.0, "Description");
        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
    }
}
