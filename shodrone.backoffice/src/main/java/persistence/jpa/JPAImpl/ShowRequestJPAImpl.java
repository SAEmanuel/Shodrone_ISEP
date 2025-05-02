package persistence.jpa.JPAImpl;

import domain.entity.ShowRequest;
import persistence.jpa.JpaBaseRepository;
import persistence.interfaces.ShowRequestRepository;

import java.util.List;
import java.util.Optional;

public class ShowRequestJPAImpl extends JpaBaseRepository<ShowRequest, Long> implements ShowRequestRepository {

    @Override
    public Optional<ShowRequest> saveInStore(ShowRequest entity) {
        if (entity.identity() != null && findById(entity.identity()) != null) {
            return Optional.empty();
        }

        add(entity);
        return Optional.of(entity);
    }



    @Override
    public List<ShowRequest> getAll() {
        return entityManager().createQuery("SELECT s FROM ShowRequest s", ShowRequest.class).getResultList();
    }

    @Override
    public Optional<ShowRequest> findById(Object id) {
        if (!(id instanceof Long)) return Optional.empty();
        return Optional.ofNullable(entityManager().find(ShowRequest.class, (long) id));
    }
}