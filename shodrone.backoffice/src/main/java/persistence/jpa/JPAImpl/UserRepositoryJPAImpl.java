package persistence.jpa.JPAImpl;

import authz.*;
import persistence.jpa.JpaBaseRepository;

import java.util.Optional;

/**
 * JPA implementation of the {@link UserRepository} interface.
 * Manages persistence of {@link User} entities using their {@link Email} as the primary key.
 * Inherits basic CRUD operations from {@link JpaBaseRepository}.
 */
public class UserRepositoryJPAImpl extends JpaBaseRepository<User, Email> implements UserRepository {

    /**
     * Retrieves a user by their email identity.
     *
     * @param id The email identifying the user.
     * @return An {@link Optional} containing the user if found, or empty otherwise.
     */
    @Override
    public Optional<User> ofIdentity(Email id) {
        return Optional.ofNullable(findById(id));
    }

    /**
     * Saves a new user or updates an existing one in the database.
     *
     * @param user The user to save.
     */
    @Override
    public void save(User user) {
        update(user);
    }
}
