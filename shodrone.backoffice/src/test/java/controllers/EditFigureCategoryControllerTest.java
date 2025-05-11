package controllers;

import authz.Email;
import controller.category.EditFigureCategoryController;
import domain.entity.FigureCategory;
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
    private final FigureCategory category = new FigureCategory(new Name("Original"), new Description("Description"), new Email("editor@shodrone.app"));

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        controller = new EditFigureCategoryController();
    }

    @Test
    void testEditCategorySuccess() {
        when(mockRepository.editChosenCategory(any(), any(), any())).thenReturn(Optional.of(category));
        Optional<FigureCategory> result = controller.editChosenCategory(category, new Name("Updated"), new Description("New Desc"));
        assertTrue(result.isPresent());
    }

    @Test
    void testEditCategoryFails() {
        when(mockRepository.editChosenCategory(any(), any(), any())).thenReturn(Optional.empty());
        Optional<FigureCategory> result = controller.editChosenCategory(category, new Name("Updated"), new Description("New Desc"));
        assertTrue(result.isEmpty());
    }
}

