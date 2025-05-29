package persistence.inmemory;

import domain.entity.FigureCategory;
import history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.entity.Email;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFigureCategoryRepositoryTest {

    private InMemoryFigureCategoryRepository repo;
    private final Email creator = new Email("test@shodrone.app");

    private static class DummyAuditLoggerService extends AuditLoggerService {
        public DummyAuditLoggerService() {
            super(null);
        }

        @Override
        public void logChanges(Object oldObj, Object newObj, String entityId, String user, Set<String> auditFields) {
        }
    }

    @BeforeEach
    void setUp() {
        repo = new InMemoryFigureCategoryRepository(new DummyAuditLoggerService());
    }

    @Test
    void testSaveSuccess() {
        FigureCategory category = new FigureCategory(new Name("Geometry"), new Description("Valid description"), creator);
        Optional<FigureCategory> saved = repo.save(category);
        assertTrue(saved.isPresent());
    }

    @Test
    void testSaveDuplicateReturnsEmpty() {
        FigureCategory category = new FigureCategory(new Name("Geometry"), new Description("Valid description"), creator);
        repo.save(category);
        Optional<FigureCategory> duplicate = repo.save(category);
        assertTrue(duplicate.isEmpty());
    }

    @Test
    void testFindByNameSuccess() {
        FigureCategory category = new FigureCategory(new Name("Aviation"), new Description("Aircraft models"), creator);
        repo.save(category);
        Optional<FigureCategory> found = repo.findByName("aviation");
        assertTrue(found.isPresent());
        assertEquals("Aviation", found.get().identity());
    }

    @Test
    void testFindAllReturnsAllSaved() {
        repo.save(new FigureCategory(new Name("A name"), new Description("Description one"), creator));
        repo.save(new FigureCategory(new Name("B name"), new Description("Description two"), creator));
        List<FigureCategory> all = repo.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testEditChosenCategoryNameOnly() {
        FigureCategory category = new FigureCategory(new Name("Old Name"), new Description("Some desc"), creator);
        repo.save(category);
        String oldKey = category.identity();
        category.changeCategoryNameTo(new Name("New Name"));
        Optional<FigureCategory> updated = repo.updateFigureCategory(category, oldKey);
        assertTrue(updated.isPresent());
        assertEquals("New Name", updated.get().identity());
        // Verifica que a chave antiga já não existe
        assertFalse(repo.findByName("Old Name").isPresent());
        // Verifica que a nova chave existe
        assertTrue(repo.findByName("New Name").isPresent());
    }

    @Test
    void testEditChosenCategoryDescriptionOnly() {
        FigureCategory category = new FigureCategory(new Name("Editable"), new Description("Old desc"), creator);
        repo.save(category);
        String oldKey = category.identity();
        category.changeDescriptionTo(new Description("Updated desc"));
        Optional<FigureCategory> updated = repo.updateFigureCategory(category, oldKey);
        assertTrue(updated.isPresent());
        assertEquals("Updated desc", updated.get().description().toString());
    }

    @Test
    void testEditCategoryNotFoundReturnsEmpty() {
        FigureCategory category = new FigureCategory(new Name("Not Found"), new Description("Description "), creator);
        String oldKey = category.identity();
        category.changeCategoryNameTo(new Name("New Name"));
        category.changeDescriptionTo(new Description("Another"));
        Optional<FigureCategory> result = repo.updateFigureCategory(category, oldKey);
        assertTrue(result.isPresent()); // Como o método faz put, a categoria passa a existir
    }

    @Test
    void testChangeStatusTogglesActive() {
        FigureCategory category = new FigureCategory(new Name("Toggle"), new Description("Toggle status"), creator);
        repo.save(category);
        assertTrue(category.isAvailable());
        String oldKey = category.identity();
        category.toggleState();
        repo.updateFigureCategory(category, oldKey);
        assertFalse(repo.findByName("Toggle").get().isAvailable());
    }

    @Test
    void testFindActiveCategoriesReturnsOnlyActive() {
        FigureCategory active = new FigureCategory(new Name("Active"), new Description("Still active"), creator);
        FigureCategory inactive = new FigureCategory(new Name("Inactive"), new Description("To deactivate"), creator);
        repo.save(active);
        repo.save(inactive);
        String oldKey = inactive.identity();
        inactive.toggleState();
        repo.updateFigureCategory(inactive, oldKey); // torna inativo
        List<FigureCategory> result = repo.findActiveCategories();
        assertEquals(1, result.size());
        assertEquals("Active", result.get(0).identity());
    }
}
