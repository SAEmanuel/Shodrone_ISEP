package controller.user;

import java.util.List;
import authz.Email;
import authz.User;
import persistence.RepositoryProvider;
import persistence.inmemory.InMemoryAuthenticationRepository;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;

import java.util.Optional;

/**
 * Controller responsible for managing user accounts.
 * Provides operations to retrieve all users, check their activation status,
 * and activate or deactivate accounts.
 */
public class ManageUserController {

    /**
     * Retrieves a list of all users in the system.
     *
     * @return A list of {@link UserDTO} representing all registered users.
     */
    public List<UserDTO> getAllUsers() {
        return RepositoryProvider.authenticationRepository().getAllUsers();
    }

    /**
     * Checks whether a specific user is currently active.
     *
     * @param dto The user to check.
     * @return True if the user is active, false if inactive or not found.
     */
    public boolean isUserActive(UserDTO dto) {
        Optional<User> user = RepositoryProvider.userRepository().ofIdentity(new Email(dto.getId()));
        return user.map(User::isActive).orElse(false);
    }

    /**
     * Activates the given user and updates their status in both
     * the domain repository and (if applicable) the in-memory auth store.
     *
     * @param dto The user to activate.
     */
    public void activateUser(UserDTO dto) {
        Email email = new Email(dto.getId());

        RepositoryProvider.userRepository().ofIdentity(email).ifPresent(user -> {
            user.activate();
            RepositoryProvider.userRepository().save(user);

            if (RepositoryProvider.authenticationRepository() instanceof InMemoryAuthenticationRepository inMemoryRepo) {
                inMemoryRepo.setUserActive(email.toString(), true);
            }
        });
    }

    /**
     * Deactivates the given user and updates their status in both
     * the domain repository and (if applicable) the in-memory auth store.
     *
     * @param dto The user to deactivate.
     */
    public void deactivateUser(UserDTO dto) {
        Email email = new Email(dto.getId());

        RepositoryProvider.userRepository().ofIdentity(email).ifPresent(user -> {
            user.deactivate();
            RepositoryProvider.userRepository().save(user);

            if (RepositoryProvider.authenticationRepository() instanceof InMemoryAuthenticationRepository inMemoryRepo) {
                inMemoryRepo.setUserActive(email.toString(), false);
            }
        });
    }
}
