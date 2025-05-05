package domain.history;

import java.io.Serializable;

/**
 * Interface that represents an entity with a unique identity.
 * Any class that implements this interface must provide an implementation of the `identity` method,
 * which returns the unique identifier of the entity.
 *
 * @param <ID> The type of the entity's identifier. It must be serializable.
 */
public interface IdentifiableEntity<ID extends Serializable> {

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return The identifier of the entity.
     */
    ID identity();
}
