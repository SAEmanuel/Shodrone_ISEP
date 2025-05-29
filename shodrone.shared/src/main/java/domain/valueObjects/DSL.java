package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public final class DSL implements ValueObject, Serializable {
    private static final long serialVersionUID = 1L;

    // Valid file name with extension: e.g., "input.txt"
    private static final Pattern FILE_NAME_REGEX = Pattern.compile("^[\\w\\-. ]+\\.[a-zA-Z0-9]+$");

    private final String fileName;

    /**
     * Constructor that validates if the string is a valid file name with an extension.
     *
     * @param fileName The input string.
     */
    public DSL(final String fileName) {
        Preconditions.nonEmpty(fileName, "File name should neither be null nor empty");
        String trimmed = fileName.trim();
        Preconditions.ensure(FILE_NAME_REGEX.matcher(trimmed).matches(), "Invalid file name format: " + trimmed);
        this.fileName = trimmed;
    }

    /**
     * Default constructor for JPA.
     */
    protected DSL() {
        this.fileName = null;
    }

    public static DSL valueOf(final String fileName) {
        return new DSL(fileName);
    }

    /**
     * Return the full file name string.
     */
    public String fileName() {
        return this.fileName;
    }

    /**
     * Returns just the extension (e.g., "txt").
     */
    public String extension() {
        int index = fileName.lastIndexOf('.');
        return index != -1 ? fileName.substring(index + 1).toLowerCase() : null;
    }

    @Override
    public String toString() {
        return this.fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DSL)) return false;
        DSL other = (DSL) o;
        return Objects.equals(this.fileName, other.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileName);
    }
}
