package domain.entity;

import domain.valueObjects.DroneStatus;
import domain.valueObjects.SerialNumber;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Drone implements AggregateRoot<String>, Serializable {

    @Id
    private SerialNumber serialNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "drone_model_id", nullable = false)
    private DroneModel droneModel;

    @Enumerated(EnumType.STRING)
    private DroneStatus droneStatus;

    protected Drone() {}

    public Drone(SerialNumber serialNumber, DroneModel droneModel) {
        this.serialNumber = serialNumber;
        this.droneModel = droneModel;
        this.droneStatus = DroneStatus.AVAILABLE;
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
                "\n📶 Status        : " + droneStatus +
                "\n────────────────────────────";
    }
    
}
