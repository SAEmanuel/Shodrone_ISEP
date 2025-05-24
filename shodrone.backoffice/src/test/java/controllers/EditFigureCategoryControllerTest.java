package controllers;

import authz.Email;
import controller.category.EditFigureCategoryController;
import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditFigureCategoryControllerTest {
    private EditFigureCategoryController controller;
    private FigureCategoryRepository mockRepository;
    private AuditLoggerService mockAuditLoggerService;
    private final FigureCategory category = new FigureCategory(new Name("Original"), new Description("Description"), new Email("editor@shodrone.app"));

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        mockAuditLoggerService = mock(AuditLoggerService.class);

        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        // Injeta o mock do AuditLoggerService via reflection
        try {
            var field = RepositoryProvider.class.getDeclaredField("auditLoggerService");
            field.setAccessible(true);
            field.set(null, mockAuditLoggerService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        controller = new EditFigureCategoryController();
    }

    @Test
    void testEditCategorySuccess() {
        // O oldValue é o identity() ANTES da alteração
        String oldValue = category.identity();
        when(mockRepository.updateFigureCategory(any(FigureCategory.class), eq(oldValue))).thenReturn(Optional.of(category));

        Optional<FigureCategory> result = controller.editChosenCategory(category, new Name("Updated"), new Description("New Desc"));

        assertTrue(result.isPresent());
        assertEquals(category, result.get());
        verify(mockRepository).updateFigureCategory(any(FigureCategory.class), eq(oldValue));
        verify(mockAuditLoggerService).logChanges(any(), any(), any(), any(), any());
    }

    @Test
    void testEditCategoryFails() {
        String oldValue = category.identity();
        when(mockRepository.updateFigureCategory(any(FigureCategory.class), eq(oldValue))).thenReturn(Optional.empty());

        Optional<FigureCategory> result = controller.editChosenCategory(category, new Name("Updated"), new Description("New Desc"));

        assertTrue(result.isEmpty());
        verify(mockRepository).updateFigureCategory(any(FigureCategory.class), eq(oldValue));
        verify(mockAuditLoggerService).logChanges(any(), any(), any(), any(), any());
    }
}
