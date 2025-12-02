package controllers;

import constants.Roles;
import controller.user.FilterUsersController;
import domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilterUsersControllerTest {

    private UserRepository mockUserRepository;
    private FilterUsersController controller;

    @BeforeEach
    void setUp() {
        mockUserRepository = mock(UserRepository.class);
        RepositoryProvider.injectUserRepository(mockUserRepository);
        controller = new FilterUsersController();
    }

    @Test
    void testFilterOnlyActiveUsers() {
        User activeUser = mock(User.class);
        when(activeUser.isActive()).thenReturn(true);
        when(activeUser.hasRole(Roles.ROLE_ADMIN)).thenReturn(false);

        User inactiveUser = mock(User.class);
        when(inactiveUser.isActive()).thenReturn(false);
        when(inactiveUser.hasRole(Roles.ROLE_ADMIN)).thenReturn(false);

        when(mockUserRepository.findAll()).thenReturn(List.of(activeUser, inactiveUser));

        List<User> result = controller.getUsersByFilter(0);

        assertEquals(1, result.size());
        assertTrue(result.contains(activeUser));
    }

    @Test
    void testFilterOnlyInactiveUsers() {
        User activeUser = mock(User.class);
        when(activeUser.isActive()).thenReturn(true);
        when(activeUser.hasRole(Roles.ROLE_ADMIN)).thenReturn(false);

        User inactiveUser = mock(User.class);
        when(inactiveUser.isActive()).thenReturn(false);
        when(inactiveUser.hasRole(Roles.ROLE_ADMIN)).thenReturn(false);

        when(mockUserRepository.findAll()).thenReturn(List.of(activeUser, inactiveUser));

        List<User> result = controller.getUsersByFilter(1);

        assertEquals(1, result.size());
        assertTrue(result.contains(inactiveUser));
    }

    @Test
    void testFilterAllUsersExceptAdmins() {
        User normalUser = mock(User.class);
        when(normalUser.isActive()).thenReturn(true);
        when(normalUser.hasRole(Roles.ROLE_ADMIN)).thenReturn(false);

        User adminUser = mock(User.class);
        when(adminUser.hasRole(Roles.ROLE_ADMIN)).thenReturn(true);

        when(mockUserRepository.findAll()).thenReturn(List.of(normalUser, adminUser));

        List<User> result = controller.getUsersByFilter(99);

        assertEquals(1, result.size());
        assertTrue(result.contains(normalUser));
        assertFalse(result.contains(adminUser));
    }
}
