package controllers;

import authz.*;
import controller.AuthenticationController;
import controller.ListUserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.interfaces.AuthenticationRepository;
import persistence.RepositoryProvider;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListUserControllerTest {

    private ListUserController controller;
    private AuthenticationRepository mockAuthRepo;

    @BeforeEach
    void setUp() {
        // Cria um mock que implementa tanto AuthenticationRepository como UserRepository
        mockAuthRepo = mock(AuthenticationRepository.class, withSettings().extraInterfaces(UserRepository.class));
        RepositoryProvider.injectAuthenticationRepository(mockAuthRepo);
        controller = new ListUserController();
    }

    @Test
    void testGetAllUsersWithStatusShowsCorrectFormat() {
        UserDTO user1 = new UserDTO("john@shodrone.app", "John", List.of(new UserRoleDTO("1", AuthenticationController.ROLE_ADMIN)));
        UserDTO user2 = new UserDTO("jane@shodrone.app", "Jane", List.of(new UserRoleDTO("2", AuthenticationController.ROLE_CRM_MANAGER)));

        User domainUser1 = mock(User.class);
        when(domainUser1.isActive()).thenReturn(true);

        User domainUser2 = mock(User.class);
        when(domainUser2.isActive()).thenReturn(false);

        when(mockAuthRepo.getAllUsers()).thenReturn(List.of(user1, user2));

        // Faz cast para UserRepository pois é suportado via extraInterfaces
        UserRepository mockUserRepo = (UserRepository) mockAuthRepo;
        when(mockUserRepo.ofIdentity(new Email("john@shodrone.app"))).thenReturn(Optional.of(domainUser1));
        when(mockUserRepo.ofIdentity(new Email("jane@shodrone.app"))).thenReturn(Optional.of(domainUser2));

        List<String> result = controller.getAllUsersWithStatus();

        assertEquals(2, result.size());
        assertTrue(result.get(0).contains("ACTIVE"));
        assertTrue(result.get(1).contains("INACTIVE"));
    }

    @Test
    void testGetAllUsersWithStatusWhenUserNotFound() {
        UserDTO user = new UserDTO("ghost@shodrone.app", "Ghost", List.of(new UserRoleDTO("1", AuthenticationController.ROLE_ADMIN)));

        when(mockAuthRepo.getAllUsers()).thenReturn(List.of(user));

        UserRepository mockUserRepo = (UserRepository) mockAuthRepo;
        when(mockUserRepo.ofIdentity(new Email("ghost@shodrone.app"))).thenReturn(Optional.empty());

        List<String> result = controller.getAllUsersWithStatus();

        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("INACTIVE"));
    }

    @Test
    void testGetAllUsersWithStatusWhenNoUsersExist() {
        when(mockAuthRepo.getAllUsers()).thenReturn(List.of());

        List<String> result = controller.getAllUsersWithStatus();

        assertTrue(result.isEmpty());
    }
}
