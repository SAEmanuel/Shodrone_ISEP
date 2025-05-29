package domain.valueObjects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void testValidAddressCreation() {
        Address address = new Address("Rua Central", "Lisboa", "1000-123", "Portugal");
        assertEquals("Rua Central", address.street());
        assertEquals("Lisboa", address.city());
        assertEquals("1000-123", address.postalCode());
        assertEquals("Portugal", address.country());
    }

    @Test
    void testInvalidPostalCodeThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Address("Rua Central", "Lisboa", "1234", "Portugal")
        );
        assertEquals("Postal code must match Portuguese format: NNNN-NNN", exception.getMessage());
    }

    @Test
    void testNullFieldsThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(null, "Lisboa", "1000-123", "Portugal"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua Central", null, "1000-123", "Portugal"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua Central", "Lisboa", null, "Portugal"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua Central", "Lisboa", "1000-123", null));
    }

    @Test
    void testEmptyFieldsThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(" ", "Lisboa", "1000-123", "Portugal"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua Central", "", "1000-123", "Portugal"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua Central", "Lisboa", "   ", "Portugal"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Rua Central", "Lisboa", "1000-123", ""));
    }

    @Test
    void testToStringFormat() {
        Address address = new Address("Rua A", "Porto", "4000-234", "Portugal");
        String expected = "Rua A, Porto, 4000-234 Portugal";
        assertEquals(expected, address.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Address a1 = new Address("Rua A", "Porto", "4000-234", "Portugal");
        Address a2 = new Address("Rua A", "Porto", "4000-234", "Portugal");
        Address a3 = new Address("Rua B", "Lisboa", "1000-123", "Portugal");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, a3);
    }
}
