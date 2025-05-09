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

    @Id
    private SerialNumber serialNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "drone_model_id", nullable = false)
    private DroneModel droneModel;

    @Enumerated(EnumType.STRING)
    private DroneStatus droneStatus;

    @ElementCollection
    @CollectionTable(name = "drone_removal_logs", joinColumns = @JoinColumn(name = "drone_serial_number"))
    private List<DroneRemovalLog> droneRemovalLogs;


    protected Drone() {
    }

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

    @Override
    public String identity() {
        return this.serialNumber.toString();
    }

    public DroneModel droneModel() {
        return this.droneModel;
    }

    public DroneStatus droneStatus() {
        return this.droneStatus;
    }

    public void changeDroneStatusTo(final DroneStatus newDroneStatus) {
        this.droneStatus = newDroneStatus;
    }

    public void addDroneRemovalLog(final DroneRemovalLog newDroneRemovalLog) {
        this.droneRemovalLogs.add(newDroneRemovalLog);
    }

    public List<DroneRemovalLog> droneRemovalLogs() {
        return this.droneRemovalLogs;
    }

    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.serialNumber.toString());
    }

    @Override
    public boolean sameAs(Object other) {
        final Drone drone = (Drone) other;
        return this.equals(drone) && droneModel().equals(drone.droneModel()) && droneStatus() == drone.droneStatus();
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
