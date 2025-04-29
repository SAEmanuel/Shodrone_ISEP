package authz;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("shodrone_backoffice");

    public void save(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public Optional<User> findByEmail(Email email) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, email);
        em.close();
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        em.close();
        return users;
    }

    public void delete(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User managedUser = em.merge(user);
        em.remove(managedUser);
        em.getTransaction().commit();
        em.close();
    }
}
