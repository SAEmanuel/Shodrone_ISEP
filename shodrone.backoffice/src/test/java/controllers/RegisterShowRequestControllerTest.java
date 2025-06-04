package controllers;

import controller.showrequest.RegisterShowRequestController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import eapli.framework.general.domain.model.EmailAddress;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.valueObjects.*;
import utils.AuthUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RegisterShowRequestControllerTest {

    private RegisterShowRequestController controller;

    @BeforeEach
    void setUp() {
        controller = new RegisterShowRequestController();

         Costumer realCostumer = new Costumer(
                Name.valueOf("Jorge Ubaldo"),
                EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
                new PhoneNumber("912861312"),
                new NIF("123456789"),
                new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
        );
        Figure figure = new Figure(new domain.valueObjects.Name("Circle"), new Description("A perfect round shape"), 1L, null, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, realCostumer);
        List<Figure> figures = List.of(figure);

        controller.foundCostumerForRegistration(Optional.of(realCostumer));
        controller.foundFiguresForRegistration(figures);
        controller.getDescriptionsForRegistration("Espetáculo de Teste");
        controller.getLocationOfShow(new Location(new Address("Rua","Porto","4444-888","Portugal"),12,12,"iasjdiasjdiasdjasid" ));
        controller.getDateForShow(LocalDateTime.now().plusHours(73));
        controller.getNumberOfDrones(5);
        controller.getShowDuration(30);
    }

    @Test
    void testRegisterShowRequestSuccess() throws IOException {
        // Assume que AuthUtils.getCurrentUserEmail() retorna algo válido
        String email = AuthUtils.getCurrentUserEmail();
        assertNotNull(email, "O email do utilizador autenticado deve estar definido.");

        // Regista o pedido de espetáculo
        ShowRequest result = controller.registerShowRequest();

        assertNotNull(result, "O pedido de espetáculo não deve ser nulo.");
        assertEquals("Espetáculo de Teste", result.getDescription().toString());
    }

    @Test
    void testFoundCostumerThrowsIfEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.foundCostumerForRegistration(Optional.empty()));

        assertEquals("No customer selected.", exception.getMessage());
    }

    @Test
    void testFoundFiguresThrowsIfEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.foundFiguresForRegistration(List.of()));

        assertEquals("No figures selected.", exception.getMessage());
    }

    @Test
    void testAllFieldsAreSetCorrectly() throws IOException {
        ShowRequest request = controller.registerShowRequest();

        assertNotNull(request.getCostumer());
        assertEquals("Espetáculo de Teste", request.getDescription().toString());
        assertEquals(5, request.getNumberOfDrones());
        assertEquals(Duration.ofMinutes(30), request.getShowDuration());
    }

}
