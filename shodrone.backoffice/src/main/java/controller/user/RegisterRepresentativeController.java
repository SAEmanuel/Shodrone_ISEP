package controller.user;

import authz.Email;
import domain.entity.Costumer;
import domain.entity.CustomerRepresentative;
import domain.valueObjects.Name;
import domain.valueObjects.PhoneNumber;
import persistence.RepositoryProvider;
import persistence.interfaces.CustomerRepresentativeRepository;
import controller.authz.AuthenticationController;

public class RegisterRepresentativeController {

    private final CustomerRepresentativeRepository representativeRepository;
    private final RegisterUserController userController;

    public RegisterRepresentativeController() {
        this.representativeRepository = RepositoryProvider.customerRepresentativeRepository();
        this.userController = new RegisterUserController();
    }

    public boolean registerRepresentative(Costumer costumer, Name name, Email email,
                                          PhoneNumber phone, String position, String password) {
        CustomerRepresentative representative = new CustomerRepresentative(costumer, name, email, phone, position);
        representativeRepository.save(representative);
        return userController.registerUser(
                name.name(),
                email.getEmail(),
                password,
                AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE
        );
    }
}
