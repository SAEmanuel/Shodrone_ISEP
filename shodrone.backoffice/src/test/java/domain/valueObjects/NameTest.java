package domain.valueObjects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    void validNameShouldBeAccepted() {
        Name name = new Name("João Silva");
        assertEquals("João Silva", name.toString());
        assertEquals("João Silva", name.name());
    }

    @Test
    void nameWithMaxLengthShouldBeAccepted() {
        String longName = "A".repeat(80);
        Name name = new Name(longName);
        assertEquals(longName, name.toString());
    }

    @Test
    void nameWithMinLengthShouldBeAccepted() {
        Name name = new Name("Ana");
        assertEquals("Ana", name.toString());
    }

    @Test
    void nullNameShouldThrow() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Name(null));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void emptyStringShouldThrow() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Name(""));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void onlyWhitespaceShouldThrow() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Name("     "));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void nameTooShortShouldThrow() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Name("Al"));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void nameTooLongShouldThrow() {
        String tooLongName = "A".repeat(81);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Name(tooLongName));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void invalidCharactersShouldThrow() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Name("J@ne Doe!"));
        assertNotNull(thrown.getMessage());
    }

    @Test
    void namesWithSameValueShouldBeEqual() {
        Name n1 = new Name("Maria Lopes");
        Name n2 = new Name("Maria Lopes");
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    void namesWithDifferentValuesShouldNotBeEqual() {
        Name n1 = new Name("Carlos");
        Name n2 = new Name("Carla");
        assertNotEquals(n1, n2);
    }

    @Test
    void toStringShouldReturnNameValue() {
        Name name = new Name("Manuel");
        assertEquals("Manuel", name.toString());
    }
}
