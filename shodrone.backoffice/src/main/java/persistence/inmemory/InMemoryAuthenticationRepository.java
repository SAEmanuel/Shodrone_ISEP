package persistence.inmemory;

import authz.*;
import persistence.interfaces.AuthenticationRepository;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.*;


public class InMemoryAuthenticationRepository implements AuthenticationRepository, UserRepository {
    private AuthFacade authenticationFacade;
    private final Map<String, Boolean> userActivationState = new HashMap<>();
    private final Map<Email, User> userStore = new HashMap<>();


    public InMemoryAuthenticationRepository() {
        authenticationFacade = new AuthFacade();
    }


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


    @Override
    public void doLogout() {
        authenticationFacade.doLogout();
    }

    @Override
    public boolean isLoggedIn() {
        return authenticationFacade.getCurrentUserSession() != null &&
                authenticationFacade.getCurrentUserSession().isLoggedIn();
    }

    @Override
    public List<UserRoleDTO> getUserRoles() {
        UserSession session = authenticationFacade.getCurrentUserSession();
        if (session != null && session.isLoggedIn()) {
            return session.getUserRoles();
        }
        return List.of();
    }

    @Override
    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }


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



    @Override
    public List<UserDTO> getAllUsers() {
        return authenticationFacade.getUsers();
    }

    public void reset() {
        this.authenticationFacade = new AuthFacade();
    }

    public void setUserActive(String email, boolean active) {
        userActivationState.put(email.toLowerCase(), active);
    }

    @Override
    public Optional<User> ofIdentity(Email id) {
        return Optional.ofNullable(userStore.get(id));
    }

    @Override
    public void save(User user) {
        userStore.put(user.getId(), user);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStore.values());
    }


}
