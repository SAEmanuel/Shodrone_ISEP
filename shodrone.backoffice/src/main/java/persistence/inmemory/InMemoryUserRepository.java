package persistence.inmemory;

import domain.entity.Email;
import domain.entity.User;
import persistence.UserRepository;

import java.util.*;

/**
 * In-memory implementation of the {@link UserRepository} interface.
 * Stores and manages {@link User} entities in a local hash map, keyed by email.
 * Suitable for development or testing without persistent storage.
 */
public class InMemoryUserRepository implements UserRepository {

    /** Internal map to store users by their unique email. */
    private final Map<Email, User> store = new HashMap<>();

    /**
     * Retrieves a user by their unique email identifier.
     *
     * @param id The email used to identify the user.
     * @return An {@link Optional} containing the user if found, or empty otherwise.
     */
    @Override
    public Optional<User> ofIdentity(Email id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Saves or updates a user in the in-memory store.
     *
     * @param user The user to save.
     */
    @Override
    public void save(User user) {
        store.put(user.getId(), user);
    }

    /**
     * Retrieves all users currently stored in memory.
     *
     * @return A list of all users.
     */
    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
