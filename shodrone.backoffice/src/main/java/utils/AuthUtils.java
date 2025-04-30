package utils;

import factories.FactoryProvider;
import persistence.jpa.AuthenticationRepository;
import pt.isep.lei.esoft.auth.UserSession;

public class AuthUtils {

    private static AuthenticationRepository authRepo;

    private static AuthenticationRepository getAuthRepo() {
        if (authRepo == null) {
            authRepo = FactoryProvider.authenticationRepository();
        }
        return authRepo;
    }


    public static String getCurrentUserEmail() {
        AuthenticationRepository authRepo = getAuthRepo();

        UserSession session = authRepo.getCurrentUserSession();
        if (session != null && session.isLoggedIn()) {
            return session.getUserId().toString();
        }
        return null;
    }
}
