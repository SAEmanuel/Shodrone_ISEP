package authz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    // ---- Creation Tests ----

    @Test
    void validEmailIsAccepted() {
        Email email = new Email("user@shodrone.app");
        assertEquals("user@shodrone.app", email.getEmail());
    }

    @Test
    void invalidEmailFormatThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Email("invalid-email"));
    }

    @Test
    void emailWithoutShodroneDomainThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Email("user@gmail.com"));
    }

    @Test
    void blankEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Email("   "));
    }

    @Test
    void nullEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    // ---- equals, hashCode, toString ----

    @Test
    void testEqualsSameObject() {
        Email email = new Email("same@shodrone.app");
        assertEquals(email, email);
    }

    @Test
    void testEqualsDifferentObjectSameValue() {
        Email e1 = new Email("match@shodrone.app");
        Email e2 = new Email("match@shodrone.app");
        assertEquals(e1, e2);
    }

    @Test
    void testEqualsNull() {
        Email email = new Email("null@shodrone.app");
        assertNotEquals(null, email);
    }

    @Test
    void testEqualsDifferentClass() {
        Email email = new Email("diff@shodrone.app");
        assertNotEquals(email, "diff@shodrone.app");
    }

    @Test
    void testEqualsDifferentEmails() {
        Email e1 = new Email("one@shodrone.app");
        Email e2 = new Email("two@shodrone.app");
        assertNotEquals(e1, e2);
    }

    @Test
    void testHashCodeConsistency() {
        Email email = new Email("hash@shodrone.app");
        assertEquals(email.hashCode(), email.hashCode());
    }

    @Test
    void testToStringReturnsEmail() {
        Email email = new Email("view@shodrone.app");
        assertEquals("view@shodrone.app", email.toString());
    }
}