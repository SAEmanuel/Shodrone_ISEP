package persistence;

import domain.history.AuditLogEntry;
import persistence.interfaces.AuditLogRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class AuditLoggerService {

    private final AuditLogRepository repository;

    public AuditLoggerService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public void logChanges(Object oldObj, Object newObj, String entityId, String user) {
        if (!oldObj.getClass().equals(newObj.getClass())) throw new IllegalArgumentException();

        for (Field field : oldObj.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object oldVal = field.get(oldObj);
                Object newVal = field.get(newObj);

                if (!Objects.equals(oldVal, newVal)) {
                    AuditLogEntry entry = new AuditLogEntry(
                            oldObj.getClass().getSimpleName(),
                            entityId,
                            field.getName(),
                            serializeValue(oldVal),
                            serializeValue(newVal),
                            user
                    );
                    repository.save(entry);
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String serializeValue(Object val) {
        if (val == null) return "null";
        return val.toString();
    }
}
