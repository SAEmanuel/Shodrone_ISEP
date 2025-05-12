package ui.showrequest;

import controller.showrequest.ListShowRequestByCostumerController;
import domain.entity.ShowRequest;
import ui.customer.FoundCostumerUI;
import utils.Utils;

import java.util.List;

/**
 * UI class responsible for listing all show requests associated with a given customer.
 *
 * <p>This interface prompts the user for customer-related input (e.g., identifier or selection),
 * and then retrieves and displays all related show requests.</p>
 */
public class ListShowRequestByCostumerUI implements Runnable {

    /** Controller responsible for retrieving show requests by customer. */
    private final ListShowRequestByCostumerController controller;
    private final FoundCostumerUI foundCostumerUI;
    /**
     * Constructs the UI with a new instance of {@link ListShowRequestByCostumerController}.
     */
    public ListShowRequestByCostumerUI() {
        controller = new ListShowRequestByCostumerController();
        foundCostumerUI = new FoundCostumerUI();
    }

    /**
     * Returns the controller used for accessing show request data by customer.
     *
     * @return the {@link ListShowRequestByCostumerController} instance
     */
    private ListShowRequestByCostumerController getRegisterShowcontroller() {
        return controller;
    }

    /**
     * Executes the UI flow to list show requests by a specific customer.
     *
     * <p>Prompts for customer information, retrieves related show requests,
     * and prints a summary of each request to the terminal. Handles any exceptions
     * by displaying an alert message.</p>
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("LIST SHOW REQUEST OF COSTUMER");
        try {
            Utils.printCenteredSubtitle("Costumer information");
            List<ShowRequest> showRequestListByCostumer = getRegisterShowcontroller().listShowRequestByCostumer(foundCostumerUI.foundCustomersUI());

            Utils.printCenteredSubtitle("Show's information");
            for (ShowRequest showRequest : showRequestListByCostumer) {
                Utils.printShowRequestResume(showRequest);
                Utils.dropLines(2);
            }
            Utils.waitForUser();

        } catch (Exception e) {
            Utils.printAlterMessage(e.getMessage());
        }
    }
}
