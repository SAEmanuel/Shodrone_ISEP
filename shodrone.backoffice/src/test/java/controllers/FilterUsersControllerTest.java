package controllers;

import authz.User;
import controller.authz.AuthenticationController;
import controller.user.FilterUsersController;
import controller.user.ListUserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilterUsersControllerTest {

    private ListUserController mockListController;
    private FilterUsersController controller;

    @BeforeEach
    void setUp() {
        mockListController = mock(ListUserController.class);
        RepositoryProvider.injectListUserController(mockListController);
        controller = new FilterUsersController();

    }

    @Test
    void testFilterOnlyActiveUsers() {
        User activeUser = mock(User.class);
        when(activeUser.isActive()).thenReturn(true);
        when(activeUser.hasRole(AuthenticationController.ROLE_ADMIN)).thenReturn(false);

        User inactiveUser = mock(User.class);
        when(inactiveUser.isActive()).thenReturn(false);
        when(inactiveUser.hasRole(AuthenticationController.ROLE_ADMIN)).thenReturn(false);

        when(mockListController.getAllUsers()).thenReturn(List.of(activeUser, inactiveUser));

        List<User> result = controller.getUsersByFilter(0);
        assertEquals(1, result.size());
        assertTrue(result.contains(activeUser));
    }

    @Test
    void testFilterOnlyInactiveUsers() {
        User activeUser = mock(User.class);
        when(activeUser.isActive()).thenReturn(true);
        when(activeUser.hasRole(AuthenticationController.ROLE_ADMIN)).thenReturn(false);

        User inactiveUser = mock(User.class);
        when(inactiveUser.isActive()).thenReturn(false);
        when(inactiveUser.hasRole(AuthenticationController.ROLE_ADMIN)).thenReturn(false);

        when(mockListController.getAllUsers()).thenReturn(List.of(activeUser, inactiveUser));

        List<User> result = controller.getUsersByFilter(1);
        assertEquals(1, result.size());
        assertTrue(result.contains(inactiveUser));
    }

    @Test
    void testFilterAllUsersExceptAdmins() {
        User normalUser = mock(User.class);
        when(normalUser.isActive()).thenReturn(true);
        when(normalUser.hasRole(AuthenticationController.ROLE_ADMIN)).thenReturn(false);

        User adminUser = mock(User.class);
        when(adminUser.hasRole(AuthenticationController.ROLE_ADMIN)).thenReturn(true);

        when(mockListController.getAllUsers()).thenReturn(List.of(normalUser, adminUser));

        List<User> result = controller.getUsersByFilter(99);
        assertEquals(1, result.size());
        assertTrue(result.contains(normalUser));
        assertFalse(result.contains(adminUser));
    }
}
