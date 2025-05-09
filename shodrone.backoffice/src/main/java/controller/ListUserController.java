package controller;

import authz.Email;
import authz.User;
import persistence.RepositoryProvider;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListUserController {

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
