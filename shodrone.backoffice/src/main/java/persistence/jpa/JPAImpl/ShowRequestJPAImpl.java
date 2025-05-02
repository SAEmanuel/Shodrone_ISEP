package persistence.jpa.JPAImpl;

import domain.entity.ShowRequest;
import jakarta.persistence.TypedQuery;
import persistence.jpa.JpaBaseRepository;
import persistence.interfaces.ShowRequestRepository;

import java.util.List;
import java.util.Optional;

public class ShowRequestJPAImpl extends JpaBaseRepository<ShowRequest, Long> implements ShowRequestRepository {

    @Override
    public Optional<ShowRequest> saveInStore(ShowRequest entity) {
        // Verifica se já existe um pedido igual (podes personalizar os critérios)
        TypedQuery<ShowRequest> query = entityManager().createQuery(
                "SELECT s FROM ShowRequest s WHERE s.submissionDate = :date AND s.submissionAuthor = :author", ShowRequest.class);
        query.setParameter("date", entity.getSubmissionDate());
        query.setParameter("author", entity.getSubmissionAuthor());

        List<ShowRequest> result = query.getResultList();

        if (result.isEmpty()) {
            entityManager().persist(entity);
            return Optional.of(entity);
        }

        return Optional.empty();
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