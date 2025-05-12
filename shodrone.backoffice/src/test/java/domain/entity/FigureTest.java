package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FigureTest {

    private FigureCategory mockCategory;
    private Costumer mockCostumer;
    private Description mockDescription;

    private Figure figure;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockCategory = mock(FigureCategory.class);
        mockCostumer = mock(Costumer.class);
        mockDescription = mock(Description.class);

        // Initialize the figure object
        figure = new Figure("Airplane", mockDescription, 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, mockCostumer);
    }

    // ---- Creation Tests ----

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

    @Test
    void testUpdateFigureCategory() {
        FigureCategory newCategory = mock(FigureCategory.class);
        figure.UpdateFigureCategory(newCategory);
        assertEquals(newCategory, figure.category());
    }

    @Test
    void testUpdateFigureCostumer() {
        Costumer newCostumer = mock(Costumer.class);
        figure.UpdateFigureCostumer(newCostumer);
        assertEquals(newCostumer, figure.costumer());
    }

    @Test
    void testDecommissionFigureStatus() {
        assertEquals(FigureStatus.ACTIVE, figure.status());
        figure.decommissionFigureStatus();
        assertEquals(FigureStatus.INACTIVE, figure.status());
    }

    // ---- Equality and Identity Tests ----

    @Test
    void testEqualsWithItself() {
        assertTrue(figure.equals(figure));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(figure.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        assertFalse(figure.equals("not a figure"));
    }

    @Test
    void testHashCodeConsistency() {
        int hash1 = figure.hashCode();
        int hash2 = figure.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testDiference() {
        Figure sameFigure = new Figure("Airplane", mockDescription, 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, mockCostumer);
        figure.setFigureId(1L);
        sameFigure.setFigureId(2L);
        assertFalse(figure.equals(sameFigure));
    }

    @Test
    void testEquality() {
        Figure sameFigure = new Figure("Airplane", mockDescription, 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, mockCostumer);
        figure.setFigureId(1L);
        sameFigure.setFigureId(1L);
        assertTrue(figure.equals(sameFigure));
    }

    // ---- toString Tests ----

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
