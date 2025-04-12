package shodrone.persistence.impl.jpa;

import eapli.framework.infrastructure.repositories.impl.jpa.JpaTransactionalRepository;
import app.Application;

/**
 * Base class for all JPA repository implementations in the Shodrone project.
 *
 * @param <T> The entity type
 * @param <K> The entity's business key type
 * @param <I> The internal DB ID type
 */
public abstract class ShodroneJpaRepositoryBase<T, K, I>
        extends JpaTransactionalRepository<T, K, I> {

    protected ShodroneJpaRepositoryBase(final String identityFieldName) {
        super(Application.settings().persistenceUnitName(),
                Application.settings().extendedPersistenceProperties(),
                identityFieldName);
    }
}
