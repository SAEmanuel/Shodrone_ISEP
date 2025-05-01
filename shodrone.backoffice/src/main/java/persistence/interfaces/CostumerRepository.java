package persistence.interfaces;

import domain.valueObjects.NIF;

import java.util.List;
import java.util.Optional;

public interface CostumerRepository<Costumer>{

    Optional<Costumer>  save(Costumer entity);
    Optional<Costumer> findByID(Costumer id);
    Optional<Costumer>  findByNIF(NIF nif);
    Optional<List<Costumer>>  getAllCostumers();
}
