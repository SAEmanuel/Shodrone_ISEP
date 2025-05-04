package controllers;
import authz.Email;

import controller.GetFigureCategoriesController;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class GetFigureCategoriesControllerTest {
    private GetFigureCategoriesController controller;
    private FigureCategoryRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        controller = new GetFigureCategoriesController();
    }

    @Test
    void testGetAllFigureCategoriesReturnsList() {
        List<FigureCategory> list = new ArrayList<>();
        list.add(new FigureCategory(new Name("All"), new Description("All"), new Email("test@shodrone.app")));
        when(mockRepository.findAll()).thenReturn(list);
        Optional<List<FigureCategory>> result = controller.getAllFigureCategories();
        assertTrue(result.isPresent());
    }

    @Test
    void testGetAllFigureCategoriesReturnsEmpty() {
        when(mockRepository.findAll()).thenReturn(new ArrayList<>());
        Optional<List<FigureCategory>> result = controller.getAllFigureCategories();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetActiveFigureCategoriesReturnsList() {
        List<FigureCategory> list = new ArrayList<>();
        list.add(new FigureCategory(new Name("Active"), new Description("Active Desc"), new Email("test@shodrone.app")));
        when(mockRepository.findActiveCategories()).thenReturn(list);
        Optional<List<FigureCategory>> result = controller.getActiveFigureCategories();
        assertTrue(result.isPresent());
    }

    @Test
    void testGetActiveFigureCategoriesReturnsEmpty() {
        when(mockRepository.findActiveCategories()).thenReturn(new ArrayList<>());
        Optional<List<FigureCategory>> result = controller.getActiveFigureCategories();
        assertTrue(result.isEmpty());
    }
}
