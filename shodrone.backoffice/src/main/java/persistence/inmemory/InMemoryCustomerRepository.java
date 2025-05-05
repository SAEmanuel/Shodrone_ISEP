package persistence.inmemory;

import domain.entity.Costumer;
import domain.valueObjects.NIF;
import persistence.interfaces.CostumerRepository;
import utils.Validations;

import java.util.*;

/**
 * In-memory implementation of the CostumerRepository interface.
 *
 * <p>This repository manages Costumer entities in memory, using a HashMap with NIF as the key. It supports basic
 * CRUD operations including saving, finding by ID or NIF, and retrieving all customers.</p>
 *
 * @see CostumerRepository
 */
public class InMemoryCustomerRepository implements CostumerRepository {
    private final Map<NIF, Costumer> store;
    private static Long LAST_COSTUMER_ID = 0L;

    /**
     * Constructor for initializing the in-memory customer store.
     */
    public InMemoryCustomerRepository() {
        this.store = new HashMap<>();
    }

    /**
     * Finds a Costumer entity by its unique system ID.
     *
     * @param id the ID of the Costumer to find
     * @return an Optional containing the found Costumer or an empty Optional if not found
     */
    @Override
    public Optional<Costumer> findByID(Object id) {
        if (!(id instanceof Long)) return Optional.empty();
        Long customerId = (Long) id;
        return store.values().stream()
                .filter(c -> c.identity().equals(customerId))
                .findFirst();
    }

    /**
     * Finds a Costumer entity by its NIF (tax identification number).
     *
     * @param nif the NIF of the Costumer to find
     * @return an Optional containing the found Costumer or an empty Optional if not found
     */
    @Override
    public Optional<Costumer> findByNIF(NIF nif) {
        return Optional.ofNullable(store.get(nif));
    }

    /**
     * Retrieves all Costumer entities from the in-memory store.
     *
     * @return an Optional containing a list of all Costumer entities, or an empty Optional if none exist
     */
    @Override
    public Optional<List<Costumer>> getAllCostumers() {
        if (store.isEmpty()) return Optional.empty();
        return Optional.of(new ArrayList<>(store.values()));
    }

    /**
     * Saves a Costumer entity in the in-memory store.
     *
     * <p>If the provided NIF is not already associated with an existing customer, the entity is saved and
     * a new customer system ID is assigned. If the NIF already exists in the store, no action is taken.</p>
     *
     * @param entity the Costumer entity to save
     * @param costumerNIF the NIF associated with the Costumer
     * @return an Optional containing the saved entity or an empty Optional if the NIF already exists
     */
    @Override
    public Optional<Costumer> saveInStore(Costumer entity, NIF costumerNIF) {
        if (!Validations.containsKey(this.store, costumerNIF)) {
            entity.setCustomerSystemID(LAST_COSTUMER_ID++);
            store.put(costumerNIF, entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}
