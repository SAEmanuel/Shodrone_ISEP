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

    @Column(unique = true)
    private final String name;

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


    protected DroneName() {
        this.name = null;
    }

    public static DroneName valueOf(final String name) {
        return new DroneName(name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String name() {
        return this.name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DroneName)) return false;
        DroneName other = (DroneName) o;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
