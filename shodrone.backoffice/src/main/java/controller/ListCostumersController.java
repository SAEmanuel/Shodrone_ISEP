package controller;

import domain.entity.Costumer;
import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import persistence.RepositoryProvider;
import persistence.interfaces.CostumerRepository;

import java.util.List;
import java.util.Optional;

public class ListCostumersController {
    private final CostumerRepository costumerRepository;

    public ListCostumersController() {
        costumerRepository = RepositoryProvider.costumerRepository();
        addCostumerTests();
    }

    public Optional<Costumer> foundCustomerByID(Long id){
        return costumerRepository.findByID(id);
    }
    public Optional<Costumer> foundCustomerByNIF(NIF nif){
        return costumerRepository.findByNIF(nif);
    }
    public Optional<List<Costumer>> getAllCustomer(){
        return costumerRepository.getAllCostumers();
    }


    private void addCostumerTests() {
        Costumer customer1 = new Costumer(
                Name.valueOf("Jorge", "Ubaldo"),
                EmailAddress.valueOf("jorgeubaldorf@gmail.com"),
                new PhoneNumber("912861312"),
                new NIF("123456789"),
                new Address("Rua Brigadeiro", "Porto", "4440-778", "Portugal")
        );
        costumerRepository.saveInStore(customer1, customer1.nif());

        Costumer customer2 = new Costumer(
                Name.valueOf("Maria", "Silva"),
                EmailAddress.valueOf("maria.silva@example.com"),
                new PhoneNumber("923456789"),
                new NIF("286500850"),
                new Address("Rua das Flores", "Lisboa", "1100-045", "Portugal")
        );
        costumerRepository.saveInStore(customer2, customer2.nif());

        Costumer customer3 = new Costumer(
                Name.valueOf("Carlos", "Ferreira"),
                EmailAddress.valueOf("carlos.ferreira@example.com"),
                new PhoneNumber("934567890"),
                new NIF("248367080"),
                new Address("Avenida Central", "Braga", "4700-123", "Portugal")
        );
        costumerRepository.saveInStore(customer3, customer3.nif());
    }
}
