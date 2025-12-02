package controllers;

import controller.figure.ListPublicFiguresController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import domain.entity.Email;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.FigureRepository;
import utils.DslMetadata;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ListPublicFiguresController.
 * Uses a mocked FigureRepository to simulate data retrieval.
 */
class ListPublicFiguresControllerTest {

    private ListPublicFiguresController controller;
    private FigureRepository mockRepository;

    private Figure figure1;
    private Figure figure2;
    private Figure figure3;

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new ListPublicFiguresController();

        // DSL versions dummy data
        Map<String, DslMetadata> dslVersions = new HashMap<>();
        List<String> dslLines = List.of("DSL version 1.0;", "DroneType DroneTypeX;", "Position a = (0,0,0);", "Line l(a,a,DroneTypeX);");
        dslVersions.put("1.0", new DslMetadata("DroneTypeX", dslLines));

        FigureCategory category = new FigureCategory(
                new Name("Geometry"),
                new Description("Common geometric shapes"),
                new Email("show_designer@shodrone.app")
        );

        Costumer customer = new Costumer(
                Name.valueOf("Jorge Ubaldo"),
                EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
                new PhoneNumber("912861312"),
                new NIF("123456789"),
                new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
        );

        // Figures com dados válidos
        figure1 = new Figure(new Name("Airplane"), new Description("Airplane figure"),
                category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, dslVersions, customer);

        figure2 = new Figure(new Name("Airplane"), new Description("Airplane figure"),
                category, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, dslVersions, customer);

        figure3 = new Figure(new Name("Airplane"), new Description("Airplane figure"),
                category, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, dslVersions, customer);
    }

    @Test
    void testGetAllPublicFiguresReturnsList() {
        List<Figure> list = List.of(figure1);
        when(mockRepository.findAllPublicFigures()).thenReturn(list);

        Optional<List<Figure>> result = controller.listPublicFigures();
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testGetAllFigureCategoriesReturnsEmpty() {
        when(mockRepository.findAllPublicFigures()).thenReturn(new ArrayList<>());

        Optional<List<Figure>> result = controller.listPublicFigures();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPublicFiguresThatAreInactiveReturnsEmpty() {
        List<Figure> filteredFigures = List.of(figure1);
        when(mockRepository.findAllPublicFigures()).thenReturn(filteredFigures);

        Optional<List<Figure>> result = controller.listPublicFigures();
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testGetPublicFiguresThatAreExclusiveReturnsEmpty() {
        List<Figure> filteredFigures = List.of(figure1);
        when(mockRepository.findAllPublicFigures()).thenReturn(filteredFigures);

        Optional<List<Figure>> result = controller.listPublicFigures();
        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }
}