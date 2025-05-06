package controller;

import domain.entity.Costumer;
import domain.entity.ShowRequest;
import persistence.RepositoryProvider;
import ui.FoundCostumerUI;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for listing show requests associated with a customer.
 * This controller allows the user to find a customer and retrieve all the show requests related to that customer.
 */
public class ListShowRequestByCostumerController {

    // User interface component for fetching customer data
    private final FoundCostumerUI foundCostumerUI;

    // Data of the selected customer
    private Costumer costumerSelected;

    /**
     * Constructor of the controller, initializing the user interface component.
     */
    public ListShowRequestByCostumerController() {
        this.foundCostumerUI = new FoundCostumerUI();
    }

    /**
     * Lists all show requests associated with the selected customer.
     * The method fetches the customer and queries the repository for their show requests.
     *
     * @return A list of show requests associated with the selected customer.
     * @throws IllegalArgumentException If no show requests are found for the customer.
     */
    public List<ShowRequest> listShowRequestByCostumer() {
        // Find the customer based on user input
        foundCostumerForRegistration();

        // Fetch the show requests from the repository
        Optional<List<ShowRequest>> showRequestList = RepositoryProvider.showRequestRepository().findByCostumer(costumerSelected);

        // If show requests are found, return them; otherwise, throw an exception
        if (showRequestList.isPresent()) {
            return showRequestList.get();
        } else {
            throw new IllegalArgumentException("No show requests found for the given customer.");
        }
    }

    /**
     * Finds and selects a customer for listing their show requests.
     * If no customer is selected, an exception is thrown.
     */
    private void foundCostumerForRegistration() {
        // Fetch the customer data through the UI
        Optional<Costumer> result = foundCostumerUI.foundCustomersUI();
        if(result.isEmpty()) {
            throw new IllegalArgumentException("No customer selected.");
        }
        costumerSelected = result.get();
    }
}
