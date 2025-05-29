package persistence;

import domain.entity.User;

import java.util.List;

/**
 * Controller responsible for listing users along with their active status.
 */
public class ListUserController {

    /**
     * Returns a formatted list of all users with their status.
     *
     * @return List of strings like "email | name | ACTIVE/INACTIVE"
     */
    public List<String> getAllUsersWithStatus() {
        return RepositoryProvider.userRepository().findAll().stream()
                .map(user -> String.format("%s | %s | %s",
                        user.getId(),
                        user.getName(),
                        user.isActive() ? "ACTIVE" : "INACTIVE"))
                .toList();
    }

    /**
     * Returns all users directly.
     *
     * @return List of User
     */
    public List<User> getAllUsers() {
        return RepositoryProvider.userRepository().findAll();
    }
}
