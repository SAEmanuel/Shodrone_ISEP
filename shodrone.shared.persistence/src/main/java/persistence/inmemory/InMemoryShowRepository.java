package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.Show;
import domain.valueObjects.Location;
import persistence.ShowRepository;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryShowRepository implements ShowRepository {
    private final Map<Long, List<Show>> store = new HashMap<>();
    private static long LAST_SHOW_ID = 1L;

    @Override
    public Optional<Show> saveInStore(Show entity) {
        if (entity == null) return Optional.empty();

        if (entity.identity() == null) {
            entity.setShowID(LAST_SHOW_ID++);
        }

        Long customerID = entity.getCustomerID();
        if (!store.containsKey(customerID)) {
            store.put(customerID, new ArrayList<>());
        }
        store.get(customerID).add(entity);

        return Optional.of(entity);
    }

    @Override
    public List<Show> getAll() {
        List<Show> allShows = new ArrayList<>();
        for (List<Show> shows : store.values()) {
            allShows.addAll(shows);
        }
        return allShows;
    }

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

    @Override
    public Optional<List<Show>> findByCostumer(Costumer costumer) {
        if (store.containsKey(costumer.identity())) {
            return Optional.of(store.get(costumer.identity()));
        }
        return Optional.empty();
    }

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
