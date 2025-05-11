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

public class RegisterCustomerAndRepresentativeController {

    private final CostumerRepository costumerRepo;
    private final CustomerRepresentativeRepository repRepo;
    private final RegisterUserController userController;

    public RegisterCustomerAndRepresentativeController(CostumerRepository costumerRepo,
                                                       CustomerRepresentativeRepository repRepo,
                                                       RegisterUserController userController) {
        this.costumerRepo = costumerRepo;
        this.repRepo = repRepo;
        this.userController = userController;
    }

    public boolean registerCustomerAndRepresentative(Name customerName, Email customerEmail,
                                                     PhoneNumber customerPhone, NIF customerNif, Address customerAddress,
                                                     Name repName, Email repEmail, PhoneNumber repPhone, String repPosition,
                                                     String password) {
        domain.valueObjects.Name covertedName = convertToName(repName);
        EmailAddress eapliCustomerEmail = convertToEmailAddress(customerEmail);

        Costumer costumer = new Costumer(customerName, eapliCustomerEmail, customerPhone, customerNif, customerAddress);
        CustomerRepresentative representative = new CustomerRepresentative(costumer, covertedName, repEmail, repPhone, repPosition);
        costumer.addRepresentative(representative);

        Optional<Costumer> savedCostumer = costumerRepo.saveInStore(costumer, customerNif);
        if (savedCostumer.isEmpty()) {
            return false;
        }

        boolean success = userController.registerUser(
                repName.toString(), repEmail.getEmail(), password,
                AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE
        );

        return success;
    }

    private domain.valueObjects.Name convertToName(eapli.framework.infrastructure.authz.domain.model.Name customerName) {
        return domain.valueObjects.Name.valueOf(customerName.toString());
    }


    private EmailAddress convertToEmailAddress(Email customerEmail) {
        return EmailAddress.valueOf(customerEmail.getEmail());
    }
}
