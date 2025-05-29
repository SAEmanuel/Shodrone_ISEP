package controllers;

import controller.category.AddFigureCategoryController;
import domain.entity.Email;
import domain.entity.FigureCategory;
import history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.FigureCategoryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddFigureCategoryControllerTest {

    private AddFigureCategoryController controller;
    private FigureCategoryRepository mockRepository;
    private AuditLoggerService mockAuditLoggerService;

    private final Name name = new Name("Test Category");
    private final Description description = new Description("Test description");
    private final Email createdBy = new Email("user@shodrone.app");

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        mockAuditLoggerService = mock(AuditLoggerService.class);

        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        // injeta o mock de auditoria
        injectAuditLoggerService(mockAuditLoggerService);

        controller = new AddFigureCategoryController();
    }

    private void injectAuditLoggerService(AuditLoggerService mock) {
        try {
            var field = RepositoryProvider.class.getDeclaredField("auditLoggerService");
            field.setAccessible(true);
            field.set(null, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddFigureCategoryWithNameAndDescription_Success() {
        FigureCategory fakeCategory = new FigureCategory(name, description, createdBy);
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.of(fakeCategory));

        Optional<FigureCategory> result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);

        assertTrue(result.isPresent());
        assertEquals(fakeCategory, result.get());
        verify(mockRepository).save(any(FigureCategory.class));
        verify(mockAuditLoggerService).logCreation(any(), any(), any(), any());
    }

    @Test
    void testAddFigureCategoryWithNameOnly_Success() {
        FigureCategory fakeCategory = new FigureCategory(name, createdBy);
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.of(fakeCategory));

        Optional<FigureCategory> result = controller.addFigureCategoryWithName(name, createdBy);

        assertTrue(result.isPresent());
        assertEquals(fakeCategory, result.get());
        verify(mockRepository).save(any(FigureCategory.class));
        verify(mockAuditLoggerService).logCreation(any(), any(), any(), any());
    }

    @Test
    void testAddFigureCategory_SaveFails_ReturnsEmpty() {
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.empty());

        Optional<FigureCategory> result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);

        assertTrue(result.isEmpty());
        verify(mockAuditLoggerService).logCreation(any(), any(), any(), any());
    }
}
