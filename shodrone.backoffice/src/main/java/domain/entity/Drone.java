package domain.entity;

import domain.valueObjects.DroneRemovalLog;
import domain.valueObjects.DroneStatus;
import domain.valueObjects.SerialNumber;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import more.ListDisplayable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Drone implements AggregateRoot<String>, Serializable, ListDisplayable {

    /**
     * The serial number of the drone, serving as its unique identifier.
     */
    @Id
    private SerialNumber serialNumber;

    /**
     * The drone model associated with this drone.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "drone_model_id", nullable = false)
    private DroneModel droneModel;

    /**
     * The current status of the drone.
     */
    @Enumerated(EnumType.STRING)
    private DroneStatus droneStatus;

    /**
     * The list of removal logs for the drone.
     */
    @ElementCollection
    @CollectionTable(name = "drone_removal_logs", joinColumns = @JoinColumn(name = "drone_serial_number"))
    private List<DroneRemovalLog> droneRemovalLogs;

    /**
     * Default constructor required for JPA.
     *
     * @return void
     */
    protected Drone() {
    }

    /**
     * Constructs a Drone with the specified serial number and drone model.
     *
     * @param serialNumber the serial number of the drone
     * @param droneModel the drone model associated with the drone
     * @return void
     */
    public Drone(final SerialNumber serialNumber, final DroneModel droneModel) {
        try {
            Preconditions.nonEmpty(serialNumber.getSerialNumber(), "Model ID should neither be null nor empty");
            Preconditions.nonEmpty(droneModel.identity(), "Drone Model ID should neither be null nor empty");

            this.serialNumber = serialNumber;
            this.droneModel = droneModel;
            this.droneStatus = DroneStatus.AVAILABLE;
            this.droneRemovalLogs = new ArrayList<>();

        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Retrieves the identity of the Drone.
     *
     * @return the serial number as a String
     */
    @Override
    public String identity() {
        return this.serialNumber.toString();
    }

    /**
     * Retrieves the drone model of the Drone.
     *
     * @return the drone model as a DroneModel object
     */
    public DroneModel droneModel() {
        return this.droneModel;
    }

    /**
     * Retrieves the status of the Drone.
     *
     * @return the drone status as a DroneStatus enum
     */
    public DroneStatus droneStatus() {
        return this.droneStatus;
    }

    /**
     * Changes the status of the Drone to a new status.
     *
     * @param newDroneStatus the new status to set
     * @return void
     */
    public void changeDroneStatusTo(final DroneStatus newDroneStatus) {
        this.droneStatus = newDroneStatus;
    }

    /**
     * Adds a removal log to the Drone's list of removal logs.
     *
     * @param newDroneRemovalLog the removal log to add
     * @return void
     */
    public void addDroneRemovalLog(final DroneRemovalLog newDroneRemovalLog) {
        this.droneRemovalLogs.add(newDroneRemovalLog);
    }

    /**
     * Retrieves the list of removal logs for the Drone.
     *
     * @return a List of DroneRemovalLog objects
     */
    public List<DroneRemovalLog> droneRemovalLogs() {
        return this.droneRemovalLogs;
    }

    /**
     * Checks if the Drone has the specified identity.
     *
     * @param id the ID to check against
     * @return true if the Drone's serial number matches the specified ID (case-insensitive), false otherwise
     */
    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.serialNumber.toString());
    }

    /**
     * Checks if this Drone is the same as another object.
     *
     * @param other the object to compare with
     * @return true if the objects are the same Drone based on identity, model, and status, false otherwise
     */
    @Override
    public boolean sameAs(Object other) {
        final Drone drone = (Drone) other;
        return this.equals(drone) && droneModel().equals(drone.droneModel()) && droneStatus() == drone.droneStatus();
    }

    /**
     * Computes the hash code of the Drone.
     *
     * @return the hash code value for this Drone
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Checks if this Drone is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * Returns a string representation of the Drone with formatted fields.
     *
     * @return a formatted String containing the Drone's serial number, model, name, and status
     */
    @Override
    public String toString() {
        return "\n📦 Drone Information" +
                "\n────────────────────────────" +
                "\n🔢 Serial Number : " + serialNumber +
                "\n📄 Model         : " + (droneModel != null ? droneModel.identity() : "N/A") +
                "\n🏷️ Name          : " + (droneModel != null ? droneModel.droneName().name() : "N/A") +
                "\n📶 Status        : " + droneStatus +
                "\n────────────────────────────";
    }

    /**
     * Returns a concise string representation of the drone for list display.
     *
     * @return a string with serial number, model, and name of the drone
     */
    public String toListString() {
        return "🔢 Serial Number: " + serialNumber +
                " | 📄 Model: " + (droneModel != null ? droneModel.identity() : "N/A") +
                " | 🏷️ Name: " + (droneModel != null ? droneModel.droneName().name() : "N/A");
    }
}