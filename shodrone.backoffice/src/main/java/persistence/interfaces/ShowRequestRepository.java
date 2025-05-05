package persistence.interfaces;

import domain.entity.Costumer;
import domain.entity.ShowRequest;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the repository that manages {@link ShowRequest} entities.
 * <p>
 * This repository interface defines the operations that can be performed on {@link ShowRequest} entities in a data store.
 * The operations include saving, retrieving, updating, and finding show requests by their ID or associated customer.
 * </p>
 */
public interface ShowRequestRepository {

    /**
     * Saves a {@link ShowRequest} entity in the store.
     *
     * @param entity the {@link ShowRequest} entity to be saved
     * @return an {@link Optional} containing the saved {@link ShowRequest} if successful, or an empty {@link Optional} if the save operation fails
     */
    public Optional<ShowRequest> saveInStore(ShowRequest entity);

    /**
     * Retrieves all {@link ShowRequest} entities from the store.
     *
     * @return a list of all {@link ShowRequest} entities
     */
    public List<ShowRequest> getAll();

    /**
     * Finds a {@link ShowRequest} entity by its ID.
     *
     * @param id the ID of the {@link ShowRequest}
     * @return an {@link Optional} containing the {@link ShowRequest} if found, or an empty {@link Optional} if not found
     */
    public Optional<ShowRequest> findById(Object id);

    /**
     * Finds all {@link ShowRequest} entities associated with a given {@link Costumer}.
     *
     * @param costumer the {@link Costumer} to search for associated show requests
     * @return an {@link Optional} containing a list of {@link ShowRequest} entities if found, or an empty {@link Optional} if no show requests are found for the given customer
     */
    public Optional<List<ShowRequest>> findByCostumer(Costumer costumer);

    /**
     * Updates an existing {@link ShowRequest} entity in the store.
     *
     * @param entity the {@link ShowRequest} entity to be updated
     * @return an {@link Optional} containing the updated {@link ShowRequest} if the update was successful, or an empty {@link Optional} if the update operation fails
     */
    public Optional<ShowRequest> updateShowRequest(ShowRequest entity);
}