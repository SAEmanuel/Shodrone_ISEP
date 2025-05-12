package controllers;

import authz.Email;
import controller.figure.DecommissionFigureController;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DecommissionFigureControllerTest {
    private DecommissionFigureController controller;
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

    private final Figure figure = new Figure(new Name("Airplane") , new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null,customer
    );

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new DecommissionFigureController();
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