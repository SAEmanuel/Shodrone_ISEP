package authz;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Represents a role assigned to users in the Shodrone system.
 * Each role has a unique identifier and a human-readable description.
 * Roles define the permissions and capabilities available to a user within the system.
 */
@Entity
@Table(name = "roles")
public class UserRole {

    /** Unique identifier for the role, stored in uppercase. */
    @Id
    private String id;

    /** Human-readable description of the role's purpose or responsibility. */
    private String description;

    /**
     * Default constructor required by JPA.
     * Should not be used directly in application logic.
     */
    public UserRole() {}

    /**
     * Constructs a new {@code UserRole} with the specified ID and description.
     *
     * @param id          Unique identifier for the role (e.g., "ADMIN").
     * @param description Description of the role's purpose.
     * @throws IllegalArgumentException if either ID or description is null or blank.
     */
    public UserRole(String id, String description) {
        if (id == null || id.isBlank() || description == null || description.isBlank()) {
            throw new IllegalArgumentException("UserRole id and/or description cannot be blank.");
        }
        this.id = id.trim().toUpperCase();
        this.description = description;
    }

    /**
     * Retrieves the role's identifier.
     *
     * @return Role ID in uppercase.
     */
    public String getId() {
        return id;
    }

    /**
     * Updates the role's identifier if the given value is valid.
     *
     * @param id New ID to assign (non-blank).
     */
    public void setId(String id) {
        if (id != null && !id.isBlank()) {
            this.id = id.trim().toUpperCase();
        }
    }

    /**
     * Retrieves the description of the role.
     *
     * @return Description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the role if valid.
     *
     * @param description New description (non-blank).
     */
    public void setDescription(String description) {
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
    }

    /**
     * Changes the description of the role.
     *
     * @param newDescription New description string.
     * @return True if the change was applied, false otherwise.
     */
    public boolean changeDescription(String newDescription) {
        if (newDescription == null || newDescription.isBlank()) {
            return false;
        }
        this.description = newDescription;
        return true;
    }

    /**
     * Checks if this role has the given ID (case-insensitive).
     *
     * @param id Role ID to compare.
     * @return True if it matches this role's ID, false otherwise.
     */
    public boolean hasId(String id) {
        return id != null && !id.isBlank() && this.id.equals(id.trim().toUpperCase());
    }

    /**
     * Computes the hash code based on the role ID.
     *
     * @return Hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id.hashCode();
        return hash;
    }

    /**
     * Compares this role with another object for equality based on ID.
     *
     * @param o Object to compare.
     * @return True if the roles have the same ID, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id);
    }

    /**
     * Returns a string representation of the role.
     *
     * @return Formatted string with ID and description.
     */
    @Override
    public String toString() {
        return String.format("%s - %s", id, description);
    }
}
