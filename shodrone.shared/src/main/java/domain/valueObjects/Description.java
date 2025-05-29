package domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.functional.Either;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.util.Objects;

/**
 * Value Object that represents a description.
 * This class ensures that the description adheres to specific validation rules:
 * - Cannot be null.
 * - Cannot be empty or only whitespace.
 * - Length must be between a minimum of 5 characters and a maximum of 300 characters.
 *
 * The description is stored as a trimmed string value.
 *
 * This class supports both a standard constructor and a factory method with error handling via Either.
 */
@Embeddable
public class Description implements ValueObject, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 300;

    @Column(name = "description")
    @XmlAttribute
    @JsonProperty("description")
    private final String value;

    /**
     * Constructs a Description with the specified value.
     *
     * @param value The description text.
     * @throws IllegalArgumentException if the value is null, empty, or doesn't meet the length requirements.
     */
    public Description(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty or only whitespace");
        }
        if (trimmed.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Description must have at least " + MIN_LENGTH + " characters");
        }
        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Description must have at most " + MAX_LENGTH + " characters");
        }
        this.value = trimmed;
    }

    /**
     * Default constructor for ORM frameworks.
     */
    protected Description() {
        // for ORM
        value = null;
    }

    /**
     * Factory method that creates a Description object or returns an error message.
     *
     * @param value The description text.
     * @return Either a left containing an error message or a right containing a valid Description object.
     */
    public static Description valueOf(final String value) {
        return tryValueOf(value).rightValueOrElseThrow(IllegalArgumentException::new);
    }

    /**
     * Factory method that tries to create a Description object or returns an error message.
     *
     * @param value The description text.
     * @return Either a left containing an error message or a right containing a valid Description object.
     */
    public static Either<String, Description> tryValueOf(final String value) {
        if (value == null)
            return Either.left("Description cannot be null");
        String trimmed = value.trim();
        if (trimmed.isEmpty())
            return Either.left("Description cannot be empty or only whitespace");
        if (trimmed.length() < MIN_LENGTH)
            return Either.left("Description must have at least " + MIN_LENGTH + " characters");
        if (trimmed.length() > MAX_LENGTH)
            return Either.left("Description must have at most " + MAX_LENGTH + " characters");
        return Either.right(new Description(trimmed));
    }

    /**
     * Returns the length of the description.
     *
     * @return The length of the description.
     */
    @Override
    public int length() {
        return value.length();
    }

    /**
     * Returns the string representation of the description.
     *
     * @return The description value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this description to another object for equality.
     * Two descriptions are considered equal if their string values are the same.
     *
     * @param o The object to compare to.
     * @return True if the descriptions are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description)) return false;
        Description that = (Description) o;
        return Objects.equals(value, that.value);
    }

    /**
     * Generates a hash code for the description based on its value.
     *
     * @return The hash code of the description.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
