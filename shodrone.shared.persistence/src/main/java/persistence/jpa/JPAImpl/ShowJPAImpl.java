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

/**
 * JPA implementation of the {@link ShowRepository} interface.
 * <p>
 * This class provides persistence operations for {@link Show} entities
 * using the Java Persistence API (JPA). It extends {@link JpaBaseRepository},
 * gaining basic CRUD functionality, and implements custom queries as needed.
 *
 * <p>Supported operations include:
 * <ul>
 *     <li>Saving a new Show entity</li>
 *     <li>Fetching all shows</li>
 *     <li>Finding a show by ID</li>
 *     <li>Finding all shows by a specific customer</li>
 *     <li>Detecting duplicate shows based on location, date, and customer</li>
 * </ul>
 *
 * <p>This class is meant to be used as part of a JPA-based persistence context.
 */
public class ShowJPAImpl extends JpaBaseRepository<Show, Long> implements ShowRepository {

    /**
     * Saves a {@link Show} entity to the database.
     *
     * @param entity the Show entity to persist
     * @return an {@link Optional} containing the saved entity, or empty if input is null
     */
    @Override
    public Optional<Show> saveInStore(Show entity) {
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(add(entity));
    }

    /**
     * Retrieves all {@link Show} entities from the database.
     *
     * @return a list of all stored shows
     */
    @Override
    public List<Show> getAll() {
        return findAll();
    }

    /**
     * Finds a {@link Show} entity by its primary key (ID).
     *
     * @param id the ID of the show
     * @return an {@link Optional} containing the found Show, or empty if not found
     */
    @Override
    public Optional<Show> findById(Object id) {
        Show show = super.findById((Long) id);
        return Optional.ofNullable(show);
    }

    /**
     * Finds all shows associated with a given {@link Costumer}.
     *
     * @param costumer the customer whose shows to retrieve
     * @return an {@link Optional} with a list of shows, or empty if none found or if input is null
     */
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

    /**
     * Checks if a duplicate {@link Show} exists in the database.
     * <p>
     * A show is considered a duplicate if it has the same location, date, and customer ID.
     *
     * @param location   the location of the show
     * @param showDate   the date and time of the show
     * @param customerID the ID of the customer
     * @return an {@link Optional} containing the found duplicate, or empty if none found
     */
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
