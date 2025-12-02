package persistence;

import domain.entity.Email;
import domain.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * Provides basic data access methods to retrieve, save, and list users.
 * This abstraction allows for flexible implementations using different storage backends.
 */
public interface UserRepository {

    /**
     * Retrieves a user by their unique identity (email).
     *
     * @param id The email used to identify the user.
     * @return An {@link Optional} containing the user if found, or empty if not.
     */
    Optional<User> ofIdentity(Email id);

    /**
     * Saves or updates the given user entity in the repository.
     * If the user already exists, their information is updated.
     *
     * @param user The user to save.
     */
    void save(User user);

    /**
     * Retrieves all users stored in the repository.
     *
     * @return A list of all users.
     */
    List<User> findAll();
}
