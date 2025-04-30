package authz;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class UserRoleRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("shodrone_backoffice");

    public void save(UserRole role) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
        em.close();
    }

    public Optional<UserRole> findById(String id) {
        EntityManager em = emf.createEntityManager();
        UserRole role = em.find(UserRole.class, id);
        em.close();
        return Optional.ofNullable(role);
    }

    public List<UserRole> findAll() {
        EntityManager em = emf.createEntityManager();
        List<UserRole> roles = em.createQuery("SELECT r FROM UserRole r", UserRole.class).getResultList();
        em.close();
        return roles;
    }

}
