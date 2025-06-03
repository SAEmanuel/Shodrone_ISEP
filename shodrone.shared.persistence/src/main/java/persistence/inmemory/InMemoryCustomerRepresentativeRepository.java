package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.CustomerRepresentative;
import domain.entity.Email;
import persistence.CustomerRepresentativeRepository;

import java.util.*;

/**
 * In-memory implementation of the {@link CustomerRepresentativeRepository} interface.
 * Stores and manages {@link CustomerRepresentative} entities using local data structures.
 * Useful for testing or temporary scenarios without requiring database persistence.
 */
public class InMemoryCustomerRepresentativeRepository implements CustomerRepresentativeRepository {

    /** Internal map to store representatives by ID. */
    private final Map<Long, CustomerRepresentative> idMap = new HashMap<>();

    /** Internal map to store representatives by email. */
    private final Map<Email, CustomerRepresentative> emailMap = new HashMap<>();

    /** Auto-incrementing ID counter for new entities. */
    private long nextId = 1L;

    /**
     * Saves the given customer representative entity in memory.
     * If it doesn't have an ID, one is automatically assigned using reflection.
     *
     * @param entity The representative to save.
     * @return The saved entity.
     * @throws RuntimeException if the ID cannot be set via reflection.
     */
    @Override
    public Optional<CustomerRepresentative> saveInStore(CustomerRepresentative entity){
        if (entity.identity() == null) {
            try {
                var field = CustomerRepresentative.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(entity, nextId);
            } catch (Exception e) {
                throw new RuntimeException("Failed to set ID", e);
            }
        }
        idMap.put(entity.identity(), entity);
        emailMap.put(entity.getEmail(), entity);
        return Optional.of(entity);
    }

    /**
     * Retrieves a representative by its ID.
     *
     * @param id The ID of the representative.
     * @return An {@link Optional} containing the entity, if found.
     */
    public Optional<CustomerRepresentative> ofIdentity(Long id) {
        return Optional.ofNullable(idMap.get(id));
    }

    /**
     * Returns all stored customer representatives.
     *
     * @return A collection of all entities.
     */
    public Iterable<CustomerRepresentative> findAll() {
        return idMap.values();
    }

    /**
     * Removes the given representative from memory.
     *
     * @param entity The entity to remove.
     */
    public void delete(CustomerRepresentative entity) {
        idMap.remove(entity.identity());
        emailMap.remove(entity.getEmail());
    }

    /**
     * Deletes a representative by its ID.
     *
     * @param entityId The ID of the entity to delete.
     */
    public void deleteOfIdentity(Long entityId) {
        ofIdentity(entityId).ifPresent(this::delete);
    }

    /**
     * Returns the total number of stored representatives.
     *
     * @return Number of stored entities.
     */
    public long count() {
        return idMap.size();
    }

    @Override
    public Optional<Costumer> getAssociatedCustomer(String emailOfRepresentative){
        CustomerRepresentative foundChoice = emailMap.get(emailOfRepresentative);

        if(foundChoice == null){
            return Optional.empty();
        }
        return Optional.of(foundChoice.getCostumer());
    }

}
