package more;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.functional.Either;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Description implements ValueObject, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 300;

    @Column(name = "description")
    @XmlAttribute
    @JsonProperty("description")
    private final String value;

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

    protected Description() {
        // for ORM
        value = null;
    }

    public static Description valueOf(final String value) {
        return tryValueOf(value).rightValueOrElseThrow(IllegalArgumentException::new);
    }

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

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description)) return false;
        Description that = (Description) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
