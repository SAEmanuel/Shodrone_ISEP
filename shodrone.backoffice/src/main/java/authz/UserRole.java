package authz;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    private String id;

    private String description;

    public UserRole() {}

    public UserRole(String id, String description) {
        if (id == null || id.isBlank() || description == null || description.isBlank()) {
            throw new IllegalArgumentException("UserRole id and/or description cannot be blank.");
        }
        this.id = id.trim().toUpperCase();
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id != null && !id.isBlank()) {
            this.id = id.trim().toUpperCase();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
    }

    public boolean changeDescription(String newDescription) {
        if (newDescription == null || newDescription.isBlank()) {
            return false;
        }
        this.description = newDescription;
        return true;
    }

    public boolean hasId(String id) {
        return id != null && !id.isBlank() && this.id.equals(id.trim().toUpperCase());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", id, description);
    }
}
