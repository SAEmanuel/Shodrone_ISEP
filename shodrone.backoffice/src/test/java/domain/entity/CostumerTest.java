package domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CostumerTest {

    private Name name;
    private EmailAddress email;
    private PhoneNumber phone;
    private NIF nif;
    private Address address;
    private Costumer costumer;

    @BeforeEach
    void setUp() {
        Name.valueOf("John", "Doe");
        email = EmailAddress.valueOf("john.doe@example.com");
        phone = new PhoneNumber("912345678");
        nif = new NIF("123456789");
        address = new Address("Rua Exemplo", "1234-567", "Lisboa", "Portugal");
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
        Costumer other = new Costumer(Name.valueOf("Another", "Name"),
                EmailAddress.valueOf("another@example.com"),
                new PhoneNumber("934567890"),
                new NIF("123456789"),
                new Address("Outra Rua", "9999-999", "Porto", "Portugal"));

        assertEquals(costumer, other);
    }

    @Test
    void testNotEqualsDifferentNIF() {
        Costumer other = new Costumer(Name.valueOf("Different", "Person"),
                EmailAddress.valueOf("different@example.com"),
                new PhoneNumber("900000000"),
                new NIF("987654321"),
                new Address("Outra Rua", "8888-888", "Braga", "Portugal"));

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
