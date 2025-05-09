package authz;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @EmbeddedId
    private Email id;

    @Embedded
    private Password password;

    private String name;

    @Column(name = "ACTIVE")
    private boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> roles = new HashSet<>();

    public User() {}

    public User(Email id, Password password, String name) {
        if (id == null || password == null || name == null || name.isBlank()) {
            throw new IllegalArgumentException("User cannot have an id, password or name as null/blank.");
        }
        this.id = id;
        this.password = password;
        this.name = name.trim();
        this.active = true;
    }

    public Email getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public boolean hasId(Email id) {
        return this.id.equals(id);
    }

    public boolean hasPassword(String pwd) {
        return this.password.checkPassword(pwd);
    }

    public Password getPassword() {
        return this.password;
    }

    public boolean addRole(UserRole role) {
        return role != null && this.roles.add(role);
    }

    public boolean removeRole(UserRole role) {
        return role != null && this.roles.remove(role);
    }

    public boolean hasRole(UserRole role) {
        return this.roles.contains(role);
    }

    public boolean hasRole(String roleId) {
        for (UserRole role : this.roles) {
            if (role.hasId(roleId)) {
                return true;
            }
        }
        return false;
    }

    public List<UserRole> getRoles() {
        return Collections.unmodifiableList(new ArrayList<>(roles));
    }

    public boolean changeName(String newName) {
        if (newName == null || newName.isBlank()) {
            return false;
        }
        this.name = newName.trim();
        return true;
    }

    public boolean changeRoles(List<UserRole> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
        return this.roles.containsAll(roles);
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", id.toString(), name);
    }
}
