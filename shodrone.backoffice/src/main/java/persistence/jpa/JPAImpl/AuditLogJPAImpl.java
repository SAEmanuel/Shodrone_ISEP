package persistence.jpa.JPAImpl;

import domain.history.AuditLogEntry;
import persistence.interfaces.AuditLogRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;

public class AuditLogJPAImpl extends JpaBaseRepository<AuditLogEntry, Long> implements AuditLogRepository {

    @Override
    public void save(AuditLogEntry entry) {
        add(entry);
    }

    @Override
    public void saveAll(List<AuditLogEntry> entries) {
        for (AuditLogEntry entry : entries) {
            add(entry);
        }
    }

    @Override
    public List<AuditLogEntry> findByEntity(String entityName, String entityId) {
        return entityManager()
                .createQuery("SELECT e FROM AuditLogEntry e WHERE e.entityName = :entityName AND e.entityId = :entityId ORDER BY e.changedAt DESC", AuditLogEntry.class)
                .setParameter("entityName", entityName)
                .setParameter("entityId", entityId)
                .getResultList();
    }

    @Override
    public List<AuditLogEntry> findAll() {
        return entityManager()
                .createQuery("SELECT e FROM AuditLogEntry e ORDER BY e.changedAt DESC", AuditLogEntry.class)
                .getResultList();
    }



}