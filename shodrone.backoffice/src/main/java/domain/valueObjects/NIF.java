package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NIF implements ValueObject {

    private String nif;

    // Default constructor for JPA
    protected NIF() {
    }

    /**
     * Constructor to create a valid NIF object.
     *
     * @param number The NIF number (should be 9 digits).
     * @throws IllegalArgumentException if the NIF is invalid.
     */
    public NIF(String number) {
        if (!isValidNIF(number)) {
            throw new IllegalArgumentException("Invalid Portuguese NIF: " + number);
        }
        this.nif = number;
    }

    /**
     * Validates a Portuguese NIF.
     *
     * @param nif The NIF number to validate.
     * @return True if the NIF is valid, otherwise false.
     */
    private boolean isValidNIF(String nif) {
        if (nif == null || !nif.matches("\\d{9}")) return false;

        int firstDigit = Character.getNumericValue(nif.charAt(0));
        // The first digit must be one of the allowed values.
        if (!(firstDigit == 1 || firstDigit == 2 || firstDigit == 3 || firstDigit == 5 || firstDigit == 6 || firstDigit == 8 || firstDigit == 9)) {
            return false;
        }

        // Checksum validation: sum the digits from left to right.
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += Character.getNumericValue(nif.charAt(i)) * (9 - i);
        }

        // The last digit is a check digit.
        int checkDigit = 11 - (sum % 11);
        if (checkDigit >= 10) checkDigit = 0;

        // Validate the check digit.
        return checkDigit == Character.getNumericValue(nif.charAt(8));
    }

    /**
     * Returns the NIF value.
     *
     * @return The NIF number as a String.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Returns the NIF as a string.
     *
     * @return The NIF number.
     */
    @Override
    public String toString() {
        return nif;
    }

    /**
     * Compares this NIF object with another for equality.
     *
     * @param o The object to compare with.
     * @return True if the NIFs are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NIF)) return false;
        NIF nif = (NIF) o;
        return this.nif.equals(nif.nif);
    }

    /**
     * Generates a hash code for the NIF object.
     *
     * @return The hash code for this NIF object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nif);
    }
}
