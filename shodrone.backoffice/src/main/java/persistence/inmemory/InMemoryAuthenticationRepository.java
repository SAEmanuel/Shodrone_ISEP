package persistence.inmemory;

import persistence.interfaces.AuthenticationRepository;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;


public class InMemoryAuthenticationRepository implements AuthenticationRepository {
    private AuthFacade authenticationFacade;

    public InMemoryAuthenticationRepository() {
        authenticationFacade = new AuthFacade();
    }


    @Override
    public boolean doLogin(String email, String pwd) {
        return authenticationFacade.doLogin(email, pwd).isLoggedIn();
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
        return authenticationFacade.addUserWithRole(name, email, pwd, roleId);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return authenticationFacade.getUsers();
    }

    public void reset() {
        this.authenticationFacade = new AuthFacade();
    }
}
