package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value Object that represents an Address.
 * This class encapsulates the details of a physical address, including street, city, postal code, and country.
 * The postal code must follow the Portuguese format: NNNN-NNN (4 digits, a hyphen, 3 digits).
 * This class is immutable and validates its fields to ensure consistency.
 *
 * @see ValueObject
 */
@Embeddable
public class Address implements ValueObject {

    private String street;
    private String city;
    private String postalCode;
    private String country;

    /**
     * Default constructor for JPA.
     */
    protected Address() {
        // for JPA
    }

    /**
     * Constructs a new Address object with the provided details.
     *
     * @param street The street address.
     * @param city The city.
     * @param postalCode The postal code (must match the Portuguese format NNNN-NNN).
     * @param country The country.
     * @throws IllegalArgumentException if any of the fields are null or empty, or if the postal code is invalid.
     */
    public Address(String street, String city, String postalCode, String country) {
        if (isNullOrEmpty(street) || isNullOrEmpty(city) || isNullOrEmpty(postalCode) || isNullOrEmpty(country)) {
            throw new IllegalArgumentException("All address fields must be provided and non-empty.");
        }
        if (!postalCode.matches("\\d{4}-\\d{3}")) {
            throw new IllegalArgumentException("Postal code must match Portuguese format: NNNN-NNN");
        }
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param s The string to check.
     * @return True if the string is null or empty, otherwise false.
     */
    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Retrieves the street part of the address.
     *
     * @return The street.
     */
    public String street() {
        return street;
    }

    /**
     * Retrieves the city part of the address.
     *
     * @return The city.
     */
    public String city() {
        return city;
    }

    /**
     * Retrieves the postal code part of the address.
     *
     * @return The postal code.
     */
    public String postalCode() {
        return postalCode;
    }

    /**
     * Retrieves the country part of the address.
     *
     * @return The country.
     */
    public String country() {
        return country;
    }

    /**
     * Returns a string representation of the address in the format:
     * "street, city, postalCode country".
     *
     * @return The string representation of the address.
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s %s", street, city, postalCode, country);
    }

    /**
     * Compares this address to another object to determine equality.
     * Two addresses are considered equal if all their fields (street, city, postalCode, country) are the same.
     *
     * @param o The object to compare to.
     * @return True if the addresses are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(country, address.country);
    }

    /**
     * Generates a hash code for the address based on its fields.
     *
     * @return The hash code of the address.
     */
    @Override
    public int hashCode() {
        return Objects.hash(street, city, postalCode, country);
    }
}
