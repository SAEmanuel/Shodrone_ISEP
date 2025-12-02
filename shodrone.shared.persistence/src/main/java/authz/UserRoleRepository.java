package authz;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import domain.entity.UserRole;
import java.util.Optional;

/**
 * Repository class for managing {@link UserRole} entities.
 * Provides basic persistence operations using JPA, including saving,
 * retrieving by ID, and listing all roles.
 *
 * This implementation uses a shared {@code EntityManagerFactory}
 * configured for the {@code shodrone_backoffice} persistence unit.
 */
public class UserRoleRepository {

    /** Shared entity manager factory for database access. */
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("shodrone_backoffice");

    /**
     * Persists a new {@link UserRole} entity to the database.
     *
     * @param role The role to be saved.
     */
    public void save(UserRole role) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Retrieves a {@link UserRole} entity by its ID.
     *
     * @param id The role ID to search for.
     * @return An {@link Optional} containing the role if found, or empty otherwise.
     */
    public Optional<UserRole> findById(String id) {
        EntityManager em = emf.createEntityManager();
        UserRole role = em.find(UserRole.class, id);
        em.close();
        return Optional.ofNullable(role);
    }

    /**
     * Retrieves all {@link UserRole} entities from the database.
     *
     * @return A list of all stored roles.
     */
    public List<UserRole> findAll() {
        EntityManager em = emf.createEntityManager();
        List<UserRole> roles = em.createQuery("SELECT r FROM UserRole r", UserRole.class).getResultList();
        em.close();
        return roles;
    }
}
