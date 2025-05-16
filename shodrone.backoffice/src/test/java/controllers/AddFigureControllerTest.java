package controllers;

import authz.Email;
import controller.figure.AddFigureController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


/**
 * Unit tests for AddFigureController.
 * Uses Mockito to mock the FigureRepository and verify controller behavior.
 */
class AddFigureControllerTest {

    private AddFigureController controller;
    private FigureRepository mockRepository;

    // Sample valid test data
    private final Name name = new Name("Test Figure");
    private final Description description = new Description("Test description");
    private final Long version = 1L;
    private final FigureCategory category = new FigureCategory(
            new domain.valueObjects.Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );
    private final FigureAvailability figureAvailability = FigureAvailability.PUBLIC;
    private final FigureStatus figureStatus = FigureStatus.ACTIVE;

    // Sample customer used to associate with the figure
    private final Costumer customer = new Costumer(
            eapli.framework.infrastructure.authz.domain.model.Name.valueOf("Jorge", "Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    /**
     * Setup method called before each test.
     * Initializes the mock repository and injects it into the RepositoryProvider.
     * Instantiates the controller to be tested.
     */
    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new AddFigureController();
    }

    /**
     * Tests successful addition of a Figure with all parameters.
     * Mocks repository save to return an Optional containing the figure.
     * Asserts the controller returns the figure wrapped in Optional and that repository save was called.
     */
    @Test
    void testAddFigureSuccess() {
        Figure fakeFigure = new Figure(name, description, version, category, figureAvailability, figureStatus, null, customer);
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.of(fakeFigure));

        Optional<Figure> result = controller.addFigure(name, description, version, category, figureAvailability, figureStatus, null, customer);

        assertTrue(result.isPresent());
        assertEquals(fakeFigure, result.get());
        verify(mockRepository).save(any(Figure.class));
    }

    /**
     * Tests adding a Figure with only name, category, and customer, relying on default availability and status.
     * Mocks repository save to return an Optional containing the figure.
     * Asserts correct Optional result and repository interaction.
     */
    @Test
    void testAddFigureWithNameCategoryAndCostumerOnly_Success() {
        //Added manually the default Availability and Status
        Figure fakeFigure = new Figure(name, null, null, category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, customer);
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.of(fakeFigure));

        //Added manually the default Availability and Status
        Optional<Figure> result = controller.addFigure(name, null, null, category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, customer);

        assertTrue(result.isPresent());
        assertEquals(fakeFigure, result.get());
        verify(mockRepository).save(any(Figure.class));
    }

    /**
     * Tests the case where saving a Figure fails (repository returns empty Optional).
     * Asserts that the controller returns an empty Optional to indicate failure.
     */
    @Test
    void testAddFigure_SaveFails_ReturnsEmpty() {
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.empty());

        Optional<Figure> result = controller.addFigure(name, null,null, null, null, null, null, null);

        assertTrue(result.isEmpty());
    }

}