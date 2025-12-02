package controller.user;

import constants.Roles;
import domain.entity.User;
import persistence.RepositoryProvider;

import java.util.List;

/**
 * Controller responsible for filtering users based on their active status.
 */
public class FilterUsersController {
    public List<User> getUsersByFilter(int filterIndex) {
        return RepositoryProvider.userRepository().findAll()
                .stream()
                .filter(user -> {
                    if (user.hasRole(Roles.ROLE_ADMIN)) return false;
                    boolean active = user.isActive();
                    return switch (filterIndex) {
                        case 0 -> active;
                        case 1 -> !active;
                        default -> true;
                    };
                })
                .toList();
    }
}

