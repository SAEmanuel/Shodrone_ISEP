package persistence.inmemory;

import domain.history.AuditLogEntry;
import persistence.interfaces.AuditLogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class InMemoryAuditLogRepository implements AuditLogRepository {

    private final List<AuditLogEntry> store = new CopyOnWriteArrayList<>();

    @Override
    public void save(AuditLogEntry entry) {
        store.add(entry);
    }

    @Override
    public void saveAll(List<AuditLogEntry> entries) {
        store.addAll(entries);
    }

    @Override
    public List<AuditLogEntry> findByEntity(String entityName, String entityId) {
        return store.stream()
                .filter(entry -> entry.entityName().equalsIgnoreCase(entityName)
                        && entry.entityId().equalsIgnoreCase(entityId))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogEntry> findAll() {
        return new ArrayList<>(store);
    }
}