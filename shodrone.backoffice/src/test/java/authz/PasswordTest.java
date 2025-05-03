package authz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    // ---- Constructor and Validation ----

    @Test
    void validPasswordCreatesInstance() {
        Password p = new Password("Pass123#4");
        assertNotNull(p.getPassword());
        assertTrue(p.getPassword().startsWith("$2a$")); // BCrypt prefix
    }

    @Test
    void invalidPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Password("short"));
    }

    @Test
    void validateAcceptsValidPassword() {
        assertTrue(Password.validate("Strong123!"));
    }

    @Test
    void validateRejectsNull() {
        assertFalse(Password.validate(null));
    }

    @Test
    void validateRejectsBlank() {
        assertFalse(Password.validate("   "));
    }

    @Test
    void validateRejectsMissingUppercase() {
        assertFalse(Password.validate("lowercase123#"));
    }

    @Test
    void validateRejectsMissingDigits() {
        assertFalse(Password.validate("Password!"));
    }

    @Test
    void validateRejectsLessThanThreeDigits() {
        assertFalse(Password.validate("Pass12!"));
    }

    @Test
    void validateRejectsMissingSpecialChar() {
        assertFalse(Password.validate("Password123"));
    }

    // ---- checkPassword ----

    @Test
    void checkPasswordReturnsTrueForCorrectPassword() {
        Password p = new Password("MyPass123@");
        assertTrue(p.checkPassword("MyPass123@"));
    }

    @Test
    void checkPasswordReturnsFalseForIncorrectPassword() {
        Password p = new Password("MyPass123@");
        assertFalse(p.checkPassword("WrongPass123@"));
    }

    @Test
    void checkPasswordReturnsFalseForNullInput() {
        Password p = new Password("Valid123$");
        assertFalse(p.checkPassword(null));
    }

    @Test
    void checkPasswordReturnsFalseForBlankInput() {
        Password p = new Password("Valid123$");
        assertFalse(p.checkPassword("   "));
    }

    // ---- equals, hashCode, toString ----

    @Test
    void testEqualsSameObject() {
        Password p = new Password("Test123#");
        assertEquals(p, p);
    }

    @Test
    void testEqualsNull() {
        Password p = new Password("Test123#");
        assertNotEquals(null, p);
    }

    @Test
    void testEqualsDifferentClass() {
        Password p = new Password("Test123#");
        assertNotEquals(p, "not a password");
    }

    @Test
    void testEqualsDifferentObjectSameHash() {
        Password p1 = new Password("SamePass123!");
        Password p2 = new Password("SamePass123!");
        assertNotEquals(p1, p2); // Different salt → different hash
    }

    @Test
    void testHashCodeConsistency() {
        Password p = new Password("Hash123#");
        assertEquals(p.hashCode(), p.hashCode());
    }

    @Test
    void testToStringReturnsHashedValue() {
        Password p = new Password("ToStr123!");
        assertTrue(p.toString().startsWith("$2a$"));
    }

}