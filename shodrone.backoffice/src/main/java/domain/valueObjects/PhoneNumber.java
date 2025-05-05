package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class PhoneNumber implements ValueObject {

    private static final Pattern PORTUGUESE_PHONE_PATTERN = Pattern.compile("^(9[1236]\\d{7}|2\\d{8})$");

    private String number;

    // Default constructor for JPA
    protected PhoneNumber() {
    }

    /**
     * Constructor to create a valid phone number.
     *
     * @param number The phone number to be validated.
     * @throws IllegalArgumentException if the number is invalid.
     */
    public PhoneNumber(String number) {
        if (!isValidPortugueseNumber(number)) {
            throw new IllegalArgumentException("Invalid Portuguese phone number: " + number);
        }
        this.number = number;
    }

    /**
     * Validates a Portuguese phone number.
     *
     * @param number The phone number to validate.
     * @return True if the number is valid, otherwise false.
     */
    private boolean isValidPortugueseNumber(String number) {
        return number != null && PORTUGUESE_PHONE_PATTERN.matcher(number).matches();
    }

    /**
     * Gets the phone number.
     *
     * @return The phone number as a String.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Formats the phone number as an international number with the Portuguese country code.
     *
     * @return The formatted phone number as a String in the format +351 xxx xxx xxx.
     */
    public String formatInternational() {
        if (number == null) return "";
        return "+351 " + number.substring(0, 3) + " " + number.substring(3, 6) + " " + number.substring(6);
    }

    /**
     * Returns the phone number as a string.
     *
     * @return The phone number.
     */
    @Override
    public String toString() {
        return number;
    }

    /**
     * Compares this PhoneNumber object with another for equality.
     *
     * @param o The object to compare with.
     * @return True if the phone numbers are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(number, that.number);
    }

    /**
     * Generates a hash code for the PhoneNumber object.
     *
     * @return The hash code for this PhoneNumber object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
