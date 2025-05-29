package persistence.jpa;

import jakarta.persistence.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Utility abstract class for implementing JPA repositories. This class is based on
 * {@code JpaBaseRepository} by Paulo Gandra de Sousa.
 *
 * <p>This class provides common persistence operations such as {@link #add}, {@link #update}, {@link #delete},
 * and {@link #findById} for any JPA entity. It abstracts the JPA EntityManager interactions
 * and provides a convenient base for repository classes.</p>
 *
 * @param <T>  the entity type for which the repository is being created.
 * @param <ID> the type of the entity's identifier (primary key).
 */
public abstract class JpaBaseRepository<T, ID extends Serializable> implements GenericRepository<T, ID> {

    /**
     * The entity manager factory used to create entity managers.
     */
    @PersistenceUnit
    private static EntityManagerFactory emFactory;

    /**
     * The entity class being handled by this repository.
     */
    private final Class<T> entityClass;

    /**
     * The entity manager used for interacting with the persistence context.
     */
    private EntityManager _entityManager;

    /**
     * Returns the {@link EntityManagerFactory} instance.
     * Creates it if necessary.
     *
     * @return the {@link EntityManagerFactory}
     */
    protected EntityManagerFactory entityManagerFactory() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory(persistenceUnitName());
        }
        return emFactory;
    }

    /**
     * Constructor that sets the entity class type dynamically using reflection.
     */
    @SuppressWarnings("unchecked")
    public JpaBaseRepository() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    /**
     * Returns an {@link EntityManager} instance.
     * Creates a new one if not already created or if it is closed.
     *
     * @return the {@link EntityManager}
     */
    protected EntityManager entityManager() {
        if (_entityManager == null || !_entityManager.isOpen()) {
            _entityManager = entityManagerFactory().createEntityManager();
        }
        return _entityManager;
    }

    /**
     * Persists a new entity and commits the transaction.
     *
     * @param entity the entity to persist
     * @return the persisted entity
     * @throws IllegalArgumentException if the entity is {@code null}
     */
    public T add(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = entityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
        em.close();
        return entity;
    }

    /**
     * Updates an existing entity in the persistence store and commits the transaction.
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    @Override
    public T update(T entity) {
        EntityManager em = entityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        T updated = em.merge(entity);
        tx.commit();
        em.close();
        return updated;
    }

    /**
     * Deletes an entity from the persistence store and commits the transaction.
     *
     * @param entity the entity to delete
     */
    @Override
    public void delete(T entity) {
        EntityManager em = entityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        tx.commit();
        em.close();
    }

    /**
     * Finds an entity by its identifier (ID).
     *
     * @param id the identifier of the entity
     * @return the found entity or {@code null} if not found
     */
    public T findById(ID id) {
        return this.entityManager().find(entityClass, id);
    }

    /**
     * Retrieves all entities from the persistence store.
     *
     * @return a list of all entities
     */
    public List<T> findAll() {
        Query query = entityManager().createQuery(
                "SELECT e FROM " + entityClass.getSimpleName() + " e");
        List<T> list = query.getResultList();
        return list;
    }

    /**
     * Returns the name of the persistence unit for this repository.
     * Subclasses should implement this method to provide the persistence unit name.
     *
     * @return the name of the persistence unit
     */
    protected String persistenceUnitName() {
        return "shodrone_backoffice";
    }
}