package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public class SerialNumber implements ValueObject {

    private static final Pattern VALID_SERIAL_NUMBER_REGEX = Pattern.compile("^SN-\\d{5}$");
    private static final int LENGTH = 8;

    /**
     * The serial number string of the SerialNumber.
     */
    private final String serialNumber;

    /**
     * Constructs a SerialNumber with the specified serial number string.
     *
     * @param serialNumber the serial number string to set
     * @return void
     */
    public SerialNumber(final String serialNumber) {
        try {
            Preconditions.nonEmpty(serialNumber, "Serial Number should neither be null nor empty");

            String serialNumberTrimmed = serialNumber.trim();
            Preconditions.ensure(!serialNumberTrimmed.isEmpty(), "Serial Number cannot be only whitespace");
            Preconditions.ensure(serialNumberTrimmed.length() == LENGTH, "Serial Number must have exactly " + LENGTH + " characters");
            Preconditions.matches(VALID_SERIAL_NUMBER_REGEX, serialNumberTrimmed, "Invalid Serial Number: " + serialNumberTrimmed);

            this.serialNumber = serialNumberTrimmed;
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    /**
     * Default constructor required for JPA.
     *
     * @return void
     */
    protected SerialNumber() {
        serialNumber = null;
    }

    /**
     * Retrieves the serial number of the SerialNumber.
     *
     * @return the serial number as a String
     */
    public String getSerialNumber() {
        return this.serialNumber;
    }

    /**
     * Returns the string representation of the SerialNumber.
     *
     * @return the serial number as a String
     */
    @Override
    public String toString() {
        return this.serialNumber;
    }

    /**
     * Checks if this SerialNumber is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerialNumber)) return false;
        SerialNumber id = (SerialNumber) o;
        return serialNumber.equals(id.serialNumber);
    }

    /**
     * Computes the hash code of the SerialNumber.
     *
     * @return the hash code value for this SerialNumber
     */
    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }
}