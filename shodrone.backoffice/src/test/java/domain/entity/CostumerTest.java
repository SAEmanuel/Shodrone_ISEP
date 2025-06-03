package domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CostumerTest {

    private Name name;
    private EmailAddress email;
    private PhoneNumber phone;
    private NIF nif;
    private Address address;
    private Costumer costumer;

    @BeforeEach
    void setUp() {
        name = Name.valueOf("John Doe");
        email = EmailAddress.valueOf("john.doe@example.com");
        phone = new PhoneNumber("912345678");
        nif = new NIF("900000007");
        address = new Address("Rua Exemplo", "Lisboa", "1234-567", "Portugal");
        costumer = new Costumer(name, email, phone, nif, address);
    }

    @Test
    void testIdentityInitiallyNull() {
        assertNull(costumer.identity());
    }

    @Test
    void testName() {
        assertEquals(name, costumer.name());
    }

    @Test
    void testEmail() {
        assertEquals(email, costumer.email());
    }

    @Test
    void testPhoneNumber() {
        assertEquals(phone, costumer.phoneNumber());
    }

    @Test
    void testNif() {
        assertEquals(nif, costumer.nif());
    }

    @Test
    void testAddress() {
        assertEquals(address, costumer.address());
    }

    @Test
    void testEqualsSameNIF() {
        Costumer other = new Costumer(Name.valueOf("Another Name"),
                EmailAddress.valueOf("another@example.com"),
                new PhoneNumber("934567890"),
                new NIF("900000007"),
                new Address("Outra Rua", "Porto", "9999-999", "Portugal"));

        assertEquals(costumer, other);
    }

    @Test
    void testNotEqualsDifferentNIF() {
        Costumer other = new Costumer(Name.valueOf("Different Person"),
                EmailAddress.valueOf("different@example.com"),
                new PhoneNumber("912861312"),
                new NIF("245716084"),
                new Address("Outra Rua", "Braga", "8888-888", "Portugal"));

        assertNotEquals(costumer, other);
    }

    @Test
    void testHashCodeConsistentWithEquals() {
        Costumer other = new Costumer(name, email, phone, nif, address);
        assertEquals(costumer, other);
        assertEquals(costumer.hashCode(), other.hashCode());
    }

    @Test
    void testToStringContainsNameAndEmail() {
        String result = costumer.toString();
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("john.doe@example.com"));
    }
}
