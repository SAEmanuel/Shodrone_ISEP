package authz;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> ofIdentity(Email id);

    void save(User user);

    List<User> findAll();
}
