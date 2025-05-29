package persistence;

import domain.entity.CustomerRepresentative;
import eapli.framework.domain.repositories.DomainRepository;

/**
 * Repository interface for managing {@link CustomerRepresentative} entities.
 * Extends the generic {@link DomainRepository} to provide basic CRUD operations
 * using the representative's ID as the primary key.
 */
public interface CustomerRepresentativeRepository extends DomainRepository<Long, CustomerRepresentative> {
}
