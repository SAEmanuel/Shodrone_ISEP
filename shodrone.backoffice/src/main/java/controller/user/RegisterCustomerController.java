package controller.user;

import domain.entity.Costumer;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import persistence.RepositoryProvider;
import persistence.interfaces.CostumerRepository;

import java.util.Optional;

import static utils.Utils.convertToName;

public class RegisterCustomerController {

    private final CostumerRepository customerRepository;

    public RegisterCustomerController() {
        this.customerRepository = RepositoryProvider.costumerRepository();
    }

    public Optional<Costumer> registerCustomer(Name customerName, EmailAddress email, PhoneNumber phone,
                                               NIF nif, Address address) {
        Costumer customer = new Costumer(convertToName(customerName), email, phone, nif, address);
        return customerRepository.saveInStore(customer, nif);
    }
}
