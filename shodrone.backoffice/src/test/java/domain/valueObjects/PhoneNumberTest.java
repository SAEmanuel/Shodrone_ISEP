package domain.valueObjects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;


class PhoneNumberTest {

    // Valid Mobile Numbers (start with 91, 92, 93, or 96)
    @Test
    void validMobileNumberShouldBeAccepted() {
        assertDoesNotThrow(() -> new PhoneNumber("912345678"));
        assertDoesNotThrow(() -> new PhoneNumber("923456789"));
        assertDoesNotThrow(() -> new PhoneNumber("933456789"));
        assertDoesNotThrow(() -> new PhoneNumber("963456789"));
    }

    // Valid Landline Numbers (start with 2, 9 digits total)
    @Test
    void validLandlineNumberShouldBeAccepted() {
        assertDoesNotThrow(() -> new PhoneNumber("212345678")); // Lisbon
        assertDoesNotThrow(() -> new PhoneNumber("256123456")); // Aveiro
    }

    @Test
    void invalidNumberWithWrongPrefixShouldThrowException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("801234567"));
        assertEquals("Invalid Portuguese phone number: 801234567", ex.getMessage());
    }

    @Test
    void invalidNumberWithTooFewDigitsShouldThrowException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("9123456"));
        assertEquals("Invalid Portuguese phone number: 9123456", ex.getMessage());
    }

    @Test
    void invalidNumberWithNonDigitsShouldThrowException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("91A34B678"));
        assertEquals("Invalid Portuguese phone number: 91A34B678", ex.getMessage());
    }

    @Test
    void nullNumberShouldThrowException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(null));
        assertEquals("Invalid Portuguese phone number: null", ex.getMessage());
    }

    @Test
    void phoneNumberShouldBeFormattedInternationally() {
        PhoneNumber phone = new PhoneNumber("912345678");
        assertEquals("+351 912 345 678", phone.formatInternational());
    }


    @Test
    void formatInternationalShouldReturnEmptyStringWhenNumberIsNull() throws Exception {
        PhoneNumber phone = new PhoneNumber("912345678");

        Field field = PhoneNumber.class.getDeclaredField("number");
        field.setAccessible(true);
        field.set(phone, null);

        assertEquals("", phone.formatInternational());
    }


    @Test
    void toStringShouldReturnRawNumber() {
        PhoneNumber phone = new PhoneNumber("923456789");
        assertEquals("923456789", phone.toString());
    }

    @Test
    void equalsAndHashCodeShouldWorkProperly() {
        PhoneNumber phone1 = new PhoneNumber("963456789");
        PhoneNumber phone2 = new PhoneNumber("963456789");
        PhoneNumber phone3 = new PhoneNumber("912345678");

        assertEquals(phone1, phone2);
        assertEquals(phone1.hashCode(), phone2.hashCode());

        assertNotEquals(phone1, phone3);
        assertNotEquals(phone1.hashCode(), phone3.hashCode());
    }
}
