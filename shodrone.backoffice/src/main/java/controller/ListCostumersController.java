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
}
