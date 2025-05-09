package persistence.jpa.JPAImpl;

import authz.*;
import persistence.jpa.JpaBaseRepository;

import java.util.Optional;

public class UserRepositoryJPAImpl extends JpaBaseRepository<User, Email> implements UserRepository {

    @Override
    public Optional<User> ofIdentity(Email id) {
        return Optional.ofNullable(findById(id));
    }

    @Override
    public void save(User user) {
        add(user);
    }
}
