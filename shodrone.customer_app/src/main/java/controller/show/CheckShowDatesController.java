package controller.show;

import controller.customerRepresentative.FindCustomerOfRepresentativeController;
import controller.network.AuthenticationController;
import domain.entity.Costumer;
import domain.entity.Show;
import domain.valueObjects.NIF;
import network.ShowDTO;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for managing the process of checking scheduled shows for a customer
 * associated with a logged-in customer representative.
 *
 * <p>This controller uses other controllers to:
 * <ul>
 *     <li>Find the customer associated with a representative (by email).</li>
 *     <li>Retrieve the list of shows for that customer.</li>
 * </ul>
 *
 * <p>Typical use case:
 * <ol>
 *     <li>Authenticate a representative.</li>
 *     <li>Retrieve the associated customer NIF using {@link #getCustomerNIFOfTheRepresentativeAssociated(String)}.</li>
 *     <li>Use that NIF to get the list of {@link ShowDTO} using {@link #getShowsForCustomer(NIF)}.</li>
 * </ol>
 *
 * @author
 */
public class CheckShowDatesController {

    private final FindCustomerOfRepresentativeController findCustomerOfRepresentativeController;
    private final FindShows4CustomerController findShows4CustomerController;

    /**
     * Constructs the controller and initializes dependencies using the provided authentication controller.
     *
     * @param authController The authentication controller used to identify the logged-in representative.
     */
    public CheckShowDatesController(AuthenticationController authController) {
        findCustomerOfRepresentativeController = new FindCustomerOfRepresentativeController(authController);
        findShows4CustomerController = new FindShows4CustomerController(authController);
    }

    /**
     * Retrieves the NIF of the customer associated with the representative logged in via the given email.
     *
     * @param email The email of the logged-in customer representative.
     * @return The NIF of the associated customer.
     * @throws RuntimeException if no customer is found for the given email.
     */
    public NIF getCustomerNIFOfTheRepresentativeAssociated(String email) {
        Optional<NIF> foundCustomer = findCustomerOfRepresentativeController.getCustomerIDbyHisEmail(email);

        if (foundCustomer.isEmpty()) {
            throw new RuntimeException("✖ No customer was found for the corresponding representative logged in.");
        }

        return foundCustomer.get();
    }

    /**
     * Retrieves a list of shows (as DTOs) for a given customer identified by their NIF.
     *
     * @param customerNIF The NIF of the customer whose shows should be retrieved.
     * @return An {@link Optional} list of {@link ShowDTO} representing the shows of the customer.
     * @throws RuntimeException if no shows are found for the customer.
     */
    public Optional<List<ShowDTO>> getShowsForCustomer(NIF customerNIF) {
        Optional<List<ShowDTO>> showList4Customer = findShows4CustomerController.getShows4Customer(customerNIF);

        if (showList4Customer.isEmpty()) {
            throw new RuntimeException("✖ No shows found for customer with NIF [" + customerNIF + "].");
        }

        return showList4Customer;
    }
}
