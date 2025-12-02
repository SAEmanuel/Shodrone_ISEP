package controllers;

import controller.showrequest.ListShowRequestByCostumerController;
import domain.entity.*;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ListShowRequestByCostumerControllerTest {

    private ListShowRequestByCostumerController controller;
    private CostumerRepository costumerRepository;
    private ShowRequestRepository showRequestRepository;
    private Costumer testCostumer;

    @BeforeEach
    void setUp() {
        controller = new ListShowRequestByCostumerController();
        costumerRepository = RepositoryProvider.costumerRepository();
        showRequestRepository = RepositoryProvider.showRequestRepository();

        testCostumer = new Costumer(
                Name.valueOf("Jorge Ubaldo"),
                EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
                new PhoneNumber("912861312"),
                new NIF("123456789"),
                new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
        );

        costumerRepository.saveInStore(testCostumer, testCostumer.nif());
    }


    @Test
    void testListShowRequestByCostumer_WithCostumerWithoutRequests_ThrowsException() {
        Costumer noShows = new Costumer(
                Name.valueOf("Empty One"),
                EmailAddress.valueOf("empty@shodrone.app"),
                new PhoneNumber("911111111"),
                new NIF("248367080"),
                new Address("No Street", "Nowhere", "0000-000", "Noland")
        );
        costumerRepository.saveInStore(noShows, noShows.nif());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.listShowRequestByCostumer(Optional.of(noShows)));

        assertEquals("No show requests found for the given customer.", exception.getMessage());
    }

    @Test
    void testListShowRequestByCostumer_WithEmptyOptional_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.listShowRequestByCostumer(Optional.empty()));

        assertEquals("No customer selected.", exception.getMessage());
    }
}