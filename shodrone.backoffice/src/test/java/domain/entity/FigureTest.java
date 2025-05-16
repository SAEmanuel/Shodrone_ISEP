package domain.entity;

import domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit test class for the Figure entity.
 * Tests creation, updates, equality, identity, and string representation.
 */
class FigureTest {

    private FigureCategory mockCategory;
    private Costumer mockCostumer;
    private Description mockDescription;

    // Instance of Figure to be tested
    private Figure figure;

    /**
     * Setup method executed before each test.
     * Initializes mocks and creates a new Figure instance with mocked dependencies.
     */
    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockCategory = mock(FigureCategory.class);
        mockCostumer = mock(Costumer.class);
        mockDescription = mock(Description.class);

        // Initialize the figure object
        figure = new Figure(new Name("Airplane"), mockDescription, 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("Input.txt"), mockCostumer);
    }

    // ---- Creation Tests ----
    /**
     * Verifies that the Figure is correctly created with valid initial values.
     */
    @Test
    void testCreationWithValidValues() {
        assertEquals("Airplane", figure.name());
        assertEquals(mockDescription, figure.description());
        assertEquals(1L, figure.version());
        assertEquals(mockCategory, figure.category());
        assertEquals(FigureAvailability.PUBLIC, figure.availability());
        assertEquals(FigureStatus.ACTIVE, figure.status());
        assertEquals(mockCostumer, figure.costumer());
    }

    // ---- Update Methods ----
    /**
     * Tests updating the Figure's category and verifies it changes accordingly.
     */
    @Test
    void testUpdateFigureCategory() {
        FigureCategory newCategory = mock(FigureCategory.class);
        figure.UpdateFigureCategory(newCategory);
        assertEquals(newCategory, figure.category());
    }

    /**
     * Tests updating the Figure's costumer (owner) and verifies it changes accordingly.
     */
    @Test
    void testUpdateFigureCostumer() {
        Costumer newCostumer = mock(Costumer.class);
        figure.UpdateFigureCostumer(newCostumer);
        assertEquals(newCostumer, figure.costumer());
    }

    /**
     * Tests decommissioning the Figure by setting its status to INACTIVE.
     */
    @Test
    void testDecommissionFigureStatus() {
        assertEquals(FigureStatus.ACTIVE, figure.status());
        figure.decommissionFigureStatus();
        assertEquals(FigureStatus.INACTIVE, figure.status());
    }

    // ---- Equality and Identity Tests ----
    /**
     * Verifies that a Figure equals itself.
     */
    @Test
    void testEqualsWithItself() {
        assertTrue(figure.equals(figure));
    }

    /**
     * Verifies that a Figure is not equal to null.
     */
    @Test
    void testEqualsWithNull() {
        assertFalse(figure.equals(null));
    }

    /**
     * Verifies that a Figure is not equal to an object of a different class.
     */
    @Test
    void testEqualsWithDifferentClass() {
        assertFalse(figure.equals("not a figure"));
    }

    /**
     * Verifies that the hashCode method returns consistent values.
     */
    @Test
    void testHashCodeConsistency() {
        int hash1 = figure.hashCode();
        int hash2 = figure.hashCode();
        assertEquals(hash1, hash2);
    }

    /**
     * Verifies that Figures with different IDs are not equal.
     */
    @Test
    void testDiference() {
        Figure sameFigure = new Figure(new Name("Airplane"), mockDescription, 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("Input.txt"), mockCostumer);
        figure.setFigureId(1L);
        sameFigure.setFigureId(2L);
        assertFalse(figure.equals(sameFigure));
    }

    /**
     * Verifies that Figures with the same ID are equal.
     */
    @Test
    void testEquality() {
        Figure sameFigure = new Figure(new Name("Airplane"), mockDescription, 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("Input.txt"), mockCostumer);
        figure.setFigureId(1L);
        sameFigure.setFigureId(1L);
        assertTrue(figure.equals(sameFigure));
    }

    // ---- toString Tests ----
    /**
     * Tests that the toString method returns the expected formatted string.
     */
    @Test
    void testToString() {
        String expectedString = String.format(
                "ID: %-3s | Name: %-20s | Description: %-30s | Version: %-6s | Category: %-20s | Status: %-6s | Availability: %-10s | Costumer: %-20s",
                figure.identity() != null ? figure.identity() : "N/A",
                figure.name() != null ? figure.name() : "N/A",
                figure.description() != null ? figure.description() : "N/A",
                figure.version() != null ? figure.version().toString() : "N/A",
                figure.category() != null ? figure.category().identity() : "N/A",
                figure.status() != null ? figure.status() : "N/A",
                figure.availability() != null ? figure.availability() : "N/A",
                figure.costumer() != null ? figure.costumer().name() : "N/A"
        );

        assertEquals(expectedString, figure.toString());
    }

}
