package domain.entity;

import jakarta.persistence.*;
import java.util.*;

/**
 * Represents a user within the Shodrone system.
 * A user is uniquely identified by an email address and secured with a hashed password.
 * Users may have one or more roles, which determine their permissions within the system.
 * This entity supports enabling/disabling (active state), role management, and credential validation.
 */
@Entity
@Table(name = "users")
public class User {

    /** Unique identifier for the user, embedded as an Email object. */
    @EmbeddedId
    private Email id;

    /** Securely hashed password for authentication. */
    @Embedded
    private Password password;

    /** Full name of the user. */
    private String name;

    /** Indicates whether the user account is active. */
    @Column(name = "ACTIVE")
    private boolean active = true;

    /**
     * Set of roles assigned to the user, defining access permissions.
     * Mapped with eager fetching for quick access on login.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    /**
     * Default constructor required by JPA.
     * Should not be used directly in application logic.
     */
    public User() {}

    /**
     * Constructs a new user with the given email, password, and name.
     *
     * @param id       Email used as unique identifier.
     * @param password Password object (hashed internally).
     * @param name     User's full name.
     * @throws IllegalArgumentException if any argument is null or name is blank.
     */
    public User(Email id, Password password, String name) {
        if (id == null || password == null || name == null || name.isBlank()) {
            throw new IllegalArgumentException("User cannot have an id, password or name as null/blank.");
        }
        this.id = id;
        this.password = password;
        this.name = name.trim();
        this.active = true;
    }

    /**
     * Retrieves the unique identifier (email) of the user.
     *
     * @return Email object.
     */
    public Email getId() {
        return this.id;
    }

    /**
     * Retrieves the user's full name.
     *
     * @return Name string.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if this user has the specified email.
     *
     * @param id Email to compare.
     * @return True if it matches the user's email, false otherwise.
     */
    public boolean hasId(Email id) {
        return this.id.equals(id);
    }

    /**
     * Verifies if the provided password matches the stored hashed password.
     *
     * @param pwd Raw password to check.
     * @return True if passwords match, false otherwise.
     */
    public boolean hasPassword(String pwd) {
        return this.password.checkPassword(pwd);
    }

    /**
     * Retrieves the stored password object (hashed).
     *
     * @return Password object.
     */
    public Password getPassword() {
        return this.password;
    }

    /**
     * Adds a role to the user if not already assigned.
     *
     * @param role Role to add.
     * @return True if the role was added, false if already present or null.
     */
    public boolean addRole(UserRole role) {
        return role != null && this.roles.add(role);
    }

    /**
     * Removes a role from the user.
     *
     * @param role Role to remove.
     * @return True if the role was removed, false if not present or null.
     */
    public boolean removeRole(UserRole role) {
        return role != null && this.roles.remove(role);
    }

    /**
     * Checks if the user has a specific role object.
     *
     * @param role Role to check.
     * @return True if present, false otherwise.
     */
    public boolean hasRole(UserRole role) {
        return this.roles.contains(role);
    }

    /**
     * Checks if the user has a role by its ID.
     *
     * @param roleId Role ID to check.
     * @return True if a role with that ID is present, false otherwise.
     */
    public boolean hasRole(String roleId) {
        for (UserRole role : this.roles) {
            if (role.hasId(roleId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves an unmodifiable list of roles assigned to the user.
     *
     * @return List of roles.
     */
    public List<UserRole> getRoles() {
        return Collections.unmodifiableList(new ArrayList<>(roles));
    }

    /**
     * Changes the user's name, if valid.
     *
     * @param newName New name to assign.
     * @return True if changed successfully, false if invalid.
     */
    public boolean changeName(String newName) {
        if (newName == null || newName.isBlank()) {
            return false;
        }
        this.name = newName.trim();
        return true;
    }

    /**
     * Replaces the user's roles with a new list.
     *
     * @param roles New roles to assign.
     * @return True if roles were updated correctly.
     */
    public boolean changeRoles(List<UserRole> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
        return this.roles.containsAll(roles);
    }

    /**
     * Checks whether the user account is active.
     *
     * @return True if active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activates the user account.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Deactivates the user account.
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Computes a hash code based on the email (ID).
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
     * Checks equality between users based on email (ID).
     *
     * @param o Object to compare.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    public boolean passwordMatches(String rawPassword) {
        return this.password.checkPassword(rawPassword);
    }

    /**
     * Returns a string representation of the user, showing email and name.
     *
     * @return Formatted string.
     */
    @Override
    public String toString() {
        return String.format("%s - %s", id.toString(), name);
    }
}
