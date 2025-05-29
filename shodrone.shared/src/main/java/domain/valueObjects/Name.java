package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public final class Name implements ValueObject, Serializable {
    private static final long serialVersionUID = 1L;

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 80;
    private static final Pattern VALID_NAME_REGEX = Pattern.compile("^[\\pL\\pM\\p{Nl}][\\pL\\pM\\p{Nl} ',.\\-]*$", Pattern.UNICODE_CASE);

    @Column(unique = true)
    private final String name;

    /**
     * Constructor that validates the provided name.
     *
     * @param name The name value.
     * @throws IllegalArgumentException if the name is invalid.
     */
    public Name(final String name) {
        try {
            Preconditions.nonEmpty(name, "Name should neither be null nor empty");
            String trimmed = name.trim();
            Preconditions.ensure(!trimmed.isEmpty(), "Name cannot be only whitespace");
            Preconditions.ensure(trimmed.length() >= MIN_LENGTH, "Name must have at least " + MIN_LENGTH + " characters");
            Preconditions.ensure(trimmed.length() <= MAX_LENGTH, "Name must have at most " + MAX_LENGTH + " characters");
            Preconditions.matches(VALID_NAME_REGEX, trimmed, "Invalid Name: " + trimmed);
            this.name = trimmed;
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    /**
     * Default constructor for JPA.
     */
    protected Name() {
        this.name = null;
    }

    /**
     * Factory method to create a new Name object.
     *
     * @param name The name value.
     * @return A new Name instance.
     */
    public static Name valueOf(final String name) {
        return new Name(name);
    }

    /**
     * Returns the name as a string.
     *
     * @return The name value.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Getter for the name field.
     *
     * @return The name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Compares this Name object to another object for equality.
     *
     * @param o The object to compare with.
     * @return True if the names are equal, otherwise false.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name other = (Name) o;
        return Objects.equals(this.name, other.name);
    }

    /**
     * Generates a hash code for this Name object based on the name value.
     *
     * @return The hash code for this Name object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
