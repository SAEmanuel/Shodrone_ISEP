package authz;

import controller.AuthenticationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.AuthenticationRepository;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTestJpa {

    AuthenticationRepository repo;

    @BeforeEach
    void setUp() {
        RepositoryProvider.setUseInMemory(false);
        this.repo = RepositoryProvider.authenticationRepository();

        repo.addUserRole(AuthenticationController.ROLE_ADMIN, "Administrator");
        repo.addUserRole(AuthenticationController.ROLE_CRM_MANAGER, "CRM Manager");
        repo.addUserRole(AuthenticationController.ROLE_CRM_COLLABORATOR, "CRM Collaborator");
        repo.addUserRole(AuthenticationController.ROLE_SHOW_DESIGNER, "Show Designer");
        repo.addUserRole(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, "Customer Representative");
        repo.addUserRole(AuthenticationController.ROLE_DRONE_TECH, "Drone Technician");

        repo.addUserWithRole("Administrator x", "admin@shodrone.app", "Admin123!", AuthenticationController.ROLE_ADMIN);
        repo.addUserWithRole("CRM Manager x", "crm_manager@shodrone.app", "CrmMan456@", AuthenticationController.ROLE_CRM_MANAGER);
        repo.addUserWithRole("CRM Collaborator x", "crm_collaborator@shodrone.app", "Colab789#", AuthenticationController.ROLE_CRM_COLLABORATOR);
        repo.addUserWithRole("Show Designer x", "show_designer@shodrone.app", "Show321$", AuthenticationController.ROLE_SHOW_DESIGNER);
        repo.addUserWithRole("Representative x", "representative@shodrone.app", "Repres654%", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("Drone Technician x", "dronetech@shodrone.app", "Drone987^", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("DroSDSDn x", "xu@shodrone.app", "XuTech159&", AuthenticationController.ROLE_CRM_COLLABORATOR);
        repo.addUserWithRole("Drone Technician - Staff", "xv@shodrone.app", "Xv1234!", AuthenticationController.ROLE_DRONE_TECH);
    }


    @Test
    void testLoginSuccess() {
        boolean result = repo.doLogin("admin@shodrone.app", "Admin123!");
        assertTrue(result, "Login should succeed for correct credentials");
    }

    @Test
    void testLoginFailsWithWrongPassword() {
        boolean result = repo.doLogin("admin@shodrone.app", "WrongPassword");
        assertFalse(result, "Login should fail for incorrect password");
    }

    @Test
    void testLoginFailsForUnknownUser() {
        boolean result = repo.doLogin("unknown@shodrone.app", "somepass");
        assertFalse(result, "Login should fail for non-existent user");
    }

    @Test
    void testIsLoggedInAfterLogin() {
        repo.doLogin("admin@shodrone.app", "Admin123!");
        assertTrue(repo.isLoggedIn(), "isLoggedIn should return true after successful login");
    }

    @Test
    void testLogout() {
        repo.doLogin("admin@shodrone.app", "Admin123!");
        repo.doLogout();
        assertFalse(repo.isLoggedIn(), "isLoggedIn should return false after logout");
    }


    @Test
    void testCannotCreateUserWithDuplicateEmail() {
        repo.addUserWithRole("Test User", "dup@shodrone.app", "Dup123#", AuthenticationController.ROLE_ADMIN);
        boolean result = repo.addUserWithRole("Test User 2", "dup@shodrone.app", "OtherPass1!", AuthenticationController.ROLE_ADMIN);
        assertFalse(result, "Should not allow creating a user with duplicate email");
    }

    @Test
    void testGetUserRolesAfterLogin() {
        repo.doLogin("admin@shodrone.app", "Admin123!");
        var roles = repo.getUserRoles();
        assertNotNull(roles);
        assertTrue(roles.stream().anyMatch(r -> r.getId().equals(AuthenticationController.ROLE_ADMIN)));
    }

}
