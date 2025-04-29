package controller;

import domain.entity.Customer;
import persistence.inmemory.Repositories;
import persistence.interfaces.CostumerRepository;

import java.util.List;
import java.util.Optional;

public class ListCostumersController {
    //TODO ADCIONAR JPA REPOSITORY
    private CostumerRepository inMemoryCostumerRepository;

    public ListCostumersController() {
        inMemoryCostumerRepository = getJpaCustomerRepository();
    }

    private CostumerRepository getJpaCustomerRepository() {
        if(inMemoryCostumerRepository == null){
            inMemoryCostumerRepository = Repositories.getInstance().getCostumerRepository();
        }
        return inMemoryCostumerRepository;
    }


    //--------
    public Optional<Customer> foundCustomerByID(){
        return null;
    }

    public Optional<List<Customer>> foundCustomerByName(){
        return null;
    }

    public Optional<Customer> foundCustomerByNIF(){
        return null;
    }

    public Optional<List<Customer>> getAllCustomer(){
        return null;
    }
}
