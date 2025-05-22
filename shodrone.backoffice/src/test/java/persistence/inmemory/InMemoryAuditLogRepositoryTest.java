package persistence.inmemory;

import authz.Email;
import domain.entity.FigureCategory;
import domain.history.AuditLogEntry;
import domain.history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFigureCategoryRepositoryWithAuditTest {

    private InMemoryAuditLogRepository auditRepo;
    private InMemoryFigureCategoryRepository repo;
    private final Email creator = new Email("test@shodrone.app");

    @BeforeEach
    void setUp() {
        auditRepo = new InMemoryAuditLogRepository();
        AuditLoggerService auditLoggerService = new AuditLoggerService(auditRepo);
        repo = new InMemoryFigureCategoryRepository(auditLoggerService);
    }

//    @Test
//    void testAuditOnCreate() {
//        FigureCategory category = new FigureCategory(new Name("TestCat"), new Description("Initial Desc"), creator);
//        Optional<FigureCategory> saved = repo.save(category);
//        assertTrue(saved.isPresent());
//
//        List<AuditLogEntry> logs = auditRepo.findByEntity("FigureCategory", category.identity());
//
//        assertTrue(logs.stream().anyMatch(e ->
//                e.fieldName().equals("name") && e.oldValue() == null && e.newValue().equals("TestCat")));
//        assertTrue(logs.stream().anyMatch(e ->
//                e.fieldName().equals("description") && e.oldValue() == null && e.newValue().equals("Initial Desc")));
//    }

//    @Test
//    void testAuditOnEdit() {
//        FigureCategory category = new FigureCategory(new Name("TestCat"), new Description("Initial Desc"), creator);
//        repo.save(category);
//
//        Optional<FigureCategory> edited = repo.editChosenCategory(category, new Name("TestCatEdited"), new Description("Updated Desc"));
//        assertTrue(edited.isPresent());
//
//        List<AuditLogEntry> logs = auditRepo.findByEntity("FigureCategory", category.identity());
//
//        assertTrue(logs.stream().anyMatch(e ->
//                e.fieldName().equals("name") && e.oldValue().equals("TestCat") && e.newValue().equals("TestCatEdited")));
//        assertTrue(logs.stream().anyMatch(e ->
//                e.fieldName().equals("description") && e.oldValue().equals("Initial Desc") && e.newValue().equals("Updated Desc")));
//    }

//    @Test
//    void testAuditOnChangeStatusLogsActiveField() {
//        FigureCategory category = new FigureCategory(new Name("TestCat"), new Description("Initial Desc"), creator);
//        repo.save(category);
//
//        assertTrue(category.isAvailable());
//
//        repo.changeStatus(category);
//
//        List<AuditLogEntry> logs = auditRepo.findByEntity("FigureCategory", category.identity());
//
//
//        assertTrue(logs.stream().anyMatch(e ->
//                (e.fieldName().equals("available")) && e.oldValue().equals("true") && e.newValue().equals("false")));
//    }

}
