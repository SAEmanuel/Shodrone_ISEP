package controller.user;

import java.util.List;
import authz.Email;
import authz.User;
import persistence.RepositoryProvider;
import persistence.inmemory.InMemoryAuthenticationRepository;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;

import java.util.Optional;

public class ManageUserController {

    public List<UserDTO> getAllUsers() {
        return RepositoryProvider.authenticationRepository().getAllUsers();
    }

    public boolean isUserActive(UserDTO dto) {
        Optional<User> user = RepositoryProvider.userRepository().ofIdentity(new Email(dto.getId()));
        return user.map(User::isActive).orElse(false);
    }

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
