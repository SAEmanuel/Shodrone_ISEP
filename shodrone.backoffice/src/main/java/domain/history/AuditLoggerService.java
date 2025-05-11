package domain.history;

import persistence.interfaces.AuditLogRepository;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

public class AuditLoggerService {

    private final AuditLogRepository repository;

    public AuditLoggerService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public void logChanges(Object oldObj, Object newObj, String entityId, String user, Set<String> auditFields) {
        if (!oldObj.getClass().equals(newObj.getClass()))
            throw new IllegalArgumentException();

        for (Field field : oldObj.getClass().getDeclaredFields()) {
            if (!auditFields.contains(field.getName())) {
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
                            String.valueOf(oldVal),
                            String.valueOf(newVal),
                            user
                    );
                    repository.save(entry);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void logCreation(Object newObj, String entityId, String user, Set<String> auditFields) {
        for (Field field : newObj.getClass().getDeclaredFields()) {
            if (!auditFields.contains(field.getName())) continue;
            field.setAccessible(true);
            try {
                Object newVal = field.get(newObj);
                if (newVal != null) {
                    AuditLogEntry entry = new AuditLogEntry(
                            newObj.getClass().getSimpleName(),
                            entityId,
                            field.getName(),
                            null,
                            String.valueOf(newVal),
                            user
                    );
                    repository.save(entry);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
