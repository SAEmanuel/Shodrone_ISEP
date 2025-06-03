package controllers;

import controller.user.RegisterCustomerController;
import domain.entity.Costumer;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import domain.valueObjects.Name;
import persistence.CostumerRepository;
import utils.Utils;
import domain.valueObjects.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegisterCustomerControllerTest {

    private RegisterCustomerController controller;
    private CostumerRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = mock(CostumerRepository.class);
        RepositoryProvider.injectCostumerRepository(mockRepository);
        controller = new RegisterCustomerController();
    }

    @Test
    void testRegisterCustomerSuccessfully() {
        Name name = Name.valueOf("João Pedro");
        EmailAddress email = EmailAddress.valueOf("joao.pedro@shodrone.app");
        PhoneNumber phone = new PhoneNumber("912345678");
        NIF nif = new NIF("123456789");
        Address address = new Address("Rua da Liberdade", "Lisboa", "3333-333", "Portugal");

        Costumer expected = new Costumer(name, email, phone, nif, address);

        when(mockRepository.saveInStore(any(Costumer.class), eq(nif)))
                .thenReturn(Optional.of(expected));

        Optional<Costumer> result = controller.registerCustomer(name, email, phone, nif, address);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(mockRepository).saveInStore(any(Costumer.class), eq(nif));
    }

    @Test
    void testRegisterCustomerFailsReturnsEmpty() {
        Name name = Name.valueOf("Ana Lima");
        EmailAddress email = EmailAddress.valueOf("ana.lima@shodrone.app");
        PhoneNumber phone = new PhoneNumber("912345679");
        NIF nif = new NIF("123456789");
        Address address = new Address("Av. Central", "Porto", "4444-444", "Portugal");

        when(mockRepository.saveInStore(any(Costumer.class), eq(nif)))
                .thenReturn(Optional.empty());



        Optional<Costumer> result = controller.registerCustomer(name, email, phone, nif, address);

        assertTrue(result.isEmpty());
        verify(mockRepository).saveInStore(any(Costumer.class), eq(nif));
    }
}
