package persistence.inmemory;

import authz.*;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {

    private final Map<Email, User> store = new HashMap<>();

    @Override
    public Optional<User> ofIdentity(Email id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(User user) {
        store.put(user.getId(), user);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
