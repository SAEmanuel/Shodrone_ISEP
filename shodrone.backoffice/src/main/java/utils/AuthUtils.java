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
        AuthenticationRepository repo = getAuthRepo();

        if (repo == null || !repo.isLoggedIn()) {
            return "system@shodrone.app";
        }

        var session = repo.getCurrentUserSession();
        if (session == null || session.getUserId() == null) {
            return "system@shodrone.app";
        }

        return session.getUserId().toString();
    }
}