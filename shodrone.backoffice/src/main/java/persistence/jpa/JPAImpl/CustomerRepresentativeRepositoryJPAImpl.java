package persistence.jpa.JPAImpl;

import domain.entity.CustomerRepresentative;
import persistence.CustomerRepresentativeRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 * JPA implementation of the {@link CustomerRepresentativeRepository} interface.
 * Provides automatic transactional support for managing {@link CustomerRepresentative} entities.
 * Uses the "shodrone_backoffice" persistence unit and "id" as the identity field.
 */
public class CustomerRepresentativeRepositoryJPAImpl
        extends JpaAutoTxRepository<CustomerRepresentative, Long, Long>
        implements CustomerRepresentativeRepository {

    /**
     * Constructs a new JPA repository for customer representatives.
     * Configured to use the "shodrone_backoffice" persistence unit.
     */
    public CustomerRepresentativeRepositoryJPAImpl() {
        super("shodrone_backoffice", "id");
    }
}
