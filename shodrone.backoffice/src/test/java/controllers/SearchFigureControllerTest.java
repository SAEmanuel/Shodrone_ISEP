package controllers;

import authz.Email;
import controller.figure.SearchFigureController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchFigureControllerTest {
    private SearchFigureController controller;
    private FigureRepository mockRepository;

    private final FigureCategory category = new FigureCategory(
            new domain.valueObjects.Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );

    private final Costumer customer = new Costumer(
            eapli.framework.infrastructure.authz.domain.model.Name.valueOf("Jorge", "Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    private final Figure figure1 = new Figure("Airplane", new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, customer
    );

    private final Figure figure2 = new Figure("Airplane", new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, customer
    );

    private final Figure figure3 = new Figure("Helicopter", new Description("Helicopter figure"), (long) 2.0,
            category, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, customer
    );

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);

        List<Figure> allFigures = new ArrayList<>();
        allFigures.add(figure1);
        allFigures.add(figure2);
        allFigures.add(figure3);

        when(mockRepository.findFigures(null, null, null, null,
                null, null, null, null))
                .thenReturn(Optional.of(allFigures));

        RepositoryProvider.injectFigureRepository(mockRepository);

        controller = new SearchFigureController();
    }

    @Test
    void testSearchByFigureId() {
        // Assuming you're searching for the figure with a specific ID (e.g., figure1's ID)
        when(mockRepository.findFigures(figure1.identity(), null, null,
                null, null, null, null, null))
                .thenReturn(Optional.of(List.of(figure1)));

        Optional<List<Figure>> result = controller.searchFigure(figure1.identity(), null, null,
                null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByName() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, "Airplane", null, null, null, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, "Airplane", null, null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByDescription() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, new Description("Airplane figure"), null, null, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, new Description("Airplane figure"), null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByVersion() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, (long) 1.2, null, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, (long) 1.2, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByCategory() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, null, category, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, null, category, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByAvailability() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, null, null, FigureAvailability.PUBLIC, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, null, null, FigureAvailability.PUBLIC, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByStatus() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, null, null, null, FigureStatus.ACTIVE, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, null, null, null, FigureStatus.ACTIVE, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testSearchByCustomer() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, null, null, null, null, customer))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, null, null, null, null, customer);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }
}
