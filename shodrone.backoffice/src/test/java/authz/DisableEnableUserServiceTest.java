package authz;

import controller.authz.AuthenticationController;
import controller.user.ManageUserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.inmemory.InMemoryAuthenticationRepository;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DisableEnableUserServiceTest {

    private ManageUserController controller;
    private User user;
    private Email email;
    private Password password;

    @BeforeEach
    void setUp() {
        controller = new ManageUserController();

        email = new Email("user@shodrone.app");
        password = new Password("Pw123!");
        user = new User(email, password, "User");

        RepositoryProvider.setUseInMemory(true);

        RepositoryProvider.userRepository().save(user);

        RepositoryProvider.authenticationRepository()
                .addUserWithRole("User", email.toString(), password.toString(), AuthenticationController.ROLE_CRM_MANAGER);

        if (RepositoryProvider.authenticationRepository() instanceof InMemoryAuthenticationRepository inMemoryRepo) {
            inMemoryRepo.setUserActive(email.toString(), true);
        }
    }

    @Test
    void testNewUserIsActive() {
        UserDTO dto = new UserDTO(email.toString(), "User", new ArrayList<>());

        assertTrue(controller.isUserActive(dto), "New User is active");
    }

    @Test
    void testDeactivateUser() {
        UserDTO dto = new UserDTO(email.toString(), "User", new ArrayList<>());

        controller.deactivateUser(dto);

        assertFalse(controller.isUserActive(dto), "Inactive User");
    }

    @Test
    void testReactivateUser() {
        UserDTO dto = new UserDTO(email.toString(), "User", new ArrayList<>());

        controller.deactivateUser(dto);
        assertFalse(controller.isUserActive(dto), "Inactive User");

        controller.activateUser(dto);
        assertTrue(controller.isUserActive(dto), "User is active");
    }
}
