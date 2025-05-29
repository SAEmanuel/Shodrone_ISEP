package ui.customer;

import controller.user.RegisterCustomerController;
import controller.user.RegisterRepresentativeController;
import domain.entity.Costumer;
import domain.valueObjects.*;
import domain.entity.Email;
import eapli.framework.general.domain.model.EmailAddress;
import utils.Utils;

import java.util.Optional;

public class RegisterCustomerUI implements Runnable {

    private final RegisterCustomerController customerController = new RegisterCustomerController();
    private final RegisterRepresentativeController repController = new RegisterRepresentativeController();

    @Override
    public void run() {
        try {
            Utils.showNameRules();
            Name customerName = Utils.rePromptForName("Customer Name");

            Utils.showEmailRules();
            EmailAddress customerEmail = Utils.rePromptForValidEmailAddress("Customer Email");
            PhoneNumber customerPhone = Utils.rePromptForPhone("Customer Phone");
            NIF nif = Utils.rePromptForNIF("Customer NIF");
            //aqui
            Address address = Utils.rePromptForAddress();

            Utils.showNameRules();
            Name repName = Utils.rePromptForName("Representative Name");

            Utils.showEmailRules();
            Email repEmail = Utils.rePromptForEmail("Representative Email");
            PhoneNumber repPhone = Utils.rePromptForPhone("Representative Phone");
            //aqui
            String repPosition = Utils.rePromptForNonEmptyLine("Representative Position");

            Utils.showPasswordRules();
            String repPassword = Utils.rePromptForPassword("Representative Password");

            Optional<Costumer> savedCustomer = customerController.registerCustomer(
                    customerName, customerEmail, customerPhone, nif, address
            );

            if (savedCustomer.isEmpty()) {
                Utils.printFailMessage("❌ Failed to register Customer. A customer with that NIF may already exist.");
                return;
            }

            boolean repSuccess = repController.registerRepresentative(
                    savedCustomer.get(), repName, repEmail, repPhone, repPosition, repPassword
            );

            if (repSuccess) {
                Utils.printSuccessMessage("✅ Customer and Representative registered successfully!");
            } else {
                Utils.printFailMessage("❌ Customer created but failed to register representative.");
            }

        } catch (Exception e) {
            Utils.printFailMessage("❌ Unexpected error: " + e.getMessage());
        }
    }
}
