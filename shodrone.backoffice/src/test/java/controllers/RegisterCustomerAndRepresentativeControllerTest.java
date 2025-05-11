package controllers;

import authz.Email;
import controller.authz.AuthenticationController;
import controller.RegisterCustomerAndRepresentativeController;
import controller.user.RegisterUserController;
import domain.entity.Costumer;
import domain.valueObjects.*;
import eapli.framework.infrastructure.authz.domain.model.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.interfaces.CostumerRepository;
import persistence.interfaces.CustomerRepresentativeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegisterCustomerAndRepresentativeControllerTest {

    private CostumerRepository mockCostumerRepo;
    private CustomerRepresentativeRepository mockRepRepo;
    private RegisterUserController mockUserController;
    private RegisterCustomerAndRepresentativeController controller;

    @BeforeEach
    void setUp() {
        mockCostumerRepo = mock(CostumerRepository.class);
        mockRepRepo = mock(CustomerRepresentativeRepository.class);
        mockUserController = mock(RegisterUserController.class);
        controller = new RegisterCustomerAndRepresentativeController(mockCostumerRepo, mockRepRepo, mockUserController);
    }

    @Test
    void testRegisterCustomerAndRepresentative_Success() {
        Name customerName = Name.valueOf("Primeiro","Cliente");
        Email customerEmail = new Email("cliente@shodrone.app");
        PhoneNumber phone = new PhoneNumber("912345678");
        NIF nif = new NIF("232111111");
        Address address = new Address("Rua do Cliente", "Porto", "4440-310", "Portugal");

        Name repName = Name.valueOf("Primeiro","Representante");
        Email repEmail = new Email("rep@shodrone.app");
        PhoneNumber repPhone = new PhoneNumber("923456789");
        String repPosition = "Gestor";
        String password = "senhaSegura123!";

        when(mockCostumerRepo.saveInStore(any(), eq(nif)))
                .thenReturn(Optional.of(mock(Costumer.class)));

        when(mockUserController.registerUser(
                anyString(), eq("rep@shodrone.app"), eq(password),
                eq(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE)))
                .thenReturn(true);

        boolean result = controller.registerCustomerAndRepresentative(
                customerName, customerEmail, phone, nif, address,
                repName, repEmail, repPhone, repPosition, password
        );

        assertTrue(result);
    }

    @Test
    void testRegisterCustomerFailsToSaveCustomer() {
        Name name = Name.valueOf("Segundo","Cliente");
        Email email = new Email("cliente@shodrone.app");

        when(mockCostumerRepo.saveInStore(any(), any())).thenReturn(Optional.empty());

        boolean result = controller.registerCustomerAndRepresentative(
                name, email, new PhoneNumber("911111111"),
                new NIF("509172121"), new Address("Rua", "Lisboa", "4555-311", "Portugal"),
                Name.valueOf("Segundo","Rep"), new Email("rep@shodrone.app"),
                new PhoneNumber("922222222"), "cargo", "@senha123"
        );

        assertFalse(result);
        verify(mockUserController, never()).registerUser(any(), any(), any(), any());
    }

    @Test
    void testRegisterCustomerFailsToRegisterUser() {
        when(mockCostumerRepo.saveInStore(any(), any())).thenReturn(Optional.of(mock(Costumer.class)));
        when(mockUserController.registerUser(any(), any(), any(), any())).thenReturn(false);

        boolean result = controller.registerCustomerAndRepresentative(
                Name.valueOf("Erro","Errar"), new Email("xpto@shodrone.app"),
                new PhoneNumber("933333333"), new NIF("501964843"),
                new Address("Rua erro", "Braga", "4567-890", "Portugal"),
                Name.valueOf("Zé","Erro"), new Email("ze@shodrone.app"),
                new PhoneNumber("934444444"), "cargo", "Erro123!"
        );

        assertFalse(result);
    }
}
