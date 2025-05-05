package persistence.interfaces;

import domain.entity.Costumer;
import domain.valueObjects.NIF;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the repository that manages {@link Costumer} entities.
 * <p>
 * This repository interface defines the operations that can be performed on {@link Costumer} entities in a data store.
 * The operations include saving, finding by ID, finding by NIF (Tax Identification Number), and retrieving all customers.
 * </p>
 */
public interface CostumerRepository {

    /**
     * Saves a {@link Costumer} entity in the store, using the provided NIF as the identifier.
     *
     * @param entity the {@link Costumer} entity to be saved
     * @param costumerNIF the NIF (Tax Identification Number) of the costumer
     * @return an {@link Optional} containing the saved {@link Costumer} if successful, or an empty {@link Optional} if the save operation fails
     */
    Optional<Costumer> saveInStore(Costumer entity, NIF costumerNIF);

    /**
     * Finds a {@link Costumer} entity by its ID.
     *
     * @param id the ID of the {@link Costumer}
     * @return an {@link Optional} containing the {@link Costumer} if found, or an empty {@link Optional} if not found
     */
    Optional<Costumer> findByID(Object id);

    /**
     * Finds a {@link Costumer} entity by its NIF (Tax Identification Number).
     *
     * @param nif the NIF of the {@link Costumer}
     * @return an {@link Optional} containing the {@link Costumer} if found, or an empty {@link Optional} if not found
     */
    Optional<Costumer> findByNIF(NIF nif);

    /**
     * Retrieves all {@link Costumer} entities from the store.
     *
     * @return an {@link Optional} containing a list of all {@link Costumer} entities if found, or an empty {@link Optional} if there are no costumers
     */
    Optional<List<Costumer>> getAllCostumers();
}
