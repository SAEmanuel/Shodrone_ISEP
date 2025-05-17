package controllers;

import authz.User;
import controller.user.CheckUserStatusController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CheckUserStatusControllerTest {

    private CheckUserStatusController controller;

    @BeforeEach
    void setUp() {
        controller = new CheckUserStatusController();
    }

    @Test
    void testUserIsActiveReturnsTrue() {
        User mockUser = mock(User.class);
        when(mockUser.isActive()).thenReturn(true);

        boolean result = controller.isUserActive(mockUser);

        assertTrue(result, "Expected user to be active");
    }

    @Test
    void testUserIsInactiveReturnsFalse() {
        User mockUser = mock(User.class);
        when(mockUser.isActive()).thenReturn(false);

        boolean result = controller.isUserActive(mockUser);

        assertFalse(result, "Expected user to be inactive");
    }
}
