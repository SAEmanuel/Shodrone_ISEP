package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.valueObjects.NIF;
import jakarta.persistence.TypedQuery;
import persistence.interfaces.CostumerRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.List;
import java.util.Optional;

public class CostumerJPAImpl extends JpaAutoTxRepository<Costumer, Long, Long> implements CostumerRepository {

    public CostumerJPAImpl() {
        super("shodrone_backoffice", "customerSystemID");
    }

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
        if (existing.isPresent()) {
            System.out.println("❗ Cliente com o mesmo NIF já existe.");
            return Optional.empty();
        }

        try {
            return Optional.of(super.save(entity));
        } catch (Exception e) {
            System.err.println("❌ Erro ao guardar o cliente:");
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
