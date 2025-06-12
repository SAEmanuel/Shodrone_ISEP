package controllers;

import controller.figure.DecommissionFigureController;
import domain.entity.Costumer;
import domain.entity.Email;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.FigureRepository;
import persistence.RepositoryProvider;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DecommissionFigureController.
 */
class DecommissionFigureControllerTest {
    private DecommissionFigureController controller;
    private FigureRepository mockRepository;

    private final FigureCategory category = new FigureCategory(
            new Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );

    private final Costumer customer = new Costumer(
            Name.valueOf("Jorge Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    private Figure figure;

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new DecommissionFigureController();

        // Criar dslVersions de teste
        Map<String, List<String>> dslVersions = new HashMap<>();
        dslVersions.put("v1", List.of("LINE(1,2)", "CIRCLE(3)"));

        figure = new Figure(
                new Name("Airplane"),
                new Description("Airplane figure"),
                category,
                FigureAvailability.PUBLIC,
                FigureStatus.ACTIVE,
                dslVersions,
                customer
        );
    }

    @Test
    void testDecommissionFigureSuccess() {
        when(mockRepository.editChosenFigure(any())).thenReturn(Optional.of(figure));

        Optional<Figure> result = controller.editChosenFigure(figure);

        assertTrue(result.isPresent());
    }

    @Test
    void testDecommissionFigureFails() {
        when(mockRepository.editChosenFigure(any())).thenReturn(Optional.empty());

        Optional<Figure> result = controller.editChosenFigure(figure);

        assertTrue(result.isEmpty());
    }
}