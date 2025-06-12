package controller.show;

import controller.customerRepresentative.FindCustomerOfRepresentativeController;
import controller.network.AuthenticationController;
import domain.valueObjects.NIF;
import network.ShowDTO;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for retrieving detailed show information
 * for a customer represented by the currently logged-in customer representative.
 *
 * <p>This controller performs two main operations:</p>
 * <ul>
 *     <li>Finds the customer associated with a representative's email.</li>
 *     <li>Retrieves the list of shows associated with that customer.</li>
 * </ul>
 *
 * <p>This is typically used in UI flows where show details are needed
 * for validation, review, or selection purposes.</p>
 */
public class GetShowInfoController {

    private final FindCustomerOfRepresentativeController findCustomerOfRepresentativeController;
    private final FindShows4CustomerController findShows4CustomerController;

    /**
     * Constructs the controller using the provided {@link AuthenticationController}.
     * This enables delegation of authentication-aware operations to subcontrollers.
     *
     * @param authController The authentication controller for the current user session.
     */
    public GetShowInfoController(AuthenticationController authController) {
        findCustomerOfRepresentativeController = new FindCustomerOfRepresentativeController(authController);
        findShows4CustomerController = new FindShows4CustomerController(authController);
    }

    /**
     * Retrieves the {@link NIF} of the customer associated with the representative
     * currently authenticated (identified by their email).
     *
     * @param email The email of the logged-in representative.
     * @return The NIF of the customer they represent.
     * @throws RuntimeException if no customer is found for the given representative.
     */
    public NIF getCustomerNIFOfTheRepresentativeAssociated(String email) {
        Optional<NIF> foundCustomer = findCustomerOfRepresentativeController.getCustomerIDbyHisEmail(email);

        if (foundCustomer.isEmpty()) {
            throw new RuntimeException("✖ No customer was found for the corresponding representative logged in.");
        }

        return foundCustomer.get();
    }

    /**
     * Retrieves the list of shows associated with the given customer's {@link NIF}.
     *
     * @param customerNIF The NIF of the customer for whom the shows are to be retrieved.
     * @return An {@link Optional} containing a list of {@link ShowDTO} if any shows exist.
     * @throws RuntimeException if no shows are found for the given customer.
     */
    public Optional<List<ShowDTO>> getShowsForCustomer(NIF customerNIF) {
        Optional<List<ShowDTO>> showList4Customer = findShows4CustomerController.getShows4Customer(customerNIF);

        if (showList4Customer.isEmpty()) {
            throw new RuntimeException("✖ No shows found for customer with NIF [" + customerNIF + "].");
        }

        return showList4Customer;
    }
}
