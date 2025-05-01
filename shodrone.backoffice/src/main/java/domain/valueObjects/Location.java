package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

import java.util.Objects;

@Embeddable
public class Location implements ValueObject {

    @Embedded
    private Address address;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(length = 300,name = "location_description")
    private String description;

    protected Location() {
        // For JPA
    }

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

    public Address address() { return address; }
    public double latitude() { return latitude; }
    public double longitude() { return longitude; }
    public String description() { return description; }

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

    @Override
    public int hashCode() {
        return Objects.hash(address, latitude, longitude, description);
    }
}
