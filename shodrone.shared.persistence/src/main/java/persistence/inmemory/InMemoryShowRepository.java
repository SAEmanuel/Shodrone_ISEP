package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.Show;
import domain.valueObjects.Location;
import persistence.ShowRepository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * In-memory implementation of the {@link ShowRepository} interface.
 *
 * <p>This repository is intended for testing or development environments where a
 * persistent database is not required. It stores {@link Show} entities in a
 * {@link HashMap}, grouping them by the {@code customerID}.</p>
 *
 * <p>The repository supports basic CRUD operations including save, find by ID,
 * find by customer, list all, and detect duplicates.</p>
 *
 * @author Catarina
 */
public class InMemoryShowRepository implements ShowRepository {

    /** Internal map to store shows, keyed by customer ID. */
    private final Map<Long, List<Show>> store = new HashMap<>();

    /** ID counter for auto-assigning new show IDs. */
    private static long LAST_SHOW_ID = 1L;

    /**
     * Saves a {@link Show} entity into the in-memory store.
     *
     * <p>If the show has no ID, a new ID is assigned. The show is then
     * added to the list of shows for the corresponding customer.</p>
     *
     * @param entity the show entity to store
     * @return an {@link Optional} containing the stored entity, or empty if input is null
     */
    @Override
    public Optional<Show> saveInStore(Show entity) {
        if (entity == null) return Optional.empty();

        if (entity.identity() == null) {
            entity.setShowID(LAST_SHOW_ID++);
        }

        Long customerID = entity.getCustomerID();
        store.computeIfAbsent(customerID, k -> new ArrayList<>());
        store.get(customerID).add(entity);

        return Optional.of(entity);
    }

    /**
     * Returns a list of all stored {@link Show} entities, across all customers.
     *
     * @return a list of all shows
     */
    @Override
    public List<Show> getAll() {
        List<Show> allShows = new ArrayList<>();
        for (List<Show> shows : store.values()) {
            allShows.addAll(shows);
        }
        return allShows;
    }

    /**
     * Searches for a {@link Show} by its ID.
     *
     * @param id the identifier of the show
     * @return an {@link Optional} containing the show if found, or empty otherwise
     */
    @Override
    public Optional<Show> findById(Object id) {
        for (List<Show> shows : store.values()) {
            for (Show show : shows) {
                if (show.identity().equals(id)) {
                    return Optional.of(show);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Finds all {@link Show} entries belonging to a specific {@link Costumer}.
     *
     * @param costumer the customer whose shows are to be retrieved
     * @return an {@link Optional} containing the list of shows if any exist, or empty otherwise
     */
    @Override
    public Optional<List<Show>> findByCostumer(Costumer costumer) {
        return Optional.ofNullable(store.get(costumer.identity()));
    }

    /**
     * Checks for duplicate shows with the same {@link Location}, date, and customer.
     *
     * <p>This is useful to avoid scheduling duplicate shows for the same customer
     * at the same time and place.</p>
     *
     * @param location the location of the show
     * @param showDate the scheduled date/time
     * @param customerID the ID of the customer
     * @return an {@link Optional} containing the duplicate show if found, or empty otherwise
     */
    @Override
    public Optional<Show> findDuplicateShow(Location location, LocalDateTime showDate, Long customerID) {
        List<Show> showsForCustomer = store.get(customerID);
        if (showsForCustomer == null) {
            return Optional.empty();
        }

        for (Show show : showsForCustomer) {
            if (show.getLocation().equals(location)
                    && show.getShowDate().equals(showDate)
                    && Objects.equals(show.getCustomerID(), customerID)) {
                return Optional.of(show);
            }
        }

        return Optional.empty();
    }
}
