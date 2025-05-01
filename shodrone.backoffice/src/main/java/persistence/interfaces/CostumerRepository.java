package persistence.interfaces;

import domain.entity.Costumer;
import domain.valueObjects.NIF;

import java.util.List;
import java.util.Optional;

public interface CostumerRepository {

    Optional<Costumer> saveInStore(Costumer entity, NIF costumerNIF);
    Optional<Costumer> findByID(Object id);
    Optional<Costumer> findByNIF(NIF nif);
    Optional<List<Costumer>> getAllCostumers();
}
