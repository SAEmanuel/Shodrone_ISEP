package ui;

import authz.Email;
import controller.AuthenticationController;
import controller.RegisterUserController;
import domain.entity.Costumer;
import domain.entity.CustomerRepresentative;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import persistence.RepositoryProvider;
import persistence.interfaces.CostumerRepository;
import utils.Utils;

import java.util.Optional;
import java.util.Scanner;
import java.util.Arrays;

public class RegisterCustomerUI implements Runnable {

    private final CostumerRepository costumerRepo = RepositoryProvider.costumerRepository();
    private final RegisterUserController userController = new RegisterUserController();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        Utils.printCenteredTitle("Register New Customer and Representative");

        // === CUSTOMER INFO ===
        Utils.showNameRules();
        domain.valueObjects.Name rawCustomerName = Utils.rePromptWhileInvalid("Customer name:", domain.valueObjects.Name::new);
        String[] custParts = rawCustomerName.name().trim().split(" ");
        if (custParts.length < 2) {
            Utils.printFailMessage("❌ Please provide both first and last name.");
            return;
        }
        Name customerName = Name.valueOf(
                custParts[0],
                String.join(" ", Arrays.copyOfRange(custParts, 1, custParts.length))
        );

        Utils.showEmailRules();
        Email rawEmail = Utils.rePromptWhileInvalid("Customer email:", Email::new);
        EmailAddress customerEmail = EmailAddress.valueOf(rawEmail.getEmail());

        PhoneNumber customerPhone = Utils.rePromptWhileInvalid("Customer phone number:", PhoneNumber::new);
        NIF customerNif = Utils.rePromptWhileInvalid("Customer NIF:", NIF::new);

        Address customerAddress = null;
        while (customerAddress == null) {
            try {
                customerAddress = Utils.promptForAddress();
            } catch (IllegalArgumentException e) {
                Utils.printFailMessage("❌ " + e.getMessage());
            }
        }

        // === REPRESENTATIVE INFO ===
        Utils.printCenteredSubtitle("Representative Information");

        domain.valueObjects.Name rawRepName = Utils.rePromptWhileInvalid("Representative name:", domain.valueObjects.Name::new);
        String[] repParts = rawRepName.name().trim().split(" ");
        if (repParts.length < 2) {
            Utils.printFailMessage("❌ Please provide both first and last name.");
            return;
        }
        Name repName = Name.valueOf(
                repParts[0],
                String.join(" ", Arrays.copyOfRange(repParts, 1, repParts.length))
        );

        domain.valueObjects.Name repLocalName = new domain.valueObjects.Name(repName.toString());

        Email repEmail = Utils.rePromptWhileInvalid("Representative email:", Email::new);
        PhoneNumber repPhone = Utils.rePromptWhileInvalid("Representative phone:", PhoneNumber::new);

        System.out.print("Representative position: ");
        String repPosition = scanner.nextLine();

        // === Create Customer and Representative ===
        Costumer costumer = new Costumer(customerName, customerEmail, customerPhone, customerNif, customerAddress);
        CustomerRepresentative rep = new CustomerRepresentative(costumer, repLocalName, repEmail, repPhone, repPosition);
        costumer.addRepresentative(rep); // 🔥 ADICIONADO ANTES DA PERSISTÊNCIA

        Optional<Costumer> savedCostumer = costumerRepo.saveInStore(costumer, customerNif);

        if (savedCostumer.isEmpty()) {
            Utils.printFailMessage("❌ Failed to save customer.");
            return;
        }

        // === USER CREATION ===
        String password = "Temporary123!";
        boolean success = userController.registerUser(
                repName.toString(), repEmail.getEmail(), password,
                AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE
        );

        if (success) {
            Utils.printSuccessMessage("✅ Customer, representative and user registered successfully.");
        } else {
            Utils.printFailMessage("❌ Error registering the representative.");
        }
    }
}
