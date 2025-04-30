package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address implements ValueObject {

    private String street;
    private String city;
    private String postalCode;
    private String country;

    protected Address() {
        // for JPA
    }

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

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public String street() {
        return street;
    }

    public String city() {
        return city;
    }

    public String postalCode() {
        return postalCode;
    }

    public String country() {
        return country;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %s", street, city, postalCode, country);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(street, city, postalCode, country);
    }
}
