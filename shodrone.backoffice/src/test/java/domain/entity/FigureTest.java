package domain.entity;

import domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DslMetadata;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FigureTest {

    private FigureCategory category;
    private Costumer customer;
    private Description description;
    private Name name;
    private Map<String, DslMetadata> dslVersions;
    private DslMetadata metadata;
    private Figure figure;

    @BeforeEach
    void setUp() {
        category = new FigureCategory(
                new Name("Geometry"),
                new Description("Geometric shapes"),
                new Email("designer@shodrone.app")
        );

        customer = new Costumer(
                Name.valueOf("Alice Martins"),
                eapli.framework.general.domain.model.EmailAddress.valueOf("alice@shodrone.app"),
                new PhoneNumber("913456789"),
                new NIF("123456789"),
                new Address("Main Street", "Lisbon", "1000-000", "Portugal")
        );

        description = new Description("An airplane shaped figure");
        name = new Name("Airplane");

        List<String> dslLines = List.of(
                "DSL version 1.0;",
                "DroneType TestDrone;",
                "Position p = (0,0,0);",
                "Line l1(p, (1,1,1));",
                "pause(2);"
        );
        metadata = new DslMetadata("TestDrone", dslLines);

        dslVersions = new HashMap<>();
        dslVersions.put("1.0", metadata);

        figure = new Figure(name, description, category,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                dslVersions, customer);
        figure.setFigureId(1L);
    }

    @Test
    void testCreationWithValidValues() {
        assertEquals("Airplane", figure.name().toString());
        assertEquals(description, figure.description());
        assertEquals(category, figure.category());
        assertEquals(FigureAvailability.PUBLIC, figure.availability());
        assertEquals(FigureStatus.ACTIVE, figure.status());
        assertEquals(customer, figure.customer());
        assertEquals(dslVersions.keySet(), figure.dslVersions().keySet());
        assertEquals("TestDrone", figure.dslVersions().get("1.0").getDroneModel());
        assertEquals(metadata.getDslLines(), figure.dslVersions().get("1.0").getDslLines());
    }

    @Test
    void testUpdateFigureCategory() {
        FigureCategory newCategory = new FigureCategory(
                new Name("Abstract"),
                new Description("Abstract patterns"),
                new Email("new@shodrone.app")
        );
        figure.updateFigureCategory(newCategory);
        assertEquals(newCategory, figure.category());
    }

    @Test
    void testUpdateCustomer() {
        Costumer newCustomer = new Costumer(
                Name.valueOf("Carlos Silva"),
                eapli.framework.general.domain.model.EmailAddress.valueOf("carlos@shodrone.app"),
                new PhoneNumber("917654321"),
                new NIF("248367080"),
                new Address("Avenida Nova", "Porto", "4000-000", "Portugal")
        );
        figure.updateCustomer(newCustomer);
        assertEquals(newCustomer, figure.customer());
    }

    @Test
    void testDecommission() {
        figure.decommission();
        assertEquals(FigureStatus.INACTIVE, figure.status());
    }

    @Test
    void testDecommissionThrowsIfAlreadyInactive() {
        figure.decommission();
        assertThrows(IllegalStateException.class, figure::decommission);
    }

    @Test
    void testEqualsWithSameId() {
        Figure other = new Figure(name, description, category,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                dslVersions, customer);
        other.setFigureId(1L);
        assertEquals(figure, other);
    }

    @Test
    void testNotEqualsDifferentId() {
        Figure other = new Figure(name, description, category,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                dslVersions, customer);
        other.setFigureId(2L);
        assertNotEquals(figure, other);
    }

    @Test
    void testSameAs() {
        Figure other = new Figure(name, description, category,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                dslVersions, customer);
        other.setFigureId(1L);
        assertTrue(figure.sameAs(other));
    }

    @Test
    void testSameAsReturnsFalseForDifferentId() {
        Figure other = new Figure(name, description, category,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                dslVersions, customer);
        other.setFigureId(99L);
        assertFalse(figure.sameAs(other));
    }

    @Test
    void testHashCodeConsistency() {
        int hash1 = figure.hashCode();
        int hash2 = figure.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testToStringNotNull() {
        assertNotNull(figure.toString());
        assertTrue(figure.toString().contains("Airplane"));
    }
}