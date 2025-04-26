package controller;

import persistence.inmemory.InMemoryAuthenticationRepository;
import persistence.inmemory.Repositories;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;


public class AuthenticationController {

    public static final String ROLE_ADMIN = "ADMINISTRATOR";
    public static final String ROLE_CRM_MANAGER = "CRM MANAGER";
    public static final String ROLE_CRM_COLLABORATOR = "CRM COLLABORATOR";
    public static final String ROLE_SHOW_DESIGNER= "SHOW DESIGNER";
    public static final String ROLE_CUSTOMER_REPRESENTATIVE= "CUSTOMER REPRESENTATIVE";
    public static final String ROLE_DRONE_TECH= "Drone Technician";

    //private final ApplicationSession applicationSession;
    private final InMemoryAuthenticationRepository inMemoryAuthenticationRepository;

    public AuthenticationController() {
        this.inMemoryAuthenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    public boolean doLogin(String email, String pwd) {
        try {
            return inMemoryAuthenticationRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public List<UserRoleDTO> getUserRoles() {
        if (inMemoryAuthenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return inMemoryAuthenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    public void doLogout() {
        inMemoryAuthenticationRepository.doLogout();
    }
}