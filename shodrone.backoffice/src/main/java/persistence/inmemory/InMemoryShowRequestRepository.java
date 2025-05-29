package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.ShowRequest;
import persistence.ShowRequestRepository;

import java.util.*;

/**
 * In-memory implementation of the ShowRequestRepository interface.
 *
 * <p>This repository manages ShowRequest entities in memory, using a HashMap as a data store. It supports
 * basic CRUD operations including saving, updating, retrieving by ID, and retrieving by customer.</p>
 *
 * @see ShowRequestRepository
 */
public class InMemoryShowRequestRepository implements ShowRequestRepository {
    private final Map<Long, ShowRequest> store = new HashMap<>();
    private static long LAST_SHOW_REQUEST_ID = 1L;

    /**
     * Saves a ShowRequest entity to the in-memory store.
     *
     * <p>If the entity's ID is null, it generates a new ID and adds the entity to the store.</p>
     *
     * @param entity the ShowRequest entity to save
     * @return an Optional containing the saved entity or an empty Optional if the entity is null
     */
    @Override
    public Optional<ShowRequest> saveInStore(ShowRequest entity) {
        if (entity == null) return Optional.empty();

        if (entity.identity() == null) {
            entity.setId(LAST_SHOW_REQUEST_ID++);
        }

        store.put(entity.identity(), entity);
        return Optional.of(entity);
    }

    /**
     * Retrieves all ShowRequest entities from the in-memory store.
     *
     * @return a list of all ShowRequest entities
     */
    @Override
    public List<ShowRequest> getAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * Finds a ShowRequest by its ID.
     *
     * @param id the ID of the ShowRequest to find
     * @return an Optional containing the found ShowRequest or an empty Optional if not found
     */
    @Override
    public Optional<ShowRequest> findById(Object id) {
        return Optional.ofNullable(store.get((long)(id)));
    }

    /**
     * Finds all ShowRequest entities associated with a given customer.
     *
     * @param costumer the customer to find ShowRequests for
     * @return an Optional containing a list of ShowRequests for the customer or an empty Optional if none found
     */
    @Override
    public Optional<List<ShowRequest>> findByCostumer(Costumer costumer) {
        if (costumer == null) return Optional.empty();

        List<ShowRequest> result = new ArrayList<>();
        for (ShowRequest request : store.values()) {
            if (costumer.identity().equals(request.getCostumer().identity())) {
                result.add(request);
            }
        }

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    /**
     * Updates an existing ShowRequest in the in-memory store.
     *
     * <p>If the ShowRequest does not exist, it returns an empty Optional. Otherwise, it updates the entity
     * and returns the updated ShowRequest.</p>
     *
     * @param entity the ShowRequest entity to update
     * @return an Optional containing the updated entity or an empty Optional if the entity does not exist
     */
    @Override
    public Optional<ShowRequest> updateShowRequest(ShowRequest entity) {
        if (entity == null || entity.identity() == null) {
            return Optional.empty();
        }

        if (!store.containsKey(entity.identity())) {
            return Optional.empty(); // Cannot update if it doesn't exist
        }

        store.put(entity.identity(), entity); // Overwrite the existing ShowRequest
        return Optional.of(entity);
    }
}
