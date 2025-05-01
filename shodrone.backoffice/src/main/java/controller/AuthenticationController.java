package controller;

import persistence.RepositoryProvider;
import persistence.interfaces.AuthenticationRepository;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

public class AuthenticationController {

    public static final String ROLE_ADMIN = "ADMINISTRATOR";
    public static final String ROLE_CRM_MANAGER = "CRM MANAGER";
    public static final String ROLE_CRM_COLLABORATOR = "CRM COLLABORATOR";
    public static final String ROLE_SHOW_DESIGNER = "SHOW DESIGNER";
    public static final String ROLE_CUSTOMER_REPRESENTATIVE = "CUSTOMER REPRESENTATIVE";
    public static final String ROLE_DRONE_TECH = "DRONE TECHNICIAN";

    private final AuthenticationRepository authRepository;

    public AuthenticationController() {
        authRepository = RepositoryProvider.authenticationRepository();
    }

    public boolean doLogin(String email, String pwd) {
        try {
            return authRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public List<UserRoleDTO> getUserRoles() {
        if (authRepository.isLoggedIn()) {
            return authRepository.getUserRoles();
        }
        return null;
    }

    public void doLogout() {
        authRepository.doLogout();
    }
}
