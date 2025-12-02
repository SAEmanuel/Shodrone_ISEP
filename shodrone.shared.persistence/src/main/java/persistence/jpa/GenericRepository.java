package persistence.jpa;

import java.io.Serializable;

/**
 * Generic interface for defining basic CRUD operations on entities.
 *
 * <p>This interface provides the methods for updating and deleting entities.
 * It is designed to be extended by specific repositories to handle various entity types.</p>
 *
 * @param <T>  the entity type for which the repository is being created.
 * @param <ID> the type of the entity's identifier (primary key).
 */
public interface GenericRepository<T, ID extends Serializable> {

    /**
     * Updates an existing entity in the persistence store.
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    T update(T entity);

    /**
     * Deletes an entity from the persistence store.
     *
     * @param entity the entity to delete
     */
    void delete(T entity);


}
