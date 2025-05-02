package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.valueObjects.NIF;
import jakarta.persistence.TypedQuery;
import persistence.interfaces.CostumerRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class CostumerJPAImpl extends JpaBaseRepository<Costumer, Long> implements CostumerRepository {

    @Override
    public Optional<Costumer> findByID(Object id) {
        if (!(id instanceof Long)) return Optional.empty();
        return Optional.ofNullable(entityManager().find(Costumer.class, id));
    }

    @Override
    public Optional<Costumer> findByNIF(NIF nif) {
        TypedQuery<Costumer> query = entityManager().createQuery(
                "SELECT c FROM Costumer c WHERE c.nif = :nif", Costumer.class);
        query.setParameter("nif", nif);
        List<Costumer> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Optional<List<Costumer>> getAllCostumers() {
        TypedQuery<Costumer> query = entityManager().createQuery(
                "SELECT c FROM Costumer c", Costumer.class);
        List<Costumer> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    @Override
    public Optional<Costumer> saveInStore(Costumer entity, NIF costumerNIF) {
        Optional<Costumer> existing = findByNIF(costumerNIF);
        if (existing.isEmpty()) {
            add(entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}
