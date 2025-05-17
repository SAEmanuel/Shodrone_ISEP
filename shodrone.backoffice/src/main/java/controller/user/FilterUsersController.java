package controller.user;

import authz.User;
import controller.authz.AuthenticationController;
import persistence.RepositoryProvider;

import java.util.List;

/**
 * Controller responsible for filtering users based on their active status.
 */
public class FilterUsersController {

    private final ListUserController listController = RepositoryProvider.listUserController();

    public List<User> getUsersByFilter(int filterIndex) {
        return listController.getAllUsers().stream()
                .filter(user -> {
                    if (user.hasRole(AuthenticationController.ROLE_ADMIN)) return false;
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
