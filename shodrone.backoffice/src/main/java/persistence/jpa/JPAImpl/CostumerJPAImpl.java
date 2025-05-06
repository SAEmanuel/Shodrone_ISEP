package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.valueObjects.NIF;
import jakarta.persistence.TypedQuery;
import persistence.interfaces.CostumerRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the CostumerRepository interface using the JPA persistence mechanism.
 *
 * <p>This class provides the necessary CRUD operations for managing Costumer entities in a database using JPA.</p>
 *
 * @see CostumerRepository
 * @see JpaBaseRepository
 */
public class CostumerJPAImpl extends JpaBaseRepository<Costumer, Long> implements CostumerRepository {

    /**
     * Finds a Costumer entity by its unique system ID.
     *
     * @param id the ID of the Costumer to find
     * @return an Optional containing the found Costumer or an empty Optional if not found
     */
    @Override
    public Optional<Costumer> findByID(Object id) {
        if (!(id instanceof Long)) return Optional.empty();
        return Optional.ofNullable(entityManager().find(Costumer.class, id));
    }

    /**
     * Finds a Costumer entity by its NIF (tax identification number).
     *
     * @param nif the NIF of the Costumer to find
     * @return an Optional containing the found Costumer or an empty Optional if not found
     */
    @Override
    public Optional<Costumer> findByNIF(NIF nif) {
        TypedQuery<Costumer> query = entityManager().createQuery(
                "SELECT c FROM Costumer c WHERE c.nif = :nif", Costumer.class);
        query.setParameter("nif", nif);
        List<Costumer> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    /**
     * Retrieves all Costumer entities from the database.
     *
     * @return an Optional containing a list of all Costumer entities or an empty Optional if none exist
     */
    @Override
    public Optional<List<Costumer>> getAllCostumers() {
        TypedQuery<Costumer> query = entityManager().createQuery(
                "SELECT c FROM Costumer c", Costumer.class);
        List<Costumer> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    /**
     * Saves a Costumer entity in the database, associating it with a given NIF.
     *
     * <p>If a Costumer with the same NIF already exists, the entity will not be saved.</p>
     *
     * @param entity the Costumer entity to save
     * @param costumerNIF the NIF associated with the Costumer
     * @return an Optional containing the saved entity, or an empty Optional if the NIF already exists
     */
    @Override
    public Optional<Costumer> saveInStore(Costumer entity, NIF costumerNIF) {
        Optional<Costumer> existing = findByNIF(costumerNIF);
        if (existing.isEmpty()) {
            add(entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}
