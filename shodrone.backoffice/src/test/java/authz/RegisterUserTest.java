package authz;

import constants.Roles;
import domain.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.UserRepository;
import persistence.inmemory.InMemoryAuthenticationRepository;
import persistence.AuthenticationRepository;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import domain.entity.Email;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterUserTest {

    private AuthenticationRepository authRepo;

    @BeforeEach
    public void setUp() {
        RepositoryProvider.setUseInMemory(true);
        this.authRepo = RepositoryProvider.authenticationRepository();

        if (authRepo instanceof InMemoryAuthenticationRepository inmemoryRepo) {
            inmemoryRepo.reset();
        }

        authRepo.addUserRole(Roles.ROLE_ADMIN, "Administrator");
        authRepo.addUserRole(Roles.ROLE_CRM_MANAGER, "CRM Manager");
        authRepo.addUserRole(Roles.ROLE_CRM_COLLABORATOR, "CRM Collaborator");
        authRepo.addUserRole(Roles.ROLE_SHOW_DESIGNER, "Show Designer");
        authRepo.addUserRole(Roles.ROLE_CUSTOMER_REPRESENTATIVE, "Customer Representative");
        authRepo.addUserRole(Roles.ROLE_DRONE_TECH, "Drone Technician");

        authRepo.addUserWithRole("Administrator x", "admin@shodrone.app", "Admin123!", Roles.ROLE_ADMIN);
        authRepo.addUserWithRole("CRM Manager x", "crm_manager@shodrone.app", "CrmMan456@", Roles.ROLE_CRM_MANAGER);
        authRepo.addUserWithRole("CRM Collaborator x", "crm_collaborator@shodrone.app", "Colab789#", Roles.ROLE_CRM_COLLABORATOR);
        authRepo.addUserWithRole("Show Designer x", "show_designer@shodrone.app", "Show321$", Roles.ROLE_SHOW_DESIGNER);
        authRepo.addUserWithRole("Representative x", "representative@shodrone.app", "Repres654%", Roles.ROLE_CUSTOMER_REPRESENTATIVE);
        authRepo.addUserWithRole("Drone Technician x", "dronetech@shodrone.app", "Drone987^", Roles.ROLE_CUSTOMER_REPRESENTATIVE);
        authRepo.addUserWithRole("DroSDSDn x", "xu@shodrone.app", "XuTech159&", Roles.ROLE_CRM_COLLABORATOR);
        authRepo.addUserWithRole("Drone Technician - Staff", "xv@shodrone.app", "Xv1234!", Roles.ROLE_DRONE_TECH);
    }

    @Test
    public void ensureUserIsCreatedSuccessfully() {
        boolean result = authRepo.addUserWithRole("João Silva", "joao@shodrone.app", "Pass123@", Roles.ROLE_ADMIN);
        assertTrue(result);

        List<UserDTO> users = authRepo.getAllUsers();
        assertEquals(9, users.size());
        UserDTO joao = users.stream()
                .filter(u -> u.getId().equalsIgnoreCase("joao@shodrone.app"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("João Silva not found"));

        assertEquals("joao@shodrone.app", joao.getId());
        assertEquals(Roles.ROLE_ADMIN, joao.getRoles().get(0).getId());
    }

    @Test
    public void ensureCannotCreateUserWithExistingEmail() {
        authRepo.addUserWithRole("Ana Lima", "ana@shodrone.app", "pw", "ADMIN");
        boolean result = authRepo.addUserWithRole("Ana Lima 2", "ana@shodrone.app", "pw2", "ADMIN");
        assertFalse(result);
    }

    @Test
    public void ensureUserRoleIsAssignedCorrectly() {
        authRepo.addUserWithRole("Carlos", "carlos@shodrone.app", "Carlos123!", Roles.ROLE_ADMIN);

        List<UserDTO> users = authRepo.getAllUsers();

        UserDTO user = users.stream()
                .filter(u -> u.getId().equalsIgnoreCase("carlos@shodrone.app"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("User not found"));

        assertEquals(Roles.ROLE_ADMIN, user.getRoles().get(0).getId());
    }



    @Test
    public void ensureUserIsActiveByDefault() {
        authRepo.addUserWithRole("Rita", "rita@shodrone.app", "Xyz123$", Roles.ROLE_ADMIN);

        UserRepository userRepo = RepositoryProvider.userRepository();
        userRepo.ofIdentity(new Email("rita@shodrone.app"))
                .ifPresentOrElse(
                        user -> assertTrue(user.isActive(), "User should be active by default"),
                        () -> fail("User not found")
                );
    }

    @Test
    public void ensureUserOnlyHasOneRole() {
        authRepo.addUserWithRole("Maria", "maria@shodrone.app", "Mariapass123!", Roles.ROLE_ADMIN);

        UserRepository userRepo = RepositoryProvider.userRepository();
        userRepo.ofIdentity(new Email("maria@shodrone.app")).ifPresent(user -> {
            user.addRole(new UserRole(Roles.ROLE_CRM_MANAGER, Roles.ROLE_CRM_MANAGER));
            userRepo.save(user);
        });

        UserDTO maria = authRepo.getAllUsers().stream()
                .filter(u -> u.getId().equalsIgnoreCase("maria@shodrone.app"))
                .findFirst()
                .orElseThrow();

        assertEquals(1, maria.getRoles().size());
    }

    @Test
    public void ensureUserIsStoredCorrectlyInRepository() {
        authRepo.addUserWithRole("Pedro", "pedro@shodrone.app", "Pedro321@", Roles.ROLE_DRONE_TECH);

        UserRepository userRepo = RepositoryProvider.userRepository();
        boolean exists = userRepo.ofIdentity(new Email("pedro@shodrone.app")).isPresent();

        assertTrue(exists, "User should be stored in user repository");
    }

}
