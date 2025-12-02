package domain.valueObjects;

import domain.valueObjects.MaintenanceTypeName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaintenanceTypeNameTest {

    @Test
    void validNameShouldBeAccepted() {
        MaintenanceTypeName name = new MaintenanceTypeName("Motor Alignment");
        assertEquals("Motor Alignment", name.name());
    }

    @Test
    void validNameWithAccentsAndSymbolsShouldBeAccepted() {
        MaintenanceTypeName name = new MaintenanceTypeName("Árvore da Transmissão - Parte 1");
        assertEquals("Árvore da Transmissão - Parte 1", name.name());
    }

    @Test
    void emptyNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName(""));
    }

    @Test
    void whitespaceOnlyNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName("     "));
    }

    @Test
    void nullNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName(null));
    }

    @Test
    void nameTooShortShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName("ab"));
    }

    @Test
    void nameTooLongShouldThrowException() {
        String longName = "a".repeat(81);
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName(longName));
    }

    @Test
    void nameWithInvalidCharactersShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName("Invalid@Name"));
        assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName("Name!With#Symbol"));
    }

    @Test
    void equalsShouldReturnTrueForSameContent() {
        MaintenanceTypeName a = new MaintenanceTypeName("Valve Inspection");
        MaintenanceTypeName b = new MaintenanceTypeName("Valve Inspection");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsShouldReturnFalseForDifferentContent() {
        MaintenanceTypeName a = new MaintenanceTypeName("AAA");
        MaintenanceTypeName b = new MaintenanceTypeName("BBB");
        assertNotEquals(a, b);
    }

    @Test
    void valueOfShouldCreateValidInstance() {
        MaintenanceTypeName name = MaintenanceTypeName.valueOf("Battery Replacement");
        assertEquals("Battery Replacement", name.name());
    }

    @Test
    void toStringShouldReturnSameAsName() {
        MaintenanceTypeName name = new MaintenanceTypeName("Rotor Fix");
        assertEquals("Rotor Fix", name.toString());
    }
}
