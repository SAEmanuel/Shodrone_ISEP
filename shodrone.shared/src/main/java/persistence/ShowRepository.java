package persistence;

import domain.entity.Costumer;
import domain.entity.Show;
import java.util.List;
import java.util.Optional;

public interface ShowRepository {

    public Optional<Show> saveInStore(Show entity);
    public List<Show> getAll();
    public Optional<Show> findById(Object id);
    public Optional<List<Show>> findByCostumer(Costumer costumer);

}
