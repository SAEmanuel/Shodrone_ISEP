package controller.user;

import authz.Email;
import authz.User;
import persistence.RepositoryProvider;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller responsible for listing users along with their active status.
 * Combines user DTOs from the authentication repository with domain data
 * to display whether each user is active or inactive.
 */
public class ListUserController {

    /**
     * Retrieves all users and returns formatted strings containing their ID, name, and active status.
     *
     * @return A list of user information strings, each formatted as "email | name | ACTIVE/INACTIVE".
     */
    public List<String> getAllUsersWithStatus() {
        List<UserDTO> allUsers = RepositoryProvider.authenticationRepository().getAllUsers();

        return allUsers.stream()
                .map(user -> {
                    Optional<User> domainUser = RepositoryProvider.userRepository().ofIdentity(new Email(user.getId()));
                    boolean active = domainUser.map(User::isActive).orElse(false);
                    return String.format("%s | %s | %s",
                            user.getId(),
                            user.getName(),
                            active ? "ACTIVE" : "INACTIVE");
                })
                .collect(Collectors.toList());
    }
}
