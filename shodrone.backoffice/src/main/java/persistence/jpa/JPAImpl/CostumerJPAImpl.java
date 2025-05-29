package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.valueObjects.NIF;
import jakarta.persistence.TypedQuery;
import persistence.CostumerRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the {@link CostumerRepository} interface.
 * Provides methods to persist, retrieve, and validate {@link Costumer} entities using JPA.
 * This implementation is configured for the "shodrone_backoffice" persistence unit.
 */
public class CostumerJPAImpl extends JpaAutoTxRepository<Costumer, Long, Long> implements CostumerRepository {

    /**
     * Constructs a new {@code CostumerJPAImpl} using the "shodrone_backoffice" persistence unit
     * and "customerSystemID" as the identity field.
     */
    public CostumerJPAImpl() {
        super("shodrone_backoffice", "customerSystemID");
    }

    /**
     * Retrieves a customer by their system ID.
     *
     * @param id The system ID to search for.
     * @return An {@link Optional} containing the customer if found.
     */
    @Override
    public Optional<Costumer> findByID(Object id) {
        if (!(id instanceof Long)) return Optional.empty();
        return Optional.ofNullable(entityManager().find(Costumer.class, id));
    }

    /**
     * Finds a customer by their NIF (tax ID).
     *
     * @param nif The customer's NIF.
     * @return An {@link Optional} containing the customer if found.
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
     * Retrieves all customers stored in the system.
     *
     * @return An {@link Optional} containing a list of all customers, or empty if none exist.
     */
    @Override
    public Optional<List<Costumer>> getAllCostumers() {
        TypedQuery<Costumer> query = entityManager().createQuery(
                "SELECT c FROM Costumer c", Costumer.class);
        List<Costumer> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    /**
     * Saves a customer to the database only if no other customer with the same NIF exists.
     *
     * @param entity       The customer to save.
     * @param costumerNIF  The NIF to check for duplicates.
     * @return An {@link Optional} containing the saved customer, or empty if a duplicate is found or an error occurs.
     */
    @Override
    public Optional<Costumer> saveInStore(Costumer entity, NIF costumerNIF) {
        Optional<Costumer> existing = findByNIF(costumerNIF);
        if (existing.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(super.save(entity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
