package controller.authz;

import persistence.RepositoryProvider;
import persistence.interfaces.AuthenticationRepository;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * Controller responsible for handling user authentication and role retrieval.
 * Acts as an interface between the UI/business logic and the authentication repository.
 */
public class AuthenticationController {

    /** Role identifier for system administrators. */
    public static final String ROLE_ADMIN = "ADMINISTRATOR";

    /** Role identifier for CRM managers. */
    public static final String ROLE_CRM_MANAGER = "CRM MANAGER";

    /** Role identifier for CRM collaborators. */
    public static final String ROLE_CRM_COLLABORATOR = "CRM COLLABORATOR";

    /** Role identifier for show designers. */
    public static final String ROLE_SHOW_DESIGNER = "SHOW DESIGNER";

    /** Role identifier for customer representatives. */
    public static final String ROLE_CUSTOMER_REPRESENTATIVE = "CUSTOMER REPRESENTATIVE";

    /** Role identifier for drone technicians. */
    public static final String ROLE_DRONE_TECH = "DRONE TECHNICIAN";

    /** Role identifier for customers. */
    public static final String ROLE_CUSTOMER = "CUSTOMER";

    /** Reference to the authentication repository for performing login/logout operations. */
    private final AuthenticationRepository authRepository;

    /**
     * Constructs the controller and retrieves the authentication repository instance.
     */
    public AuthenticationController() {
        authRepository = RepositoryProvider.authenticationRepository();
    }

    /**
     * Attempts to authenticate a user with the provided email and password.
     *
     * @param email User's email.
     * @param pwd   User's password.
     * @return True if login is successful, false otherwise.
     */
    public boolean doLogin(String email, String pwd) {
        try {
            return authRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Retrieves the roles of the currently authenticated user.
     *
     * @return A list of {@link UserRoleDTO} if logged in, or null otherwise.
     */
    public List<UserRoleDTO> getUserRoles() {
        if (authRepository.isLoggedIn()) {
            return authRepository.getUserRoles();
        }
        return null;
    }

    /**
     * Logs out the currently authenticated user.
     */
    public void doLogout() {
        authRepository.doLogout();
    }
}
