package controller;

import domain.entity.Costumer;
import domain.valueObjects.NIF;
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
