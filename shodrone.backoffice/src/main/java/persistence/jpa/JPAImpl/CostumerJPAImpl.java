package persistence.jpa.JPAImpl;

import domain.valueObjects.NIF;
import persistence.interfaces.CostumerRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;


public class CostumerJPAImpl<Costumer> extends JpaBaseRepository<Costumer, Integer> implements CostumerRepository<Costumer> {

    @Override
    public Optional<Costumer> findByID(Object id) {
        return Optional.empty();
    }

    @Override
    public Optional<Costumer> findByNIF(NIF nif) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Costumer>> getAllCostumers() {
        return Optional.empty();
    }

    @Override
    public Optional<Costumer> save(Costumer entity) {
        return null;
    }
}
