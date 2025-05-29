package persistence.jpa.JPAImpl;

import authz.*;
import domain.entity.User;
import persistence.AuthenticationRepository;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;
import persistence.jpa.JpaBaseRepository;
import domain.entity.Email;
import domain.entity.UserRole;
import domain.entity.Password;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA-based implementation of the {@link AuthenticationRepository}.
 * Manages user login, logout, session tracking, user-role management, and persistence using JPA.
 */
public class AuthenticationRepositoryJPAImpl extends JpaBaseRepository<User, Email> implements AuthenticationRepository {

    /** Currently logged-in user. Set on successful login, cleared on logout. */
    private User loggedUser;

    /** Internal repository for managing user roles. */
    private final UserRoleRepository roleRepository;

    /**
     * Constructs a new JPA-based authentication repository and initializes the role repository.
     */
    public AuthenticationRepositoryJPAImpl() {
        super();
        this.roleRepository = new UserRoleRepository();
    }

    /**
     * Attempts to log in a user using the given email and password.
     * Validates credentials and active status.
     *
     * @param emailString    The user's email as a string.
     * @param passwordString The user's password.
     * @return True if login is successful, false otherwise.
     * @throws IllegalStateException if the user is found but is disabled.
     */
    @Override
    public boolean doLogin(String emailString, String passwordString) {
        Email email = new Email(emailString);
        User user = findById(email);
        if (user != null) {
            entityManager().refresh(user);
        }
        if (user != null && user.hasPassword(passwordString)) {

            if (!user.isActive()) {
                throw new IllegalStateException("❌ User is disabled.");
            }

            this.loggedUser = user;
            return true;
        }
        return false;
    }

    /**
     * Logs out the currently authenticated user.
     */
    @Override
    public void doLogout() {
        this.loggedUser = null;
    }

    /**
     * Checks whether a user is currently logged in.
     *
     * @return True if a user is logged in; false otherwise.
     */
    @Override
    public boolean isLoggedIn() {
        return loggedUser != null;
    }

    /**
     * Retrieves the roles of the currently logged-in user.
     *
     * @return A list of {@link UserRoleDTO}, or null if not logged in.
     */
    @Override
    public List<UserRoleDTO> getUserRoles() {
        if (!isLoggedIn()) {
            return null;
        }
        return loggedUser.getRoles().stream()
                .map(role -> new UserRoleDTO(role.getId(), role.getDescription()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the current user session as a {@link UserSession} object.
     *
     * @return The user session, or null if not logged in.
     */
    @Override
    public UserSession getCurrentUserSession() {
        if (loggedUser == null) return null;

        return new UserSession(
                new pt.isep.lei.esoft.auth.domain.model.User(
                        new pt.isep.lei.esoft.auth.domain.model.Email(loggedUser.getId().toString()),
                        new pt.isep.lei.esoft.auth.domain.model.Password(loggedUser.getPassword().toString()),
                        loggedUser.getName()
                )
        );
    }

    /**
     * Retrieves the currently logged-in user entity.
     *
     * @return The logged-in {@link User}, or null if no user is logged in.
     */
    public User getCurrentUser() {
        return loggedUser;
    }

    /**
     * Persists a user in the database using JPA.
     *
     * @param user The user to save.
     */
    public void saveUser(User user) {
        add(user);
    }

    /**
     * Persists a user role using the internal role repository.
     *
     * @param role The role to save.
     */
    public void saveRole(UserRole role) {
        roleRepository.save(role);
    }

    /**
     * Retrieves all roles stored in the system.
     *
     * @return A list of {@link UserRole} objects.
     */
    public List<UserRole> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Returns the role repository used internally by this class.
     *
     * @return The {@link UserRoleRepository} instance.
     */
    public UserRoleRepository getRoleRepository() {
        return this.roleRepository;
    }

    /**
     * Adds a new role if it does not already exist.
     *
     * @param roleId      Role ID to assign.
     * @param description Description of the role.
     * @return True if the role was added; false if it already exists.
     */
    @Override
    public boolean addUserRole(String roleId, String description) {
        if (roleRepository.findById(roleId).isEmpty()) {
            saveRole(new UserRole(roleId, description));
            return true;
        }
        return false;
    }

    /**
     * Creates and saves a new user with the given role.
     * Returns false if a user with the same email already exists.
     *
     * @param name     User's name.
     * @param email    User's email.
     * @param password User's password.
     * @param roleId   Role to assign.
     * @return True if the user was created successfully.
     * @throws IllegalStateException if the role does not exist.
     */
    @Override
    public boolean addUserWithRole(String name, String email, String password, String roleId) {
        Email userEmail = new Email(email);

        if (findById(userEmail) != null) return false;

        UserRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalStateException("❌ Role not found: " + roleId));

        User user = new User(userEmail, new Password(password), name);
        user.addRole(role);
        saveUser(user);

        return true;
    }

    /**
     * Retrieves all registered users as DTOs.
     *
     * @return A list of {@link UserDTO} objects.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return findAll().stream()
                .map(user -> new UserDTO(user.getId().toString(), user.getName(), user.getRoles().stream()
                        .map(role -> new UserRoleDTO(role.getId(), role.getDescription()))
                        .toList()))
                .toList();
    }
}
