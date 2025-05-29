package utils;

import persistence.RepositoryProvider;
import persistence.AuthenticationRepository;

/**
 * Utility class for retrieving authentication-related data.
 *
 * <p>This class provides static methods to interact with the current user session
 * via the configured {@link AuthenticationRepository}. It ensures a fallback to
 * a default system user email when no user is authenticated.</p>
 */
public class AuthUtils {

    /**
     * Cached instance of the authentication repository.
     */
    private static AuthenticationRepository authRepo;

    /**
     * Lazily retrieves the authentication repository instance from {@link RepositoryProvider}.
     *
     * @return the current {@link AuthenticationRepository} instance
     */
    private static AuthenticationRepository getAuthRepo() {
        if (authRepo == null) {
            authRepo = RepositoryProvider.authenticationRepository();
        }
        return authRepo;
    }

    /**
     * Returns the email of the currently logged-in user.
     *
     * <p>If no user is logged in, or the session is unavailable or invalid,
     * this method returns the default system email: {@code system@shodrone.app}.</p>
     *
     * @return the email of the current user, or {@code "system@shodrone.app"} if no session is active
     */
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
