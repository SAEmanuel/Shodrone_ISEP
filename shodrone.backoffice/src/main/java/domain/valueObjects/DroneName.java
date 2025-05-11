package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public final class DroneName implements ValueObject, Serializable {
    private static final long serialVersionUID = 1L;

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 80;
    private static final Pattern VALID_DRONE_REGEX = Pattern.compile("^[\\pL\\pM\\p{Nl}\\p{Nd}][\\pL\\pM\\p{Nl}\\p{Nd} ',.\\-]*$", Pattern.UNICODE_CASE);

    /**
     * The name of the drone, stored as a unique string.
     */
    @Column(unique = true)
    private final String name;

    /**
     * Constructs a DroneName with the specified name string.
     *
     * @param name the name string to set
     * @return void
     */
    public DroneName(final String name) {
        try {
            Preconditions.nonEmpty(name, "Drone Name should neither be null nor empty");
            String trimmed = name.trim();
            Preconditions.ensure(!trimmed.isEmpty(), "Drone Name cannot be only whitespace");
            Preconditions.ensure(trimmed.length() >= MIN_LENGTH, "Drone Name must have at least " + MIN_LENGTH + " characters");
            Preconditions.ensure(trimmed.length() <= MAX_LENGTH, "Drone Name must have at most " + MAX_LENGTH + " characters");
            Preconditions.matches(VALID_DRONE_REGEX, trimmed, "Invalid Drone Name: " + trimmed);
            this.name = trimmed;
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    /**
     * Default constructor required for JPA.
     *
     * @return void
     */
    protected DroneName() {
        this.name = null;
    }

    /**
     * Creates a new DroneName instance from the specified name string.
     *
     * @param name the name string to create the DroneName from
     * @return a new DroneName instance
     */
    public static DroneName valueOf(final String name) {
        return new DroneName(name);
    }

    /**
     * Returns the string representation of the DroneName.
     *
     * @return the name as a String
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Retrieves the name of the DroneName.
     *
     * @return the name as a String
     */
    public String name() {
        return this.name;
    }

    /**
     * Checks if this DroneName is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DroneName)) return false;
        DroneName other = (DroneName) o;
        return Objects.equals(this.name, other.name);
    }

    /**
     * Computes the hash code of the DroneName.
     *
     * @return the hash code value for this DroneName
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}