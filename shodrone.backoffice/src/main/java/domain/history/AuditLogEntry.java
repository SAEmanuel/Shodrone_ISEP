package domain.history;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AuditLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String entityName;
    private String entityId;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private String author;
    private LocalDateTime timestamp;

    protected AuditLogEntry() {
        // For ORM
    }

    public AuditLogEntry(String entityName, String entityId, String fieldName,
                         String oldValue, String newValue, String author) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.author = author;
        this.timestamp = LocalDateTime.now();
    }

    public Long id() {
        return id;
    }

    public String entityName() {
        return entityName;
    }

    public String entityId() {
        return entityId;
    }

    public String fieldName() {
        return fieldName;
    }

    public String oldValue() {
        return oldValue;
    }

    public String newValue() {
        return newValue;
    }

    public String author() {
        return author;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "AuditLogEntry [Entity: %s, ID: %s, Field: %s, Old: %s, New: %s, Author: %s, At: %s]",
                entityName, entityId, fieldName, oldValue, newValue, author, timestamp
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditLogEntry that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
