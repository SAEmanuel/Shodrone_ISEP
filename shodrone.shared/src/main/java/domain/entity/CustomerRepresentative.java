package domain.entity;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import domain.valueObjects.*;
import lombok.Getter;

/**
 * Represents a customer representative within the Shodrone system.
 * A representative is associated with a single {@link Costumer} and holds identifying and contact details,
 * including name, email, phone number, position, and active status.
 */
@Entity
public class CustomerRepresentative implements AggregateRoot<Long> {

    /** Unique identifier for the representative (auto-generated). */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    /** Reference to the associated customer entity. */
    @ManyToOne(cascade = CascadeType.MERGE,optional = false)
    @JoinColumn(name = "costumer_id", nullable = false)
    private Costumer costumer;

    @Embedded
    private Name name;

    /** Email address of the representative.
     * -- GETTER --
     *  Returns the email of the representative.
     *
     * @return Email object.
     */
    @Getter
    @Embedded
    private Email email;

    /** Phone number of the representative. */
    @Embedded
    private PhoneNumber phone;

    /** Job position/title of the representative. */
    @Column(nullable = false)
    private String position;

    /** Indicates whether the representative is active. */
    @Column(nullable = false)
    private boolean active = true;

    /**
     * Protected constructor required by JPA.
     */
    protected CustomerRepresentative() {
    }

    /**
     * Constructs a new customer representative with all required fields.
     *
     * @param costumer Associated customer.
     * @param name     Representative's name.
     * @param email    Representative's email.
     * @param phone    Representative's phone number.
     * @param position Representative's job position.
     */
    public CustomerRepresentative(Costumer costumer, Name name, Email email, PhoneNumber phone, String position) {
        this.costumer = costumer;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
    }

    /**
     * Marks the representative as inactive.
     */
    public void disable() {
        this.active = false;
    }

    /**
     * Marks the representative as active.
     */
    public void enable() {
        this.active = true;
    }

    /**
     * Checks if the representative is currently active.
     *
     * @return True if active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns the unique identity of this entity.
     *
     * @return The internal ID.
     */
    @Override
    public Long identity() {
        return id;
    }

    /**
     * Compares this representative with another for equality based on email.
     *
     * @param other Object to compare.
     * @return True if both represent the same email, false otherwise.
     */
    @Override
    public boolean sameAs(Object other) {
        return equals(other);
    }

    /**
     * Compares this representative to another using the email address.
     *
     * @param o The object to compare with.
     * @return True if emails match, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerRepresentative)) return false;
        CustomerRepresentative obj = (CustomerRepresentative) o;
        return email.equals(obj.email);
    }

    /**
     * Computes the hash code using the representative's email.
     *
     * @return Hash code.
     */
    @Override
    public int hashCode() {
        return email.hashCode();
    }

    /**
     * Returns a formatted string containing representative details.
     *
     * @return A readable summary string.
     */
    @Override
    public String toString() {
        return String.format("Rep: %-20s | Email: %-25s | Phone: %-10s | Position: %-15s | Active: %s",
                name, email, phone, position, active ? "Yes" : "No");
    }

    /**
     * Sets the associated customer for this representative.
     *
     * @param costumer The customer to assign.
     */
    public void defineCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

}
