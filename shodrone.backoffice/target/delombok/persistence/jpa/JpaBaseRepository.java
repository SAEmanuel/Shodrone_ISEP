/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jpa;

import jakarta.persistence.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * An utility abstract class for implementing JPA repositories. based on
 * JpaBaseRepository by Paulo Gandra de Sousa
 *
 * @param <T> the entity type that we want to build a repository for
 * @param <ID> the key type of the entity
 */
public abstract class JpaBaseRepository<T, ID extends Serializable> implements GenericRepository<T, ID> {

    @PersistenceUnit
    private static EntityManagerFactory emFactory;
    private final Class<T> entityClass;
    private EntityManager _entityManager;

    protected EntityManagerFactory entityManagerFactory() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory(persistenceUnitName());
        }
        return emFactory;
    }

    @SuppressWarnings("unchecked")
    public JpaBaseRepository() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected EntityManager entityManager() {
        if (_entityManager == null || !_entityManager.isOpen()) {
            _entityManager = entityManagerFactory().createEntityManager();
        }
        return _entityManager;
    }

    /**
     * inserts an entity and commits
     *
     * @param entity
     * @return the persisted entity
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
     * reads an entity given its ID
     *
     * @param id
     * @return
     */
    public T findById(ID id) {
        return this.entityManager().find(entityClass, id);
    }

    /**
     * Returns the List of all entities in the persistence store
     *
     * @return
     */
    //@SuppressWarnings("unchecked")
    public List<T> findAll() {
        Query query = entityManager().createQuery(
                "SELECT e FROM " + entityClass.getSimpleName() + " e");
        List<T> list = query.getResultList();
        return list;
    }

    /**
     * derived classes should implement this method to return the name of the
     * persistence unit
     *
     * @return the name of the persistence unit
     */
    protected String persistenceUnitName() {
        return "shodrone_backoffice";
    }

}