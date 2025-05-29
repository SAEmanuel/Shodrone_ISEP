package domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.valueObjects.*;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import more.ColorfulOutput;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.*;

/**
 * Represents a Drone Model entity with attributes such as ID, name, description, wind tolerances, and maximum wind speed.
 */
@XmlRootElement
@Entity
public class DroneModel implements AggregateRoot<String>, Serializable {

    @Id
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 50)
    private DroneModelID droneModelID;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private DroneName droneName;

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
    private double maxWindSpeed;

    /**
     * Default constructor required for JPA.
     *
     * @return void
     */
    protected DroneModel() {}

    /**
     * Constructs a DroneModel with a description.
     *
     * @param droneModelID the ID of the drone model
     * @param droneName the name of the drone model
     * @param description the description of the drone model
     * @param maxWindSpeed the maximum wind speed the drone model can handle (in m/s)
     * @return void
     */
    public DroneModel(DroneModelID droneModelID, DroneName droneName, Description description, double maxWindSpeed) {
        try {
            Preconditions.nonEmpty(droneModelID.getModelID());
            Preconditions.nonEmpty(droneName.name());
            Preconditions.nonEmpty(description.toString());
            Preconditions.nonNull(maxWindSpeed);
            Preconditions.ensure(maxWindSpeed > 0);

            this.droneModelID = droneModelID;
            this.droneName = droneName;
            this.description = description;
            this.maxWindSpeed = maxWindSpeed;
            this.windTolerances = null;

        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Constructs a DroneModel without a description, using a default description.
     *
     * @param droneModelID the ID of the drone model
     * @param droneName the name of the drone model
     * @param maxWindSpeed the maximum wind speed the drone model can handle (in m/s)
     * @return void
     */
    public DroneModel(DroneModelID droneModelID, DroneName droneName, double maxWindSpeed) {
        try {
            Preconditions.nonEmpty(droneModelID.getModelID());
            Preconditions.nonEmpty(droneName.name());
            Preconditions.nonNull(maxWindSpeed);
            Preconditions.ensure(maxWindSpeed > 0);

            this.droneModelID = droneModelID;
            this.droneName = droneName;
            this.description = new Description("Description not provided!");
            this.maxWindSpeed = maxWindSpeed;
            this.windTolerances = new ArrayList<>();

        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Changes the description of the DroneModel.
     *
     * @param newDescription the new description to set
     * @return void
     */
    public void changeDescriptionTo(final Description newDescription) {
        if (newDescription == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.description = newDescription;
    }

    /**
     * Changes the name of the DroneModel.
     *
     * @param newDroneName the new name to set
     * @return void
     */
    public void changeDroneNameTo(final DroneName newDroneName) {
        if (newDroneName == null) {
            throw new IllegalArgumentException("Drone Name cannot be null.");
        }
        this.droneName = newDroneName;
    }

    /**
     * Changes the maximum wind speed of the DroneModel.
     *
     * @param newMaxWindSpeed the new maximum wind speed to set (in m/s)
     * @return void
     */
    public void changeMaxWindSpeedTo(final int newMaxWindSpeed) {
        if (newMaxWindSpeed < 0) {
            throw new IllegalArgumentException("Max Wind Speed cannot be negative");
        }
        this.maxWindSpeed = newMaxWindSpeed;
    }

    /**
     * Adds a WindTolerance to the DroneModel's list of wind tolerances.
     *
     * @param newWindTolerance the WindTolerance to add
     * @return void
     */
    public void addWindTolerance(final WindTolerance newWindTolerance) {
        if (newWindTolerance == null) {
            throw new IllegalArgumentException("Wind Tolerance cannot be null.");
        }
        this.windTolerances.add(newWindTolerance);
    }

    /**
     * Retrieves the identity of the DroneModel.
     *
     * @return the DroneModel's ID as a String
     */
    @Override
    public String identity() {
        return this.droneModelID.toString();
    }

    /**
     * Retrieves the name of the DroneModel.
     *
     * @return the DroneModel's name as a DroneName object
     */
    public DroneName droneName() {
        return this.droneName;
    }

    /**
     * Retrieves the description of the DroneModel.
     *
     * @return the DroneModel's description as a Description object
     */
    public Description description() {
        return this.description;
    }

    /**
     * Retrieves the maximum wind speed of the DroneModel.
     *
     * @return the maximum wind speed (in m/s)
     */
    public double maxWindSpeed() {
        return this.maxWindSpeed;
    }

    /**
     * Retrieves the list of wind tolerances for the DroneModel.
     *
     * @return a List of WindTolerance objects
     */
    public List<WindTolerance> windTolerances() {
        return this.windTolerances;
    }

    /**
     * Checks if the DroneModel has the specified identity.
     *
     * @param id the ID to check against
     * @return true if the DroneModel's ID matches the specified ID (case-insensitive), false otherwise
     */
    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.droneModelID.toString());
    }

    /**
     * Checks if this DroneModel is the same as another object.
     *
     * @param other the object to compare with
     * @return true if the objects are the same DroneModel based on identity, name, description, and max wind speed, false otherwise
     */
    @Override
    public boolean sameAs(Object other) {
        final DroneModel droneModel = (DroneModel) other;
        return this.equals(droneModel) && droneName().equals(droneModel.droneName()) && description().equals(droneModel.description()) && maxWindSpeed() == droneModel.maxWindSpeed();
    }

    /**
     * Computes the hash code of the DroneModel.
     *
     * @return the hash code value for this DroneModel
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Checks if this DroneModel is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * Returns a string representation of the DroneModel with formatted fields.
     *
     * @return a formatted String containing the DroneModel's ID, name, description, and max wind speed
     */
    @Override
    public String toString() {
        final int ID_LIMIT = 20;
        final int NAME_LIMIT = 25;
        final int DESC_LIMIT = 35;

        String idTrunc = truncate(droneModelID.toString(), ID_LIMIT);
        String nameTrunc = truncate(droneName.name(), NAME_LIMIT);
        String descTrunc = truncate(description.toString(), DESC_LIMIT);

        return String.format(
                "%s%s%-2s%s: %s%-20s%s | %s%s%-4s%s: %s%-25s%s | %s%s%-11s%s: %s%-35s%s | %s%s%-8s%s: %s%3.2f m/s%s",
                ColorfulOutput.ANSI_ORANGE, ColorfulOutput.ANSI_BOLD, "ID", ColorfulOutput.ANSI_RESET, ColorfulOutput.ANSI_BRIGHT_WHITE, idTrunc, ColorfulOutput.ANSI_RESET,
                ColorfulOutput.ANSI_ORANGE, ColorfulOutput.ANSI_BOLD, "Name", ColorfulOutput.ANSI_RESET, ColorfulOutput.ANSI_BRIGHT_WHITE, nameTrunc, ColorfulOutput.ANSI_RESET,
                ColorfulOutput.ANSI_ORANGE, ColorfulOutput.ANSI_BOLD, "Description", ColorfulOutput.ANSI_RESET, ColorfulOutput.ANSI_BRIGHT_WHITE, descTrunc, ColorfulOutput.ANSI_RESET,
                ColorfulOutput.ANSI_ORANGE, ColorfulOutput.ANSI_BOLD, "Max Wind", ColorfulOutput.ANSI_RESET, ColorfulOutput.ANSI_BRIGHT_WHITE, maxWindSpeed, ColorfulOutput.ANSI_RESET
        );
    }

    /**
     * Truncates a string to a specified length, adding ellipsis if necessary.
     *
     * @param text the text to truncate
     * @param maxLength the maximum length of the text
     * @return the truncated text, with ellipsis if it exceeds maxLength
     */
    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }
}