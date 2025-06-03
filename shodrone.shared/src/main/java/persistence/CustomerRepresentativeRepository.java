package persistence;

import domain.entity.Costumer;
import domain.entity.CustomerRepresentative;
import domain.valueObjects.NIF;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link CustomerRepresentative} entities.
 * Extends the generic {@link DomainRepository} to provide basic CRUD operations
 * using the representative's ID as the primary key.
 */
public interface CustomerRepresentativeRepository {
    public Optional<CustomerRepresentative> saveInStore(CustomerRepresentative entity);
    public Optional<Costumer> getAssociatedCustomer(String emailOfRepresentative);
}
