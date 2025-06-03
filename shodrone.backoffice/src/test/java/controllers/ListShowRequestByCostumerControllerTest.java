package controllers;

import controller.showrequest.ListShowRequestByCostumerController;
import domain.entity.Costumer;
import domain.valueObjects.Name;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import factories.FactoryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.CostumerRepository;
import persistence.ShowRequestRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ListShowRequestByCostumerControllerTest {

    private ListShowRequestByCostumerController controller;
    private CostumerRepository costumerRepository;
    private ShowRequestRepository showRequestRepository;

    @BeforeEach
    void setUp() {
        controller = new ListShowRequestByCostumerController();
        costumerRepository = RepositoryProvider.costumerRepository();
        showRequestRepository = RepositoryProvider.showRequestRepository();
    }

    @Test
    void testListShowRequestByCostumer_WithValidCostumer_ReturnsShowRequests() {
        // Arrange: busca cliente existente com pedidos
        Optional<Costumer> costumer = costumerRepository.findByNIF(new NIF("123456789"));

        assertTrue(costumer.isPresent(), "Customer 'Jorge Ubaldo' must exist in test DB");

        Figure figure1  = new Figure(new domain.valueObjects.Name("Circle"),         new Description("A perfect round shape"),                 1L,  null,  FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer.get());
        Figure figure2  = new Figure(new domain.valueObjects.Name("Airplane"),       new Description("A fixed-wing flying vehicle"),           2L,  null,  FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer.get());
        List<Figure> figureList = new ArrayList<>();
        figureList.add(figure1);
        figureList.add(figure2);

        Optional<ShowRequest> show = FactoryProvider.getShowRequestFactory().automaticBuild(costumer.get(),figureList,new Description("Empty"),new Location(new Address("rua","Porto","4444-000","Portugal"),12,12,"sodsdlosdl"), LocalDateTime.now().plusHours(73),12, Duration.ofMinutes(12));
        RepositoryProvider.showRequestRepository().saveInStore(show.get());

        // Act
        List<ShowRequest> result = controller.listShowRequestByCostumer(costumer);

        // Assert
        assertFalse(result.isEmpty(), "Expected at least one show request for this customer.");
        for (ShowRequest sr : result) {
            assertEquals(costumer.get().identity(), sr.getCostumer().identity());
        }
    }

    @Test
    void testListShowRequestByCostumer_WithCostumerWithoutRequests_ThrowsException() {
        Costumer emptyCostumer = new Costumer(
                new Name("Jorge"),
                EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
                new PhoneNumber("912861312"),
                new NIF("123456789"),
                new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
        );
        costumerRepository.saveInStore(emptyCostumer,emptyCostumer.nif());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.listShowRequestByCostumer(Optional.of(emptyCostumer)));

        assertEquals("No show requests found for the given customer.", exception.getMessage());
    }

    @Test
    void testListShowRequestByCostumer_WithEmptyOptional_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.listShowRequestByCostumer(Optional.empty()));

        assertEquals("No customer selected.", exception.getMessage());
    }
}
