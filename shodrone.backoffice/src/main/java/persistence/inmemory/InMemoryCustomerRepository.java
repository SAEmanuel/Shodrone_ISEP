package persistence.inmemory;

import domain.entity.Costumer;
import domain.valueObjects.NIF;
import persistence.interfaces.CostumerRepository;
import utils.Validations;

import java.util.*;

public class InMemoryCustomerRepository implements CostumerRepository {
    private final Map<NIF, Costumer> store;
    private static Long LAST_COSTUMER_ID = 0L;

    public InMemoryCustomerRepository() {
        this.store = new HashMap<>();
    }

    @Override
    public Optional<Costumer> findByID(Object id) {
        if (!(id instanceof Long)) return Optional.empty();
        Long customerId = (Long) id;
        return store.values().stream()
                .filter(c -> c.identity().equals(customerId))
                .findFirst();
    }

    @Override
    public Optional<Costumer> findByNIF(NIF nif) {
        return Optional.ofNullable(store.get(nif));
    }

    @Override
    public Optional<List<Costumer>> getAllCostumers() {
        if (store.isEmpty()) return Optional.empty();
        return Optional.of(new ArrayList<>(store.values()));
    }

    @Override
    public Optional<Costumer> saveInStore(Costumer entity, NIF costumerNIF) {
        if (!Validations.containsKey(this.store, costumerNIF)) {
            entity.setCustomerSystemID(LAST_COSTUMER_ID++);
            store.put(costumerNIF, entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }

}
