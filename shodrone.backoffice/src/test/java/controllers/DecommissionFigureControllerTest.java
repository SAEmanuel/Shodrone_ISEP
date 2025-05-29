package controllers;

import controller.figure.DecommissionFigureController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.FigureRepository;
import domain.valueObjects.*;
import domain.entity.Email;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DecommissionFigureController.
 * Mocks FigureRepository to simulate persistence layer behavior.
 * Tests successful and unsuccessful figure decommissioning scenarios.
 */
class DecommissionFigureControllerTest {
    private DecommissionFigureController controller;
    private FigureRepository mockRepository;

    // Sample FigureCategory to be used in tests
    private final FigureCategory category = new FigureCategory(
            new domain.valueObjects.Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );

    // Sample customer used for association with Figure
    private final Costumer customer = new Costumer(
            eapli.framework.infrastructure.authz.domain.model.Name.valueOf("Jorge", "Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    // Sample Figure instance for testing, initially active and public
    private final Figure figure = new Figure(new Name("Airplane") , new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null,customer
    );

    /**
     * Setup method runs before each test case.
     * Creates a mock FigureRepository and injects it into the RepositoryProvider.
     * Instantiates the controller being tested.
     */
    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new DecommissionFigureController();
    }

    /**
     * Tests successful decommission of a Figure.
     * Mocks the repository method to return an Optional containing the figure (simulating success).
     * Verifies the controller method returns a present Optional.
     */
    @Test
    void testDecommissionFigureSuccess() {
        when(mockRepository.editChosenFigure(any())).thenReturn(Optional.of(figure));
        Optional<Figure> result = controller.editChosenFigure(figure);
        assertTrue(result.isPresent());
    }

    /**
     * Tests the case where decommissioning a Figure fails.
     * Mocks the repository method to return an empty Optional (simulating failure).
     * Verifies the controller method returns an empty Optional.
     */
    @Test
    void testDecommissionFigureFails() {
        when(mockRepository.editChosenFigure(any())).thenReturn(Optional.empty());
        Optional<Figure> result = controller.editChosenFigure(figure);
        assertTrue(result.isEmpty());
    }
}