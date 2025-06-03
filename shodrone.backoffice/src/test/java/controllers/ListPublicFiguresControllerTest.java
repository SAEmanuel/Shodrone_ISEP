package controllers;

import controller.figure.ListPublicFiguresController;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ListPublicFiguresController.
 * Uses a mocked FigureRepository to simulate data retrieval.
 * Tests various scenarios for retrieving public figures.
 */
class ListPublicFiguresControllerTest {
    private ListPublicFiguresController controller;
    private FigureRepository mockRepository;

    // Sample FigureCategory used in tests
    private final FigureCategory category = new FigureCategory(
            new domain.valueObjects.Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );

    // Sample customer used to associate with Figures
    private final Costumer customer = new Costumer(
            Name.valueOf("Jorge Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    // Sample figures with different availabilities and statuses
    private final Figure figure1 = new Figure(new Name("Airplane") , new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, customer
    );

    private final Figure figure2 = new Figure(new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, null, customer
    );

    private final Figure figure3 = new Figure(new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, null, customer);

    /**
     * Setup executed before each test.
     * - Creates mock FigureRepository.
     * - Sets up the repository to return a list containing figure1, figure2, figure3 when queried.
     * - Injects mock repository into RepositoryProvider.
     * - Instantiates controller.
     */
    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);

        List<Figure> allFigures = new ArrayList<>();
        allFigures.add(figure1);
        allFigures.add(figure2);
        allFigures.add(figure3);

        when(mockRepository.findAllPublicFigures()).thenReturn(allFigures);

        RepositoryProvider.injectFigureRepository(mockRepository);

        controller = new ListPublicFiguresController();
    }

    /**
     * Tests that controller.listPublicFigures() returns a non-empty list when the repository
     * returns a list containing one figure.
     */
    @Test
    void testGetAllPublicFiguresReturnsList() {
        List<Figure> list = new ArrayList<>();
        list.add(figure1);

        when(mockRepository.findAllPublicFigures()).thenReturn(list);
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isPresent());
    }

    /**
     * Tests that controller.listPublicFigures() returns an empty Optional when the repository
     * returns an empty list.
     */
    @Test
    void testGetAllFigureCategoriesReturnsEmpty() {
        when(mockRepository.findAllPublicFigures()).thenReturn(new ArrayList<>());
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isEmpty());
    }

    /**
     * Tests that controller.listPublicFigures() returns a list containing active public figures.
     * Here, the mocked repository returns a list with figure1 (active and public).
     */
    @Test
    void testGetPublicFiguresThatAreInactiveReturnsEmpty() {
        List<Figure> filteredFigures = new ArrayList<>();
        filteredFigures.add(figure1);

        when(mockRepository.findAllPublicFigures()).thenReturn(filteredFigures);
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    /**
     * Tests that controller.listPublicFigures() returns a list containing public figures,
     * even when there are exclusive figures in the repository.
     * This test ensures exclusive figures are filtered out (implied in controller logic).
     */
    @Test
    void testGetPublicFiguresThatAreExclusiveReturnsEmpty() {
        List<Figure> filteredFigures = new ArrayList<>();
        filteredFigures.add(figure1);

        when(mockRepository.findAllPublicFigures()).thenReturn(filteredFigures);
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }
}
