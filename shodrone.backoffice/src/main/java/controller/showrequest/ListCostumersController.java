package controller.showrequest;

import domain.entity.Costumer;
import domain.valueObjects.NIF;
import persistence.RepositoryProvider;
import persistence.interfaces.CostumerRepository;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for handling customer-related operations.
 * This includes retrieving customers by ID, NIF, or fetching all customers.
 */
public class ListCostumersController {

    // Repository for interacting with customer data
    private final CostumerRepository costumerRepository;

    /**
     * Constructor of the controller, initializing the costumer repository.
     */
    public ListCostumersController() {
        // Initializing the customer repository through the repository provider
        costumerRepository = RepositoryProvider.costumerRepository();
    }

    /**
     * Finds a customer by their unique ID.
     *
     * @param id The ID of the customer to be found.
     * @return An Optional containing the customer if found, or empty if not.
     */
    public Optional<Costumer> foundCustomerByID(Long id) {
        return costumerRepository.findByID(id);
    }

    /**
     * Finds a customer by their NIF (Tax Identification Number).
     *
     * @param nif The NIF of the customer to be found.
     * @return An Optional containing the customer if found, or empty if not.
     */
    public Optional<Costumer> foundCustomerByNIF(NIF nif) {
        return costumerRepository.findByNIF(nif);
    }

    /**
     * Retrieves all customers from the repository.
     *
     * @return An Optional containing the list of all customers if available, or empty if none.
     */
    public Optional<List<Costumer>> getAllCustomer() {
        return costumerRepository.getAllCostumers();
    }
}
