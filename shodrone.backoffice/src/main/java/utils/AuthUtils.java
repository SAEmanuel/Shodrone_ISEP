package utils;

import persistence.RepositoryProvider;
import persistence.interfaces.AuthenticationRepository;


public class AuthUtils {

    private static AuthenticationRepository authRepo;

    private static AuthenticationRepository getAuthRepo() {
        if (authRepo == null) {
            authRepo = RepositoryProvider.authenticationRepository();
        }
        return authRepo;
    }

    public static String getCurrentUserEmail() {
        AuthenticationRepository authRepo = getAuthRepo();
        String email = authRepo.getCurrentUserSession().getUserId().toString();
        return email != null ? email.toString() : null;
    }
}