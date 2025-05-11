package persistence.inmemory;

import domain.history.AuditLogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAuditLogRepositoryTest {

    private InMemoryAuditLogRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryAuditLogRepository();
    }

    @Test
    void testSaveAndFindByEntity() {
        AuditLogEntry entry = new AuditLogEntry(
                "FigureCategory",
                "123",           // entityId
                "name",                 // fieldName
                "Old Name",             // oldValue
                "New Name",             // newValue
                "user@shodrone.app"     // changedBy
        );

        repo.save(entry);

        List<AuditLogEntry> found = repo.findByEntity("FigureCategory", "123");
        assertEquals(1, found.size());
        assertEquals("name", found.get(0).fieldName());
        assertEquals("Old Name", found.get(0).oldValue());
        assertEquals("New Name", found.get(0).newValue());
        assertEquals("user@shodrone.app", found.get(0).changedBy());
    }

    @Test
    void testSaveAllAndFindAll() {
        AuditLogEntry entry1 = new AuditLogEntry(
                "FigureCategory", "1", "description", "old", "new", "user1");
        AuditLogEntry entry2 = new AuditLogEntry(
                "FigureCategory", "2", "name", "cat1", "cat2", "user2");

        repo.saveAll(List.of(entry1, entry2));
        List<AuditLogEntry> all = repo.findAll();

        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(e -> e.entityId().equals("1")));
        assertTrue(all.stream().anyMatch(e -> e.entityId().equals("2")));
    }

    @Test
    void testFindByEntityReturnsEmptyIfNotFound() {
        List<AuditLogEntry> found = repo.findByEntity("FigureCategory", "999");
        assertTrue(found.isEmpty());
    }
}
