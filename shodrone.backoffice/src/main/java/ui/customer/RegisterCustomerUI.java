package ui.customer;

import authz.*;
import controller.RegisterCustomerAndRepresentativeController;
import controller.user.RegisterUserController;
import domain.valueObjects.*;
import eapli.framework.infrastructure.authz.domain.model.Name;
import persistence.RepositoryProvider;
import persistence.interfaces.CostumerRepository;
import persistence.interfaces.CustomerRepresentativeRepository;
import utils.Utils;

/**
 * UI class responsible for collecting input and registering a new customer
 * along with an associated customer representative and user account.
 */
public class RegisterCustomerUI implements Runnable {

    /** Repository for managing customer entities. */
    private final CostumerRepository costumerRepo = RepositoryProvider.costumerRepository();

    /** Repository for managing customer representative entities. */
    private final CustomerRepresentativeRepository repRepo = RepositoryProvider.customerRepresentativeRepository();

    /** Controller to handle user account creation. */
    private final RegisterUserController userController = new RegisterUserController();

    /** Controller coordinating customer and representative registration. */
    private final RegisterCustomerAndRepresentativeController registerController;

    /**
     * Constructs the UI and sets up required repositories and controllers.
     */
    public RegisterCustomerUI() {
        this.registerController = new RegisterCustomerAndRepresentativeController(costumerRepo, repRepo, userController);
    }

    /**
     * Executes the registration process:
     * prompts for customer and representative data → validates input → performs registration.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("Register New Customer and Representative");

        // === CUSTOMER INFO ===
        Utils.showNameRules();
        Name customerName = null;
        while (customerName == null) {
            try {
                domain.valueObjects.Name rawCustomerName = Utils.rePromptForName("Customer name:");
                customerName = Utils.convertToName(rawCustomerName);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid name. Please try again.");
            }
        }

        Utils.showEmailRules();
        Email customerEmail = Utils.rePromptForEmail("Customer email:");

        PhoneNumber customerPhone = Utils.rePromptForPhone("Customer phone number:");
        NIF customerNif = Utils.rePromptForNIF("Customer NIF:");
        Address customerAddress = Utils.rePromptForAddress();

        // === REPRESENTATIVE INFO ===
        Utils.showNameRules();
        Name repName = null;
        while (repName == null) {
            try {
                domain.valueObjects.Name rawRepName = Utils.rePromptForName("Representative name:");
                repName = Utils.convertToName(rawRepName);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid name. Please try again.");
            }
        }

        Utils.showEmailRules();
        Email repEmail = Utils.rePromptForEmail("Representative email:");

        PhoneNumber repPhone = Utils.rePromptForPhone("Representative phone:");
        String repPosition = Utils.readLineFromConsole("Representative position:");

        // === PASSWORD ===
        Utils.showPasswordRules();
        String password = Utils.rePromptForPassword("Choose a password: ");

        // === REGISTRATION ===
        boolean success = registerController.registerCustomerAndRepresentative(
                customerName, customerEmail, customerPhone, customerNif, customerAddress,
                repName, repEmail, repPhone, repPosition, password
        );

        if (success) {
            Utils.printSuccessMessage("✅ Customer, representative and user registered successfully.");
        } else {
            Utils.printFailMessage("❌ Error registering the representative.");
        }
    }
}
