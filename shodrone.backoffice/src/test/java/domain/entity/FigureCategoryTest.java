package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.Name;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FigureCategoryTest {

    private final Email creator = new Email("test@shodrone.app");

    // ---- Creation Tests ----

    @Test
    void testCreationWithValidValues() {
        FigureCategory category = new FigureCategory(new Name("Test Category"), new Description("Valid description"), creator);
        assertEquals("Test Category", category.identity());
        assertTrue(category.isAvailable());
        assertEquals("Valid description", category.description().toString());
    }

    @Test
    void testConstructorWithOnlyNameAndEmail() {
        FigureCategory category = new FigureCategory(new Name("Minimal Category"), creator);
        assertEquals("Minimal Category", category.identity());
        assertEquals("Description not provided!", category.description().toString());
        assertTrue(category.isAvailable());
    }

    @Test
    void testCreationWithNullEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FigureCategory(new Name("Invalid"), new Description("desc"), null);
        });
    }

    // ---- State Toggle Tests ----

    @Test
    void testToggleStateChangesActiveStatus() {
        FigureCategory category = new FigureCategory(new Name("Switchable"), new Description("Toggle state test"), creator);
        assertTrue(category.isAvailable());
        category.toggleState();
        assertFalse(category.isAvailable());
        category.toggleState();
        assertTrue(category.isAvailable());
    }

    // ---- Update Methods ----

    @Test
    void testUpdateName() {
        FigureCategory category = new FigureCategory(new Name("Old Name"), new Description("description"), creator);
        category.changeCategoryNameTo(new Name("New Name"));
        assertEquals("New Name", category.identity());
    }

    @Test
    void testUpdateDescription() {
        FigureCategory category = new FigureCategory(new Name("Name"), new Description("Old description"), creator);
        category.changeDescriptionTo(new Description("New description"));
        assertEquals("New description", category.description().toString());
    }

    @Test
    void testChangeNameToInvalidThrowsException() {
        FigureCategory category = new FigureCategory(new Name("Valid"), new Description("description"), creator);
        assertThrows(IllegalArgumentException.class, () -> {
            category.changeCategoryNameTo(new Name("  "));
        });
    }

    @Test
    void testChangeDescriptionToInvalidThrowsException() {
        FigureCategory category = new FigureCategory(new Name("Valid"), new Description("description"), creator);
        assertThrows(IllegalArgumentException.class, () -> {
            category.changeDescriptionTo(new Description("   "));
        });
    }

    // ---- Audit Field Tests ----

    @Test
    void testUpdateTimeChangesUpdatedOn() {
        FigureCategory category = new FigureCategory(new Name("Name"), new Description("description"), creator);
        assertNull(category.updatedOn());
        category.updateTime();
        assertNotNull(category.updatedOn());
        assertTrue(category.updatedOn().isBefore(LocalDateTime.now().plusSeconds(2)));
    }

    // ---- Identity and Equality ----

    @Test
    void testIdentityAndHasIdentity() {
        FigureCategory category = new FigureCategory(new Name("Unique"), new Description("description"), creator);
        assertTrue(category.hasIdentity("Unique"));
        assertFalse(category.hasIdentity("Another"));
    }

    @Test
    void testEquality() {
        FigureCategory category1 = new FigureCategory(new Name("Equal"), new Description("Same Description"), creator);
        FigureCategory category2 = new FigureCategory(new Name("Equal"), new Description("Same Description"), creator);
        assertEquals(category1, category2);
    }

    @Test
    void testEqualsWithItself() {
        FigureCategory category = new FigureCategory(new Name("Self"), new Description("Description"), creator);
        assertTrue(category.equals(category));
    }

    @Test
    void testEqualsWithNull() {
        FigureCategory category = new FigureCategory(new Name("NullCheck"), new Description("Description"), creator);
        assertFalse(category.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        FigureCategory category = new FigureCategory(new Name("DiffClass"), new Description("Description"), creator);
        assertFalse(category.equals("not a category"));
    }

    @Test
    void testSameAs() {
        FigureCategory category1 = new FigureCategory(new Name("Cat"), new Description("Description"), creator);
        FigureCategory category2 = new FigureCategory(new Name("Cat"), new Description("Description"), creator);
        assertTrue(category1.sameAs(category2));
    }

    @Test
    void testSameAsDifferentCategory() {
        FigureCategory category1 = new FigureCategory(new Name("FirstCat"), new Description("Description1"), creator);
        FigureCategory category2 = new FigureCategory(new Name("SecondCat"), new Description("Description2"), creator);
        assertFalse(category1.sameAs(category2));
    }

    @Test
    void testHashCodeConsistency() {
        FigureCategory category = new FigureCategory(new Name("Hash"), new Description("Value"), creator);
        int hash1 = category.hashCode();
        int hash2 = category.hashCode();
        assertEquals(hash1, hash2);
    }

    // ---- toString Tests ----

    @Test
    void testToStringActive() {
        FigureCategory category = new FigureCategory(new Name("Display"), new Description("Active status"), creator);
        String output = category.toString();
        assertTrue(output.contains("ACTIVE"));
    }

    @Test
    void testToStringInactive() {
        FigureCategory category = new FigureCategory(new Name("Display"), new Description("Inactive status"), creator);
        category.toggleState();
        String output = category.toString();
        assertTrue(output.contains("INACTIVE"));
    }
}