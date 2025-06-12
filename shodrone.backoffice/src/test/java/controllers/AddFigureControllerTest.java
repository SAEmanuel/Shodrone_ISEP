package controllers;

import controller.figure.AddFigureController;
import domain.entity.Costumer;
import domain.entity.Email;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.FigureRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AddFigureController.
 */
class AddFigureControllerTest {

    private AddFigureController controller;
    private FigureRepository mockRepository;

    private final Name name = new Name("Test Figure");
    private final Description description = new Description("Test description");
    private final FigureCategory category = new FigureCategory(
            new Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );
    private final FigureAvailability availability = FigureAvailability.PUBLIC;
    private final FigureStatus status = FigureStatus.ACTIVE;

    private final Costumer customer = new Costumer(
            Name.valueOf("Jorge Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    private final Map<String, List<String>> dslVersions = Map.of(
            "v1", List.of("Position p = (0,0,0);")
    );

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new AddFigureController();
    }

    @Test
    void testAddFigureSuccess() {
        Figure fakeFigure = new Figure(name, description, category, availability, status, dslVersions, customer);
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.of(fakeFigure));

        Optional<Figure> result = controller.addFigure(name, description, category, availability, status, dslVersions, customer);

        assertTrue(result.isPresent());
        assertEquals(fakeFigure, result.get());
        verify(mockRepository).save(any(Figure.class));
    }

    @Test
    void testAddFigureWithMinimalInputs() {
        // Usa um dslVersions vazio, mas nunca null
        Map<String, List<String>> emptyDslVersions = new HashMap<>();
        Figure minimalFigure = new Figure(name, null, category, availability, status, emptyDslVersions, customer);
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.of(minimalFigure));

        Optional<Figure> result = controller.addFigure(name, null, category, availability, status, emptyDslVersions, customer);

        assertTrue(result.isPresent());
        assertEquals(minimalFigure, result.get());
        verify(mockRepository).save(any(Figure.class));
    }

    @Test
    void testAddFigureSaveFails() {
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.empty());

        Optional<Figure> result = controller.addFigure(name, description, category, availability, status, dslVersions, customer);

        assertTrue(result.isEmpty());
    }
}