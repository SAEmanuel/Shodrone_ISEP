package domain.valueObjects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class NIFTest {

    @Test
    void validNIFShouldBeAccepted() {
        NIF nif = new NIF("123456789");  // check digit 9 is valid
        assertEquals("123456789", nif.getNif());
        assertEquals("123456789", nif.toString());
    }

    @Test
    void validNIFWithLeadingDigit1ShouldBeAccepted() {
        NIF nif = new NIF("123456789");
        assertNotNull(nif);
    }

    @Test
    void validNIFWithLeadingDigit2ShouldBeAccepted() {
        NIF nif = new NIF("245716084");
        assertNotNull(nif);
    }

    @Test
    void validNIFWithLeadingDigit9ShouldBeAccepted() {
        NIF nif = new NIF("900000007");
        assertNotNull(nif);
    }


    @Test
    void nifWithInvalidLengthShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new NIF("12345678")); // 8 digits
        assertThrows(IllegalArgumentException.class, () -> new NIF("1234567890")); // 10 digits
    }

    @Test
    void nifWithNonDigitsShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new NIF("12A45678B"));
        assertThrows(IllegalArgumentException.class, () -> new NIF("12345!789"));
    }

    @Test
    void nifWithInvalidCheckDigitShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new NIF("123456780")); // check digit incorrect
    }

    @Test
    void nifWithInvalidStartingDigitShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new NIF("423456789")); // starts with 4
        assertThrows(IllegalArgumentException.class, () -> new NIF("703456789")); // starts with 7
    }

    @Test
    void equalNIFObjectsShouldBeEqual() {
        NIF nif1 = new NIF("123456789");
        NIF nif2 = new NIF("123456789");
        assertEquals(nif1, nif2);
        assertEquals(nif1.hashCode(), nif2.hashCode());
    }

    @Test
    void differentNIFObjectsShouldNotBeEqual() {
        NIF nif1 = new NIF("123456789");
        NIF nif2 = new NIF("245716084");
        assertNotEquals(nif1, nif2);
    }

    @Test
    void nifShouldNotBeEqualToNullOrDifferentType() {
        NIF nif = new NIF("123456789");
        assertNotEquals(null, nif);
        assertNotEquals("123456789", nif); // comparing with string
    }
}
