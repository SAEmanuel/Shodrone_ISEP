package persistence.inmemory;

import constants.Roles;
import domain.entity.User;
import persistence.AuthenticationRepository;
import persistence.UserRepository;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;
import domain.entity.Email;
import domain.entity.Password;

import java.util.*;

/**
 * In-memory implementation of both {@link AuthenticationRepository} and {@link UserRepository}.
 * Manages user authentication and role-based access using {@link AuthFacade},
 * and persists users and their active state in local memory.
 *
 * Intended for development, testing, or temporary usage without a backing database.
 */
public class InMemoryAuthenticationRepository implements AuthenticationRepository, UserRepository {

    /** Handles authentication logic and session management. */
    private AuthFacade authenticationFacade;

    /** Stores active/inactive status for users, keyed by lowercase email. */
    private final Map<String, Boolean> userActivationState = new HashMap<>();

    /** In-memory store of users, keyed by email. */
    private final Map<Email, User> userStore = new HashMap<>();

    /**
     * Constructs a new in-memory authentication repository,
     * initializing the internal authentication facade.
     */
    public InMemoryAuthenticationRepository() {
        authenticationFacade = new AuthFacade();
    }

    /**
     * Attempts to log in a user using their email and password.
     * Also checks whether the user is active.
     *
     * @param email User's email.
     * @param pwd   User's password.
     * @return True if login is successful and user is active.
     * @throws IllegalStateException If the user is disabled.
     */
    @Override
    public boolean doLogin(String email, String pwd) {
        UserSession session = authenticationFacade.doLogin(email, pwd);

        if (!session.isLoggedIn()) {
            return false;
        }

        Boolean active = userActivationState.getOrDefault(email.toLowerCase(), true);
        if (!active) {
            authenticationFacade.doLogout();
            throw new IllegalStateException("User is disabled.");
        }

        return true;
    }

    /**
     * Logs out the current user session.
     */
    @Override
    public void doLogout() {
        authenticationFacade.doLogout();
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user session is active and logged in.
     */
    @Override
    public boolean isLoggedIn() {
        return authenticationFacade.getCurrentUserSession() != null &&
                authenticationFacade.getCurrentUserSession().isLoggedIn();
    }

    /**
     * Retrieves the roles of the currently authenticated user.
     *
     * @return A list of user roles or an empty list if not logged in.
     */
    @Override
    public List<UserRoleDTO> getUserRoles() {
        UserSession session = authenticationFacade.getCurrentUserSession();
        if (session != null && session.isLoggedIn()) {
            return session.getUserRoles();
        }
        return List.of();
    }

    /**
     * Retrieves the current user session object.
     *
     * @return The active {@link UserSession}, or null if not logged in.
     */
    @Override
    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    /**
     * Adds a new user role to the authentication system.
     *
     * @param id          Unique role identifier.
     * @param description Description of the role.
     * @return True if the role was added successfully.
     */
    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }

    /**
     * Registers a new user and assigns them a role.
     * Also saves the user in the in-memory store and sets them as active.
     *
     * @param name   User's name.
     * @param email  User's email.
     * @param pwd    User's password.
     * @param roleId Role to assign.
     * @return True if the user was added successfully.
     */
    public boolean addUserWithRole(String name, String email, String pwd, String roleId) {
        boolean added = authenticationFacade.addUserWithRole(name, email, pwd, roleId);
        if (added) {
            Email userEmail = new Email(email);
            Password password = new Password(pwd);
            User user = new User(userEmail, password, name);
            user.activate();
            save(user);
            userActivationState.put(email.toLowerCase(), true);
        }
        return added;
    }

    /**
     * Retrieves a list of all registered users in DTO format.
     *
     * @return List of users as {@link UserDTO}.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return authenticationFacade.getUsers();
    }

    /**
     * Resets the internal authentication facade instance.
     * Clears any state and creates a new facade.
     */
    public void reset() {
        this.authenticationFacade = new AuthFacade();
    }

    /**
     * Updates the activation state of a user.
     *
     * @param email  The user's email.
     * @param active True to activate, false to deactivate.
     */
    public void setUserActive(String email, boolean active) {
        userActivationState.put(email.toLowerCase(), active);
    }

    /**
     * Retrieves a user from the in-memory store by their email.
     *
     * @param id The email identifier.
     * @return An {@link Optional} containing the user if found.
     */
    @Override
    public Optional<User> ofIdentity(Email id) {
        return Optional.ofNullable(userStore.get(id));
    }

    /**
     * Saves a user in the in-memory store.
     * If the user already exists, it will be overwritten.
     *
     * @param user The user to save.
     */
    @Override
    public void save(User user) {
        userStore.put(user.getId(), user);
    }

    /**
     * Retrieves all users stored in memory.
     *
     * @return A list of all users.
     */
    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStore.values());
    }

    @Override
    public Optional<User> findCustomerRepresentativeByEmail(String email) {
        for (User user : userStore.values()) {
            if (user.getId().toString().equalsIgnoreCase(email)) {
                if (user.hasRole(Roles.ROLE_CUSTOMER_REPRESENTATIVE)) {
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public String getUserName(String email) {
        return userStore.values().stream()
                .filter(user -> user.getId().toString().equalsIgnoreCase(email))
                .findFirst()
                .map(User::getName)
                .orElse("");
    }

}
