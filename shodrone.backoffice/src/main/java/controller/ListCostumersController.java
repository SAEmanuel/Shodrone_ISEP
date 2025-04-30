package controller;

import domain.entity.Customer;
import persistence.inmemory.Repositories;
import persistence.interfaces.CostumerRepository;
import persistence.jpa.JPAImpl.CostumerJPAImpl;

import java.util.List;
import java.util.Optional;

public class ListCostumersController {
    private CostumerRepository inMemoryCostumerRepository;
    private CostumerJPAImpl jpaCustomerRepository;

    public ListCostumersController() {
        inMemoryCostumerRepository = getInMemoryCustomerRepository();
        jpaCustomerRepository = getJPACostumerRepository();
    }

    private CostumerRepository getInMemoryCustomerRepository() {
        if(inMemoryCostumerRepository == null){
            inMemoryCostumerRepository = Repositories.getInstance().getCostumerRepository();
        }
        return inMemoryCostumerRepository;
    }

    private CostumerJPAImpl getJPACostumerRepository() {
        if(jpaCustomerRepository == null){
            jpaCustomerRepository = new CostumerJPAImpl();
        }
        return jpaCustomerRepository;
    }

    //--------
    public Optional<Customer> foundCustomerByID(){
        return Optional.empty(); // Implement actual logic here
    }

    public Optional<Customer> foundCustomerByNIF(){
        return Optional.empty(); // Implement actual logic here
    }

    public Optional<List<Customer>> getAllCustomer(){
        return Optional.empty(); // Implement actual logic here
    }
}
