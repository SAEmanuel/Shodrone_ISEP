package controller.user;

import constants.Roles;
import domain.entity.Costumer;
import domain.entity.Email;
import domain.entity.CustomerRepresentative;
import domain.valueObjects.Name;
import domain.valueObjects.PhoneNumber;
import persistence.RepositoryProvider;
import persistence.CustomerRepresentativeRepository;

public class RegisterRepresentativeController {

    private final CustomerRepresentativeRepository representativeRepository;
    private final RegisterUserController userController;

    public RegisterRepresentativeController() {
        this.representativeRepository = RepositoryProvider.customerRepresentativeRepository();
        this.userController = new RegisterUserController();
    }

    public RegisterRepresentativeController(CustomerRepresentativeRepository representativeRepository,
                                            RegisterUserController userController) {
        this.representativeRepository = representativeRepository;
        this.userController = userController;
    }

    public boolean registerRepresentative(Costumer costumer, Name name, Email email,
                                          PhoneNumber phone, String position, String password) {
        CustomerRepresentative representative = new CustomerRepresentative(costumer, name, email, phone, position);
        representativeRepository.saveInStore(representative);
        return userController.registerUser(
                name.name(),
                email.getEmail(),
                password,
                Roles.ROLE_CUSTOMER_REPRESENTATIVE
        );
    }
}
