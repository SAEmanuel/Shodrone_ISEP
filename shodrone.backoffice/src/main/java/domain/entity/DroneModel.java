package domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.Name;
import domain.valueObjects.WindTolerance;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
public class DroneModel implements AggregateRoot<String>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @XmlElement
    @JsonProperty
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 50)
    private DroneModelID droneModelID;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private Name droneName;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private Description description;

    @XmlElement
    @JsonProperty
    @ElementCollection
    private List<WindTolerance> windTolerances;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private int maxWindSpeed;


    protected DroneModel() {}

    public DroneModel (DroneModelID droneModelID, Name droneName, Description description, int maxWindSpeed) {
        if (maxWindSpeed < 0) {
            throw new IllegalArgumentException("Max Wind Speed cannot be negative");
        }

        this.droneModelID = droneModelID;
        this.droneName = droneName;
        this.description = description;
        this.maxWindSpeed = maxWindSpeed;
        this.windTolerances = null;
    }

    public DroneModel (DroneModelID droneModelID, Name droneName, int maxWindSpeed) {
        if (maxWindSpeed < 0) {
            throw new IllegalArgumentException("Max Wind Speed cannot be negative");
        }

        this.droneModelID = droneModelID;
        this.droneName = droneName;
        this.description = new Description("Description not provided!");
        this.maxWindSpeed = maxWindSpeed;
        this.windTolerances = new ArrayList<>();
    }


    public void changeDescriptionTo(final Description newDescription) {
        if (newDescription == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.description = newDescription;
    }

    public void changeDroneNameTo(final Name newDroneName) {
        if (newDroneName == null) {
            throw new IllegalArgumentException("Drone Name cannot be null.");
        }
        this.droneName = newDroneName;
    }

    public void changeMaxWindSpeedTo(final int newMaxWindSpeed) {
        if (newMaxWindSpeed < 0) {
            throw new IllegalArgumentException("Max Wind Speed cannot be negative");
        }
        this.maxWindSpeed = newMaxWindSpeed;
    }

    public void addWindTolerance(final WindTolerance newWindTolerance) {
        if (newWindTolerance == null) {
            throw new IllegalArgumentException("Wind Tolerance cannot be null.");
        }
        this.windTolerances.add(newWindTolerance);
    }

    @Override
    public String identity() {
        return this.droneModelID.toString();
    }

    public Name droneName() {
        return this.droneName;
    }

    public Description description() {
        return this.description;
    }

    public int maxWindSpeed() {
        return this.maxWindSpeed;
    }

    public List<WindTolerance> windTolerances() {
        return this.windTolerances;
    }

    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.droneModelID.toString());
    }

    @Override
    public boolean sameAs(Object other) {
        final DroneModel droneModel = (DroneModel) other;
        return this.equals(droneModel) && droneName().equals(droneModel.droneName()) && description().equals(droneModel.description()) && maxWindSpeed() == droneModel.maxWindSpeed();
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DroneModel {\n");
        sb.append("  ID: ").append(droneModelID).append(",\n");
        sb.append("  Name: ").append(droneName).append(",\n");
        sb.append("  Description: ").append(description).append(",\n");
        sb.append("  Max Wind Speed: ").append(maxWindSpeed).append(" km/h,\n");
        sb.append("  Wind Tolerances: [\n");

        if (windTolerances != null && !windTolerances.isEmpty()) {
            for (WindTolerance wt : windTolerances) {
                sb.append("    ").append(wt).append(",\n");
            }
        } else {
            sb.append("    None\n");
        }

        sb.append("  ]\n");
        sb.append("}");
        return sb.toString();
    }



















}
