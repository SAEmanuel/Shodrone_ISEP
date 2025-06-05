package persistence;

import domain.entity.User;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining authentication-related operations.
 * Provides methods for logging in and out, checking session state, retrieving user roles,
 * managing user accounts, and accessing the current user session.
 */
public interface AuthenticationRepository {

    /**
     * Attempts to authenticate a user using the provided email and password.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return True if authentication is successful, false otherwise.
     */
    boolean doLogin(String email, String password);

    /**
     * Logs out the currently authenticated user.
     */
    void doLogout();

    /**
     * Checks if there is a user currently logged in.
     *
     * @return True if a user session is active and authenticated.
     */
    boolean isLoggedIn();

    /**
     * Retrieves the roles of the currently authenticated user.
     *
     * @return A list of {@link UserRoleDTO}, or an empty list if no user is logged in.
     */
    List<UserRoleDTO> getUserRoles();

    /**
     * Returns the current user session.
     *
     * @return The {@link UserSession} object representing the current session.
     */
    UserSession getCurrentUserSession();

    /**
     * Adds a new user role to the system.
     *
     * @param roleId      Unique identifier for the role.
     * @param description Description of the role.
     * @return True if the role was added successfully.
     */
    boolean addUserRole(String roleId, String description);

    /**
     * Creates a new user and assigns them a specific role.
     *
     * @param name     Full name of the user.
     * @param email    Email address of the user.
     * @param password Password for the user.
     * @param roleId   Role identifier to assign.
     * @return True if the user was created and assigned the role successfully.
     */
    boolean addUserWithRole(String name, String email, String password, String roleId);

    /**
     * Retrieves all registered users in the system.
     *
     * @return A list of {@link UserDTO} objects representing all users.
     */
    List<UserDTO> getAllUsers();

    Optional<User> findCustomerRepresentativeByEmail(String email);

    String getUserName(String email);
}
