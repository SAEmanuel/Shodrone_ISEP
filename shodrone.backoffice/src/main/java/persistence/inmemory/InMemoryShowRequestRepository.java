package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.ShowRequest;
import persistence.interfaces.ShowRequestRepository;

import java.util.*;

public class InMemoryShowRequestRepository implements ShowRequestRepository {
    private final Map<Long, ShowRequest> store = new HashMap<>();
    private static long LAST_SHOW_REQUEST_ID = 1L;

    @Override
    public Optional<ShowRequest> saveInStore(ShowRequest entity) {
        if (entity == null) return Optional.empty();

        if (entity.identity() == null) {
            entity.setId(LAST_SHOW_REQUEST_ID++);
        }

        store.put(entity.identity(), entity);
        return Optional.of(entity);
    }

    @Override
    public List<ShowRequest> getAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<ShowRequest> findById(Object id) {
        return Optional.ofNullable(store.get((long)(id)));
    }

    @Override
    public Optional<List<ShowRequest>> findByCostumer(Costumer costumer) {
        if (costumer == null) return Optional.empty();

        List<ShowRequest> result = new ArrayList<>();
        for (ShowRequest request : store.values()) {
            if (costumer.identity().equals(request.getCostumer().identity())) {
                result.add(request);
            }
        }

        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    @Override
    public Optional<ShowRequest> updateShowRequest(ShowRequest entity) {
        if (entity == null || entity.identity() == null) {
            return Optional.empty();
        }

        if (!store.containsKey(entity.identity())) {
            return Optional.empty(); // Cannot update if it doesn't exist
        }

        store.put(entity.identity(), entity); // Overwrite the existing ShowRequest
        return Optional.of(entity);
    }

}
