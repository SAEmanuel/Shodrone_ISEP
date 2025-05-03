package controllers;

import authz.Email;
import controller.AddFigureCategoryController;
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

class AddFigureCategoryControllerTest {

    private AddFigureCategoryController controller;
    private FigureCategoryRepository mockRepository;

    private final Name name = new Name("Test Category");
    private final Description description = new Description("Test description");
    private final Email createdBy = new Email("user@shodrone.app");

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        controller = new AddFigureCategoryController();
    }

    @Test
    void testAddFigureCategoryWithNameAndDescription_Success() {
        FigureCategory fakeCategory = new FigureCategory(name, description, createdBy);
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.of(fakeCategory));

        Optional<FigureCategory> result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);

        assertTrue(result.isPresent());
        assertEquals(fakeCategory, result.get());
        verify(mockRepository).save(any(FigureCategory.class));
    }

    @Test
    void testAddFigureCategoryWithNameOnly_Success() {
        FigureCategory fakeCategory = new FigureCategory(name, createdBy);
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.of(fakeCategory));

        Optional<FigureCategory> result = controller.addFigureCategoryWithName(name, createdBy);

        assertTrue(result.isPresent());
        assertEquals(fakeCategory, result.get());
        verify(mockRepository).save(any(FigureCategory.class));
    }

    @Test
    void testAddFigureCategory_SaveFails_ReturnsEmpty() {
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.empty());

        Optional<FigureCategory> result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);

        assertTrue(result.isEmpty());
    }
}