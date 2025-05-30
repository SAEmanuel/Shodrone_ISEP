package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public class MaintenanceTypeName implements ValueObject {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 80;
    private static final Pattern VALID_MAINTENANCE_REGEX = Pattern.compile("^[\\pL\\pM\\p{Nl}\\p{Nd}][\\pL\\pM\\p{Nl}\\p{Nd} ',.\\-]*$", Pattern.UNICODE_CASE);

    @Column(unique = true)
    private final String name;


    public MaintenanceTypeName(final String name) {
        try {
            Preconditions.nonEmpty(name, "Maintenance Type Name should neither be null nor empty");
            String trimmed = name.trim();
            Preconditions.ensure(!trimmed.isEmpty(), "Maintenance Type Name cannot be only whitespace");
            Preconditions.ensure(trimmed.length() >= MIN_LENGTH, "Maintenance Type Name must have at least " + MIN_LENGTH + " characters");
            Preconditions.ensure(trimmed.length() <= MAX_LENGTH, "Maintenance Type Name must have at most " + MAX_LENGTH + " characters");
            Preconditions.matches(VALID_MAINTENANCE_REGEX, trimmed, "Invalid Maintenance Type Name: " + trimmed);
            this.name = trimmed;
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    protected MaintenanceTypeName() {
       this.name = null;
    }

    public static MaintenanceTypeName valueOf(final String name) {
        return new MaintenanceTypeName(name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String name() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaintenanceTypeName)) return false;
        MaintenanceTypeName other = (MaintenanceTypeName) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
