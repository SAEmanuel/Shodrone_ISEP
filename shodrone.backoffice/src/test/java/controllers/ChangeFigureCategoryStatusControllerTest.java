package controllers;

import controller.category.ChangeFigureCategoryStatusController;
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

class ChangeFigureCategoryStatusControllerTest {

    private ChangeFigureCategoryStatusController controller;
    private FigureCategoryRepository mockRepository;
    private AuditLoggerService mockAuditLoggerService;

    private final FigureCategory mockCategory = new FigureCategory(
            new Name("Mock"),
            new Description("Mock Description"),
            new Email("test@shodrone.app")
    );

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

        controller = new ChangeFigureCategoryStatusController();
    }

    @Test
    void testChangeStatusSuccess() {
        String oldValue = mockCategory.identity();
        when(mockRepository.updateFigureCategory(any(FigureCategory.class), eq(oldValue))).thenReturn(Optional.of(mockCategory));

        Optional<FigureCategory> result = controller.changeStatus(mockCategory);

        assertTrue(result.isPresent());
        assertEquals(mockCategory, result.get());
        verify(mockRepository).updateFigureCategory(any(FigureCategory.class), eq(oldValue));
        verify(mockAuditLoggerService).logChanges(any(), any(), any(), any(), any());
    }

    @Test
    void testChangeStatusFails() {
        String oldValue = mockCategory.identity();
        when(mockRepository.updateFigureCategory(any(FigureCategory.class), eq(oldValue))).thenReturn(Optional.empty());

        Optional<FigureCategory> result = controller.changeStatus(mockCategory);

        assertTrue(result.isEmpty());
        verify(mockRepository).updateFigureCategory(any(FigureCategory.class), eq(oldValue));
        verify(mockAuditLoggerService).logChanges(any(), any(), any(), any(), any());
    }
}
