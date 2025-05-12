package controller;

import authz.Email;
import controller.authz.AuthenticationController;
import controller.user.RegisterUserController;
import domain.entity.Costumer;
import domain.entity.CustomerRepresentative;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import persistence.interfaces.CostumerRepository;
import persistence.interfaces.CustomerRepresentativeRepository;

import java.util.Optional;

/**
 * Handles the registration of a new {@link Costumer} along with a default {@link CustomerRepresentative}.
 * Combines business logic from the domain layer with user registration via {@link RegisterUserController}.
 */
public class RegisterCustomerAndRepresentativeController {

    /** Repository for persisting customer entities. */
    private final CostumerRepository costumerRepo;

    /** Repository for persisting customer representatives. */
    private final CustomerRepresentativeRepository repRepo;

    /** Controller responsible for creating the user account for the representative. */
    private final RegisterUserController userController;

    /**
     * Constructs a new controller with the required repositories and user controller.
     *
     * @param costumerRepo Repository for customer entities.
     * @param repRepo Repository for representative entities.
     * @param userController Controller to handle user creation.
     */
    public RegisterCustomerAndRepresentativeController(CostumerRepository costumerRepo,
                                                       CustomerRepresentativeRepository repRepo,
                                                       RegisterUserController userController) {
        this.costumerRepo = costumerRepo;
        this.repRepo = repRepo;
        this.userController = userController;
    }

    /**
     * Registers a new customer and an associated customer representative.
     * Also creates a system user for the representative.
     *
     * @param customerName Name of the customer (as defined by EAPLI).
     * @param customerEmail Email of the customer (wrapped in custom Email class).
     * @param customerPhone Phone number of the customer.
     * @param customerNif VAT number (NIF) of the customer.
     * @param customerAddress Address of the customer.
     * @param repName Name of the representative.
     * @param repEmail Email of the representative.
     * @param repPhone Phone number of the representative.
     * @param repPosition Position/title of the representative.
     * @param password Password to set for the representative's system account.
     * @return True if both customer and representative registration succeed, false otherwise.
     */
    public boolean registerCustomerAndRepresentative(Name customerName, Email customerEmail,
                                                     PhoneNumber customerPhone, NIF customerNif, Address customerAddress,
                                                     Name repName, Email repEmail, PhoneNumber repPhone, String repPosition,
                                                     String password) {

        domain.valueObjects.Name convertedName = convertToName(repName);
        EmailAddress eapliCustomerEmail = convertToEmailAddress(customerEmail);

        Costumer costumer = new Costumer(customerName, eapliCustomerEmail, customerPhone, customerNif, customerAddress);
        CustomerRepresentative representative = new CustomerRepresentative(costumer, convertedName, repEmail, repPhone, repPosition);
        costumer.addRepresentative(representative);

        Optional<Costumer> savedCostumer = costumerRepo.saveInStore(costumer, customerNif);
        if (savedCostumer.isEmpty()) {
            return false;
        }

        return userController.registerUser(
                repName.toString(), repEmail.getEmail(), password,
                AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE
        );
    }

    /**
     * Converts a framework {@link Name} into the project's domain-specific {@link domain.valueObjects.Name}.
     *
     * @param customerName The framework name object.
     * @return A new instance of the domain-specific Name object.
     */
    private domain.valueObjects.Name convertToName(eapli.framework.infrastructure.authz.domain.model.Name customerName) {
        return domain.valueObjects.Name.valueOf(customerName.toString());
    }

    /**
     * Converts a custom {@link Email} object to EAPLI's {@link EmailAddress} type.
     *
     * @param customerEmail Custom email object.
     * @return An EmailAddress object used by EAPLI.
     */
    private EmailAddress convertToEmailAddress(Email customerEmail) {
        return EmailAddress.valueOf(customerEmail.getEmail());
    }
}
