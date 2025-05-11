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

public class RegisterCustomerUI implements Runnable {

    private final CostumerRepository costumerRepo = RepositoryProvider.costumerRepository();
    private final CustomerRepresentativeRepository repRepo = RepositoryProvider.customerRepresentativeRepository();
    private final RegisterUserController userController = new RegisterUserController();
    private final RegisterCustomerAndRepresentativeController registerController;

    public RegisterCustomerUI() {
        this.registerController = new RegisterCustomerAndRepresentativeController(costumerRepo, repRepo, userController);
    }

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
