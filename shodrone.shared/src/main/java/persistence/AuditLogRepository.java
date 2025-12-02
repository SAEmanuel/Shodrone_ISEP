package persistence;

import domain.entity.AuditLogEntry;

import java.util.List;

public interface AuditLogRepository {

    /**
     * Save a single audit log entry.
     * @param entry The entry to save.
     */
    void save(AuditLogEntry entry);

    /**
     * Save multiple audit log entries at once.
     * @param entries List of entries.
     */
    void saveAll(List<AuditLogEntry> entries);

    /**
     * Retrieve audit logs for a specific entity.
     * @param entityName Class name of the entity.
     * @param entityId ID of the specific entity instance.
     * @return List of matching logs.
     */
    List<AuditLogEntry> findByEntity(String entityName, String entityId);

    /**
     * Retrieve all audit logs (optional, for full logs inspection).
     * @return List of all audit logs.
     */
    List<AuditLogEntry> findAll();
}