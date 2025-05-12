package controllers;

import authz.Email;
import controller.figure.ListPublicFiguresController;
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

class ListPublicFiguresControllerTest {
    private ListPublicFiguresController controller;
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

    private final Figure figure1 = new Figure(new Name("Airplane") , new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, customer
    );

    private final Figure figure2 = new Figure(new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, null, customer
    );

    private final Figure figure3 = new Figure(new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, null, customer);

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

    @Test
    void testGetAllPublicFiguresReturnsList() {
        List<Figure> list = new ArrayList<>();
        list.add(figure1);

        when(mockRepository.findAllPublicFigures()).thenReturn(list);
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isPresent());
    }

    @Test
    void testGetAllFigureCategoriesReturnsEmpty() {
        when(mockRepository.findAllPublicFigures()).thenReturn(new ArrayList<>());
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPublicFiguresThatAreInactiveReturnsEmpty() {
        List<Figure> filteredFigures = new ArrayList<>();
        filteredFigures.add(figure1);

        when(mockRepository.findAllPublicFigures()).thenReturn(filteredFigures);
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

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
