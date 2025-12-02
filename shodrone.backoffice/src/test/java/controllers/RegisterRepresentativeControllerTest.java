package controllers;

import constants.Roles;
import controller.authz.AuthenticationController;
import controller.user.RegisterRepresentativeController;
import controller.user.RegisterUserController;
import domain.entity.Costumer;
import domain.entity.CustomerRepresentative;
import domain.valueObjects.Address;
import domain.valueObjects.Name;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.CustomerRepresentativeRepository;
import domain.entity.Email;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegisterRepresentativeControllerTest {

    private RegisterRepresentativeController controller;
    private CustomerRepresentativeRepository mockRepository;
    private RegisterUserController mockUserController;

    @BeforeEach
    void setUp() {
        mockRepository = mock(CustomerRepresentativeRepository.class);
        mockUserController = mock(RegisterUserController.class);

        controller = new RegisterRepresentativeController(mockRepository, mockUserController);
    }


    @Test
    void testRegisterRepresentativeSuccess() {
        Name name = new Name("Ana Lima");
        Email email = new Email("ana.lima@shodrone.app");
        PhoneNumber phone = new PhoneNumber("912345679");
        String position = "Sales Lead";
        String password = "securePassword123";

        Costumer costumer = new Costumer(
                Name.valueOf("Ana Lima"),
                EmailAddress.valueOf(email.getEmail()),
                phone,
                new NIF("123456789"),
                new Address("Rua X", "Porto", "4000-123", "Portugal")
        );

        when(mockUserController.registerUser(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        boolean result = controller.registerRepresentative(costumer, name, email, phone, position, password);

        assertTrue(result);
        verify(mockRepository).saveInStore(any(CustomerRepresentative.class));
        verify(mockUserController).registerUser(
                eq(name.name()),
                eq(email.getEmail()),
                eq(password),
                eq(Roles.ROLE_CUSTOMER_REPRESENTATIVE)
        );
    }

}
