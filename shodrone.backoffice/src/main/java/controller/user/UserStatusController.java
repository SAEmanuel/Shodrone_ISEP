package controller.user;

import authz.User;
import persistence.RepositoryProvider;
import persistence.inmemory.InMemoryAuthenticationRepository;

/**
 * Controller responsible for activating or deactivating users.
 */
public class UserStatusController {

    public boolean activateUser(User user) {
        if (user.isActive()) return false;

        user.activate();
        RepositoryProvider.userRepository().save(user);
        updateInMemoryStatus(user, true);
        return true;
    }

    public boolean deactivateUser(User user) {
        if (!user.isActive()) return false;

        user.deactivate();
        RepositoryProvider.userRepository().save(user);
        updateInMemoryStatus(user, false);
        return true;
    }

    public boolean changeUserStatus(User user, boolean activate) {
        boolean isAlreadyInState = user.isActive() == activate;

        if (isAlreadyInState) return false;

        if (activate) {
            activateUser(user);
        } else {
            deactivateUser(user);
        }

        //RepositoryProvider.userRepository().save(user);

        return true;
    }

    private void updateInMemoryStatus(User user, boolean active) {
        if (RepositoryProvider.authenticationRepository() instanceof InMemoryAuthenticationRepository inMemoryRepo) {
            inMemoryRepo.setUserActive(user.getId().toString(), active);
        }
    }
}
