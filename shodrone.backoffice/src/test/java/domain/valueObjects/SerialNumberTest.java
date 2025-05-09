package domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SerialNumberTest {

    @Test
    void validSerialNumberShouldBeAccepted() {
        SerialNumber sn = new SerialNumber("SN-12345");
        assertEquals("SN-12345", sn.getSerialNumber());
        assertEquals("SN-12345", sn.toString());
    }

    @Test
    void nullSerialNumberShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber(null);
        });
    }

    @Test
    void emptySerialNumberShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber("");
        });
    }

    @Test
    void whitespaceOnlySerialNumberShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber("        ");
        });
    }

    @Test
    void serialNumberWithWrongLengthShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber("SN-1234"); // 7 characters
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber("SN-123456"); // 9 characters
        });
    }

    @Test
    void serialNumberWithInvalidPrefixShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber("XX-12345");
        });
    }

    @Test
    void serialNumberWithSpecialCharactersShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber("SN-12@45");
        });
    }

    @Test
    void serialNumberWithLettersShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber("SN-12A45");
        });
    }

    @Test
    void serialNumberWithSpaceShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SerialNumber("SN-1 345");
        });
    }

    @Test
    void equalSerialNumbersShouldBeEqual() {
        SerialNumber sn1 = new SerialNumber("SN-12345");
        SerialNumber sn2 = new SerialNumber("SN-12345");
        assertEquals(sn1, sn2);
        assertEquals(sn1.hashCode(), sn2.hashCode());
    }

    @Test
    void differentSerialNumbersShouldNotBeEqual() {
        SerialNumber sn1 = new SerialNumber("SN-12345");
        SerialNumber sn2 = new SerialNumber("SN-54321");
        assertNotEquals(sn1, sn2);
    }
}
