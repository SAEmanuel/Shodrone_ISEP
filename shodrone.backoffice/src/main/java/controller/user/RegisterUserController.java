package controller.user;

import persistence.RepositoryProvider;
import persistence.AuthenticationRepository;

/**
 * Controller responsible for handling user registration.
 * Delegates the registration process to the configured {@link AuthenticationRepository}.
 */
public class RegisterUserController {

    /** Repository used for user registration and role assignment. */
    private final AuthenticationRepository repository;

    /**
     * Constructs the controller and initializes the authentication repository.
     */
    public RegisterUserController() {
        repository = RepositoryProvider.authenticationRepository();
    }

    /**
     * Registers a new user with the specified details and assigns a role.
     *
     * @param name     The user's name.
     * @param email    The user's email.
     * @param password The user's password.
     * @param roleId   The role to assign to the user.
     * @return True if registration is successful; false otherwise.
     */
    public boolean registerUser(String name, String email, String password, String roleId) {
        return repository.addUserWithRole(name, email, password, roleId);
    }
}
