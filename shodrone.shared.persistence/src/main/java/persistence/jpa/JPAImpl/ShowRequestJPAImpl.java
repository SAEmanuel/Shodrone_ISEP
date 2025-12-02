package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.entity.ShowRequest;
import persistence.jpa.JpaBaseRepository;
import persistence.ShowRequestRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the ShowRequestRepository interface using the JPA persistence mechanism.
 *
 * <p>This class provides the necessary CRUD operations for managing ShowRequest entities in a database using JPA.</p>
 *
 * @see ShowRequestRepository
 * @see JpaBaseRepository
 */
public class ShowRequestJPAImpl extends JpaBaseRepository<ShowRequest, Long> implements ShowRequestRepository {

    /**
     * Saves a ShowRequest entity in the database if it doesn't already exist.
     *
     * <p>If the entity has an identity (ID) and the ID already exists in the database, it won't be saved again.</p>
     *
     * @param entity the ShowRequest entity to save
     * @return an Optional containing the saved entity or an empty Optional if it already exists
     */
    @Override
    public Optional<ShowRequest> saveInStore(ShowRequest entity) {
        if (entity.identity() != null && findById(entity.identity()) != null) {
            return Optional.empty();
        }

        if (entity.identity() != null) {
            update(entity);
        } else {
            add(entity);
        }

        return Optional.of(entity);
    }


    /**
     * Retrieves all ShowRequest entities from the database.
     *
     * @return a List of all ShowRequest entities
     */
    @Override
    public List<ShowRequest> getAll() {
        return entityManager().createQuery("SELECT s FROM ShowRequest s", ShowRequest.class).getResultList();
    }

    /**
     * Finds a ShowRequest entity by its unique ID.
     *
     * @param id the ID of the ShowRequest to find
     * @return an Optional containing the found ShowRequest or an empty Optional if not found
     */
    @Override
    public Optional<ShowRequest> findById(Object id) {
        if (!(id instanceof Long)) return Optional.empty();
        return Optional.ofNullable(entityManager().find(ShowRequest.class, (long) id));
    }

    /**
     * Finds ShowRequest entities associated with a specific Costumer.
     *
     * @param costumer the Costumer whose ShowRequests are to be retrieved
     * @return an Optional containing a List of ShowRequests for the given Costumer or an empty Optional if none found
     */
    @Override
    public Optional<List<ShowRequest>> findByCostumer(Costumer costumer) {
        if (costumer == null) return Optional.empty();

        List<ShowRequest> requests = entityManager()
                .createQuery("SELECT s FROM ShowRequest s WHERE s.costumer = :costumer", ShowRequest.class)
                .setParameter("costumer", costumer)
                .getResultList();

        return requests.isEmpty() ? Optional.empty() : Optional.of(requests);
    }

    /**
     * Updates an existing ShowRequest entity in the database.
     *
     * @param entity the ShowRequest entity to update
     * @return an Optional containing the updated ShowRequest or an empty Optional if the entity does not exist
     */
    @Override
    public Optional<ShowRequest> updateShowRequest(ShowRequest entity) {
        if (entity == null || entity.identity() == null) {
            return Optional.empty();
        }

        Optional<ShowRequest> existing = Optional.ofNullable(findById(entity.identity()));
        if (existing.isEmpty()) {
            return Optional.empty();
        }

        ShowRequest updated = update(entity);
        return Optional.ofNullable(updated);
    }

}