package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.entity.Show;
import domain.valueObjects.Location;
import jakarta.persistence.TypedQuery;
import persistence.ShowRepository;
import persistence.jpa.JpaBaseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ShowJPAImpl extends JpaBaseRepository<Show, Long> implements ShowRepository {

    @Override
    public Optional<Show> saveInStore(Show entity) {
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(add(entity));
    }

    @Override
    public List<Show> getAll() {
        return findAll();
    }

    @Override
    public Optional<Show> findById(Object id) {
        Show show = super.findById((Long) id);
        return Optional.ofNullable(show);
    }

    @Override
    public Optional<List<Show>> findByCostumer(Costumer costumer) {
        if (costumer == null) {
            return Optional.empty();
        }

        List<Show> results = entityManager().createQuery(
                        "SELECT s FROM Show s WHERE s.customerID = :costumerId", Show.class)
                .setParameter("costumerId", costumer.identity())
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results);
    }

    @Override
    public Optional<Show> findDuplicateShow(Location location, LocalDateTime showDate, Long customerID) {
        TypedQuery<Show> query = entityManager().createQuery(
                "SELECT s FROM Show s WHERE s.location = :location AND s.showDate = :showDate AND s.customerID = :customerID",
                Show.class
        );

        query.setParameter("location", location);
        query.setParameter("showDate", showDate);
        query.setParameter("customerID", customerID);

        List<Show> result = query.getResultList();

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }

}
