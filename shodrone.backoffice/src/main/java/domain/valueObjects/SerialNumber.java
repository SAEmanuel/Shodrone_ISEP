package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import java.util.regex.Pattern;

public class SerialNumber implements ValueObject {

    private static final Pattern VALID_SERIAL_NUMBER_REGEX = Pattern.compile("^SN-\\d{5}$");
    private static final int LENGTH = 8;

    private final String serialNumber;

    public SerialNumber(final String serialNumber) {

        try {
            Preconditions.nonEmpty(serialNumber, "Serial Number should neither be null nor empty");

            String serialNumberTrimmed = serialNumber.trim();
            Preconditions.ensure(!serialNumberTrimmed.isEmpty(), "Serial Number cannot be only whitespace");
            Preconditions.ensure(serialNumberTrimmed.length() == LENGTH, "Serial Number must have exactly " + LENGTH + " characters");
            Preconditions.matches(VALID_SERIAL_NUMBER_REGEX, serialNumberTrimmed, "Invalid Serial Number: " + serialNumberTrimmed);

            this.serialNumber = serialNumberTrimmed;
        }
        catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    protected SerialNumber() {
        serialNumber = null;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    @Override
    public String toString() {
        return this.serialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerialNumber)) return false;
        SerialNumber id = (SerialNumber) o;
        return serialNumber.equals(id.serialNumber);
    }

    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }








}
