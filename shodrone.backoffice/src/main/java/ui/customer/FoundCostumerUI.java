package ui.customer;

import controller.showrequest.ListCostumersController;
import domain.entity.Costumer;
import utils.Utils;
import utils.Validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UI class responsible for finding and selecting a customer through various criteria.
 * <p>
 * Allows searching by ID, NIF, or viewing the full list of customers. Useful in workflows
 * where identifying a customer is a prerequisite for further operations (e.g., creating a show request).
 */
public class FoundCostumerUI implements Runnable {
    /** Controller responsible for customer retrieval logic. */
    private final ListCostumersController listcustomerscontroller;

    /**
     * Constructs the UI with a new instance of {@link ListCostumersController}.
     */
    public FoundCostumerUI() {
        listcustomerscontroller = new ListCostumersController();
    }

    /**
     * Provides access to the customer controller.
     *
     * @return the {@link ListCostumersController} instance
     */
    private ListCostumersController getListcustomerscontroller() {
        return listcustomerscontroller;
    }

    /**
     * Entry point for running the UI via {@link Runnable}. Calls the interactive customer search interface.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("FOUND CUSTOMER");
        try {
            foundCustomersUI();
        }catch(Exception e){}
    }

    /**
     * Displays options to search for a customer and performs the corresponding search.
     *
     * <p>The user can choose to search by:
     * <ul>
     *     <li>Customer ID</li>
     *     <li>Customer NIF</li>
     *     <li>Display all customers and select one from the list</li>
     * </ul>
     *
     * @return an {@link Optional} containing the selected {@link Costumer}, or empty if not found or aborted
     */
    public Optional<Costumer> foundCustomersUI() {
        List<String> options = new ArrayList<>();
        options.add("Search Customer by ID");
        options.add("Search Customer by NIF");
        options.add("Show All Customers");
        int option = Utils.showAndSelectIndexCustomOptions(options, "Search Customer");
        Optional<?> optionalResult = Optional.empty();

        switch (option) {
            case 0:
                optionalResult = foundUniqueCostumerIDUI(option);
                didMethodFoundCostumer(optionalResult);
                break;
            case 1:
                optionalResult = foundUniqueCostumerNIFUI(option);
                didMethodFoundCostumer(optionalResult);
                break;
            case 2:
                optionalResult = foundListCostumerUI(option);
                optionalResult = Utils.showAndSelectObjectFromList((Optional<List<?>>) optionalResult,"Costumers");
                break;
            default:
                Utils.exitImmediately("Cannot register a show request without a customer.");
        }
        return (Optional<Costumer>) optionalResult;
    }

    /**
     * Prompts the user to insert a customer ID and searches for a unique match.
     *
     * @param option the option index (not used but passed for consistency)
     * @return an {@link Optional} of the found {@link Costumer}, or empty if not found
     */
    private Optional<Costumer> foundUniqueCostumerIDUI(int option) {
        return getListcustomerscontroller().foundCustomerByID(Utils.readLongFromConsole("Insert the Customer ID"));
    }

    /**
     * Prompts the user to insert a NIF and searches for a unique customer match.
     *
     * @param option the option index (not used but passed for consistency)
     * @return an {@link Optional} of the found {@link Costumer}, or empty if not found
     */
    private Optional<Costumer> foundUniqueCostumerNIFUI(int option) {
        return getListcustomerscontroller().foundCustomerByNIF(Utils.readNIFFromConsole("Insert the Customer NIF"));
    }

    /**
     * Fetches the full list of registered customers.
     *
     * @param option the option index (not used but passed for consistency)
     * @return an {@link Optional} list of {@link Costumer}s, or empty if none exist
     */
    private Optional<List<Costumer>> foundListCostumerUI(int option){
        return getListcustomerscontroller().getAllCustomer();
    }

    /**
     * Verifies if the customer search result was successful and prints the outcome.
     * Exits the program if no customer was found.
     *
     * @param result the {@link Optional} result of a customer search
     */
    private void didMethodFoundCostumer(Optional<?> result){
        if(Validations.isNotEmptyOptional(result)){
            Utils.printOptionalValidMessage("Customers found",result);
        }else{
            Utils.exitImmediately("No customer/s found in system. Please register a new customer in 'Register Customer' menu option.");
        }
    }
}