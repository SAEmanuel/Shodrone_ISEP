package persistence.jpa.JPAImpl;

import domain.entity.CustomerRepresentative;
import persistence.interfaces.CustomerRepresentativeRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 * JPA implementation of the CustomerRepresentativeRepository.
 */
public class CustomerRepresentativeRepositoryJPAImpl
        extends JpaAutoTxRepository<CustomerRepresentative, Long, Long>
        implements CustomerRepresentativeRepository {

    public CustomerRepresentativeRepositoryJPAImpl() {
        super("shodrone_backoffice", "id");
    }

}
