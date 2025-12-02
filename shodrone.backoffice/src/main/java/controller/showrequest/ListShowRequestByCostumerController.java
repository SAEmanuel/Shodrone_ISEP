package controller.showrequest;

import domain.entity.Costumer;
import domain.entity.ShowRequest;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for listing show requests associated with a customer.
 * This controller allows the user to find a customer and retrieve all the show requests related to that customer.
 */
public class ListShowRequestByCostumerController {

    /**
     * Constructor of the controller, initializing the user interface component.
     */
    public ListShowRequestByCostumerController() {}

    /**
     * Lists all show requests associated with the selected customer.
     * The method fetches the customer and queries the repository for their show requests.
     *
     * @return A list of show requests associated with the selected customer.
     * @throws IllegalArgumentException If no show requests are found for the customer.
     */
    public List<ShowRequest> listShowRequestByCostumer(Optional<Costumer> costumerSelected) {
        if(costumerSelected.isEmpty()) {
            throw new IllegalArgumentException("No customer selected.");
        }

        // Fetch the show requests from the repository
        Optional<List<ShowRequest>> showRequestList = RepositoryProvider.showRequestRepository().findByCostumer(costumerSelected.get());

        // If show requests are found, return them; otherwise, throw an exception
        if (showRequestList.isPresent()) {
            return showRequestList.get();
        } else {
            throw new IllegalArgumentException("No show requests found for the given customer.");
        }
    }
}
