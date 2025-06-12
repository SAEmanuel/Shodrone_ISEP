package controllers;

import controller.figure.SearchFigureController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.FigureRepository;
import domain.entity.Email;
import domain.valueObjects.*;
import utils.DslMetadata;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for SearchFigureController.
 * Uses a mocked FigureRepository to simulate figure searches by various criteria.
 */
class SearchFigureControllerTest {

    private SearchFigureController controller;
    private FigureRepository mockRepository;

    private final FigureCategory category = new FigureCategory(
            new Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );

    private final Costumer customer = new Costumer(
            Name.valueOf("Jorge"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    private Map<String, DslMetadata> sampleDsl;

    private Figure figure1;
    private Figure figure2;
    private Figure figure3;

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);

        // DSL with metadata
        List<String> dslLines = List.of(
                "DSL version 1.0.0;",
                "DroneType DroneX;",
                "Position a = (0,0,0);",
                "Line l(a,a,DroneX);"
        );
        DslMetadata metadata = new DslMetadata("DroneX", dslLines);
        sampleDsl = new HashMap<>();
        sampleDsl.put("1.0.0", metadata);

        // Create sample figures
        figure1 = new Figure(new Name("Airplane"), new Description("Airplane figure"),
                category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, sampleDsl, customer);

        figure2 = new Figure(new Name("Airplane"), new Description("Airplane figure"),
                category, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, sampleDsl, customer);

        figure3 = new Figure(new Name("Helicopter"), new Description("Helicopter figure"),
                category, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, sampleDsl, customer);

        // Default behavior for unfiltered search
        List<Figure> allFigures = List.of(figure1, figure2, figure3);
        when(mockRepository.findFigures(null, null, null, null, null, null, null, null, null))
                .thenReturn(Optional.of(allFigures));

        controller = new SearchFigureController();
    }

    @Test
    void testSearchByFigureId() {
        when(mockRepository.findFigures(figure1.identity(), null, null,
                null, null, null, null, null, null))
                .thenReturn(Optional.of(List.of(figure1)));

        Optional<List<Figure>> result = controller.searchFigure(figure1.identity(), null, null,
                null, null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByName() {
        when(mockRepository.findFigures(null, new Name("Airplane"), null,
                null, null, null, null, null, null))
                .thenReturn(Optional.of(List.of(figure1, figure2)));

        Optional<List<Figure>> result = controller.searchFigure(null, new Name("Airplane"), null,
                null, null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByDescription() {
        when(mockRepository.findFigures(null, null, new Description("Airplane figure"),
                null, null, null, null, null, null))
                .thenReturn(Optional.of(List.of(figure1, figure2)));

        Optional<List<Figure>> result = controller.searchFigure(null, null, new Description("Airplane figure"),
                null, null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure2));
    }

    @Test
    void testSearchByCategory() {
        when(mockRepository.findFigures(null, null, null, null, category,
                null, null, null, null))
                .thenReturn(Optional.of(List.of(figure1, figure2, figure3)));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null,
                null, category, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure3));
    }

    @Test
    void testSearchByAvailability() {
        when(mockRepository.findFigures(null, null, null, null, null,
                FigureAvailability.PUBLIC, null, null, null))
                .thenReturn(Optional.of(List.of(figure1, figure2)));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null,
                null, null, FigureAvailability.PUBLIC, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByStatus() {
        when(mockRepository.findFigures(null, null, null, null, null,
                null, FigureStatus.ACTIVE, null, null))
                .thenReturn(Optional.of(List.of(figure1, figure3)));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null,
                null, null, null, FigureStatus.ACTIVE, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure3));
    }

    @Test
    void testSearchByCustomer() {
        when(mockRepository.findFigures(null, null, null, null, null,
                null, null, null, customer))
                .thenReturn(Optional.of(List.of(figure1, figure2, figure3)));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null,
                null, null, null, null, null, customer);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure2));
    }
}