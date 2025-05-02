package persistence.inmemory;

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
}
