package controller;

import persistence.RepositoryProvider;
import persistence.interfaces.AuthenticationRepository;

public class RegisterUserController {
    private final AuthenticationRepository repository;

    public RegisterUserController() {
        repository = RepositoryProvider.authenticationRepository();
    }

    public boolean registerUser(String name, String email, String password, String roleId) {
         return repository.addUserWithRole(name, email, password, roleId);
    }
}
