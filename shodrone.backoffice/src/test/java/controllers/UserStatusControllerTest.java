package controllers;

import controller.user.UserStatusController;
import domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.UserRepository;
import persistence.inmemory.InMemoryAuthenticationRepository;
import domain.entity.Email;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserStatusControllerTest {

    private UserStatusController controller;
    private User mockUser;
    private UserRepository mockUserRepository;
    private InMemoryAuthenticationRepository mockAuthRepo;

    @BeforeEach
    void setUp() {
        controller = new UserStatusController();
        mockUser = mock(User.class);
        mockUserRepository = mock(UserRepository.class);
        mockAuthRepo = mock(InMemoryAuthenticationRepository.class);

        Email mockEmail = new Email("test.user@shodrone.app");
        when(mockUser.getId()).thenReturn(mockEmail);

        RepositoryProvider.injectUserRepository(mockUserRepository);
        RepositoryProvider.injectAuthenticationRepository(mockAuthRepo);
    }


    @Test
    void testActivateUser_Success() {
        when(mockUser.isActive()).thenReturn(false);

        boolean result = controller.activateUser(mockUser);

        assertTrue(result);
        verify(mockUser).activate();
        verify(mockUserRepository).save(mockUser);
        verify(mockAuthRepo).setUserActive(anyString(), eq(true));
    }

    @Test
    void testActivateUser_AlreadyActive() {
        when(mockUser.isActive()).thenReturn(true);

        boolean result = controller.activateUser(mockUser);

        assertFalse(result);
        verify(mockUser, never()).activate();
        verify(mockUserRepository, never()).save(mockUser);
    }

    @Test
    void testDeactivateUser_Success() {
        when(mockUser.isActive()).thenReturn(true);

        boolean result = controller.deactivateUser(mockUser);

        assertTrue(result);
        verify(mockUser).deactivate();
        verify(mockUserRepository).save(mockUser);
        verify(mockAuthRepo).setUserActive(anyString(), eq(false));
    }

    @Test
    void testDeactivateUser_AlreadyInactive() {
        when(mockUser.isActive()).thenReturn(false);

        boolean result = controller.deactivateUser(mockUser);

        assertFalse(result);
        verify(mockUser, never()).deactivate();
        verify(mockUserRepository, never()).save(mockUser);
    }

    @Test
    void testChangeUserStatus_ToActive() {
        when(mockUser.isActive()).thenReturn(false);

        boolean result = controller.changeUserStatus(mockUser, true);

        assertTrue(result);
        verify(mockUser).activate();
        verify(mockUserRepository).save(mockUser);
        verify(mockAuthRepo).setUserActive(anyString(), eq(true));
    }

    @Test
    void testChangeUserStatus_ToInactive() {
        when(mockUser.isActive()).thenReturn(true);

        boolean result = controller.changeUserStatus(mockUser, false);

        assertTrue(result);
        verify(mockUser).deactivate();
        verify(mockUserRepository).save(mockUser);
        verify(mockAuthRepo).setUserActive(anyString(), eq(false));
    }

    @Test
    void testChangeUserStatus_NoChange() {
        when(mockUser.isActive()).thenReturn(true);

        boolean result = controller.changeUserStatus(mockUser, true);

        assertFalse(result);
        verify(mockUser, never()).activate();
        verify(mockUserRepository, never()).save(mockUser);
    }
}
