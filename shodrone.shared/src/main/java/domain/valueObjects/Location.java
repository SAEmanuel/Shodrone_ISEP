package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

import java.util.Objects;

/**
 * Value Object that represents a location with an address, latitude, longitude, and an optional description.
 * The location includes the following validation:
 * - The latitude must be between -90 and 90.
 * - The longitude must be between -180 and 180.
 * - The address cannot be null.
 * - The description is optional but cannot be blank.
 *
 * The address is embedded in this class, and the latitude, longitude, and description are stored as basic properties.
 */
@Embeddable
public class Location implements ValueObject {

    @Embedded
    private Address address;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(length = 300, name = "location_description")
    private String description;

    /**
     * Default constructor for JPA.
     */
    protected Location() {
        // For JPA
    }

    /**
     * Constructs a Location object with the specified address, latitude, longitude, and description.
     *
     * @param address The address of the location.
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param description The optional description of the location.
     * @throws IllegalArgumentException if the address is null, latitude is out of range, or longitude is out of range.
     */
    public Location(Address address, double latitude, double longitude, String description) {
        if (address == null) throw new IllegalArgumentException("Address cannot be null.");
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description != null && !description.isBlank() ? description.trim() : null;
    }

    /**
     * Returns the address of the location.
     *
     * @return The address of the location.
     */
    public Address address() {
        return address;
    }

    /**
     * Returns the latitude of the location.
     *
     * @return The latitude of the location.
     */
    public double latitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the location.
     *
     * @return The longitude of the location.
     */
    public double longitude() {
        return longitude;
    }

    /**
     * Returns the description of the location, if present.
     *
     * @return The description of the location.
     */
    public String description() {
        return description;
    }

    /**
     * Returns a string representation of the location in the format:
     *
     * "[address] | Lat: [latitude] | Lon: [longitude] | [description]"
     *
     * @return The string representation of the location.
     */
    @Override
    public String toString() {
        return String.format(
                "%s | Lat: %.6f | Lon: %.6f%s",
                address.toString(),
                latitude,
                longitude,
                description != null ? " | " + description : ""
        );
    }

    /**
     * Compares this location to another object for equality.
     *
     * @param o The object to compare to.
     * @return True if the locations are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location that = (Location) o;
        return Double.compare(latitude, that.latitude) == 0 &&
                Double.compare(longitude, that.longitude) == 0 &&
                Objects.equals(address, that.address) &&
                Objects.equals(description, that.description);
    }

    /**
     * Generates a hash code for the location based on its address, latitude, longitude, and description.
     *
     * @return The hash code of the location.
     */
    @Override
    public int hashCode() {
        return Objects.hash(address, latitude, longitude, description);
    }
}
