package controllers;

import authz.Email;
import controller.category.ChangeFigureCategoryStatusController;
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

class ChangeFigureCategoryStatusControllerTest {

    private ChangeFigureCategoryStatusController controller;
    private FigureCategoryRepository mockRepository;

    private final FigureCategory mockCategory = new FigureCategory(new Name("Mock"), new Description("Mock Description"), new Email("test@shodrone.app"));

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        controller = new ChangeFigureCategoryStatusController();
    }

//    @Test
//    void testChangeStatusSuccess() {
//        when(mockRepository.changeStatus(mockCategory)).thenReturn(Optional.of(mockCategory));
//        Optional<FigureCategory> result = controller.changeStatus(mockCategory);
//        assertTrue(result.isPresent());
//    }
//
//    @Test
//    void testChangeStatusFails() {
//        when(mockRepository.changeStatus(mockCategory)).thenReturn(Optional.empty());
//        Optional<FigureCategory> result = controller.changeStatus(mockCategory);
//        assertTrue(result.isEmpty());
//    }
}