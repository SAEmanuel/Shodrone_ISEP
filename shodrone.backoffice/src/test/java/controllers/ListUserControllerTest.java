package controllers;

import persistence.ListUserController;
import domain.entity.Email;
import domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListUserControllerTest {

    private ListUserController controller;
    private UserRepository mockUserRepo;

    @BeforeEach
    void setUp() {
        mockUserRepo = mock(UserRepository.class);
        RepositoryProvider.injectUserRepository(mockUserRepo);
        controller = new ListUserController();
    }

    @Test
    void testGetAllUsersWithStatusShowsCorrectFormat() {
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn(new Email("john@shodrone.app"));
        when(user1.getName()).thenReturn("John");
        when(user1.isActive()).thenReturn(true);

        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(new Email("jane@shodrone.app"));
        when(user2.getName()).thenReturn("Jane");
        when(user2.isActive()).thenReturn(false);

        when(mockUserRepo.findAll()).thenReturn(List.of(user1, user2));

        List<String> result = controller.getAllUsersWithStatus();

        assertEquals(2, result.size());
        assertTrue(result.get(0).contains("ACTIVE") || result.get(1).contains("ACTIVE"));
        assertTrue(result.get(0).contains("INACTIVE") || result.get(1).contains("INACTIVE"));
    }

    @Test
    void testGetAllUsersWithStatusWhenNoUsersExist() {
        when(mockUserRepo.findAll()).thenReturn(List.of());

        List<String> result = controller.getAllUsersWithStatus();

        assertTrue(result.isEmpty());
    }
}
