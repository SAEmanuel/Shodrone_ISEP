package domain.entity;

import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Costumer class represents a customer in the system.
 * It is an entity class that stores information about a customer, including their name, email, phone number,
 * NIF (tax identification number), and address. This class is annotated with JPA annotations to map it to a database table
 * and implements the AggregateRoot interface for domain-driven design (DDD).
 *
 * The Costumer class ensures that a customer's NIF is unique, and it provides methods for accessing and manipulating
 * customer data, including equality checks and string representation.
 */
@XmlRootElement
@Entity
public final class Costumer implements AggregateRoot<Long>, Serializable {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerSystemID;

    @Embedded
    private Name name;

    @Column(nullable = false, length = 100)
    private EmailAddress email;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "number", column = @Column(name = "phone_number", nullable = false, unique = true))})
    private PhoneNumber phoneNumber;

    @Getter
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "nif", nullable = false, unique = true))})
    private NIF nif;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "costumer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerRepresentative> representatives = new ArrayList<>();


    /**
     * Default constructor for JPA.
     */
    protected Costumer() {}

    /**
     * Constructs a Costumer with the given name, email, phone number, NIF, and address.
     *
     * @param name the customer's name.
     * @param email the customer's email address.
     * @param phoneNumber the customer's phone number.
     * @param nif the customer's NIF (tax identification number).
     * @param address the customer's address.
     */
    public Costumer(final Name name, final EmailAddress email,
                    final PhoneNumber phoneNumber, final NIF nif,
                    final Address address) {
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
        this.nif = Objects.requireNonNull(nif);
        this.address = address;
    }

    /**
     * Constructs a Costumer with the given customer ID, name, email, phone number, NIF, and address.
     *
     * @param costumerID the customer ID (System-generated ID).
     * @param name the customer's name.
     * @param email the customer's email address.
     * @param phoneNumber the customer's phone number.
     * @param nif the customer's NIF (tax identification number).
     * @param address the customer's address.
     */
    public Costumer(final long costumerID, final Name name, final EmailAddress email,
                    final PhoneNumber phoneNumber, final NIF nif,
                    final Address address) {
        this.customerSystemID = costumerID;
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
        this.nif = Objects.requireNonNull(nif);
        this.address = address;
    }

    /**
     * Returns the customer's system ID (primary key).
     *
     * @return the customer's system ID.
     */
    public Long identity() {
        return customerSystemID;
    }

    /**
     * Returns the customer's name.
     *
     * @return the customer's name.
     */
    public Name name() {
        return name;
    }

    /**
     * Returns the customer's email address.
     *
     * @return the customer's email address.
     */
    public EmailAddress email() {
        return email;
    }

    /**
     * Returns the customer's phone number.
     *
     * @return the customer's phone number.
     */
    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the customer's NIF (tax identification number).
     *
     * @return the customer's NIF.
     */
    public NIF nif() {
        return nif;
    }

    /**
     * Returns the customer's address.
     *
     * @return the customer's address.
     */
    public Address address() {
        return address;
    }

    /**
     * Checks equality based on the customer's NIF (assuming it is unique).
     *
     * @param o the object to compare.
     * @return true if the object is equal to this Costumer, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Costumer)) return false;
        final Costumer other = (Costumer) o;
        return nif.equals(other.nif); // assuming NIF is unique
    }

    /**
     * Returns a hash code for the Costumer based on its NIF.
     *
     * @return the hash code of the Costumer.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nif);
    }

    /**
     * Checks whether the current Costumer is the same as another object.
     * Compares the customer ID if it is not null, otherwise compares by NIF.
     *
     * @param other the other object to compare.
     * @return true if the current Costumer is the same as the other object, false otherwise.
     */
    @Override
    public boolean sameAs(final Object other) {
        if (this == other) return true;
        if (!(other instanceof Costumer)) return false;
        final Costumer otherCostumer = (Costumer) other;

        if (this.customerSystemID != null && otherCostumer.customerSystemID != null) {
            return this.customerSystemID.equals(otherCostumer.customerSystemID);
        }

        return this.nif.equals(otherCostumer.nif);
    }

    /**
     * Returns a string representation of the Costumer entity.
     * The string includes the customer ID, name, email, phone number, NIF, and address.
     *
     * @return the string representation of the Costumer.
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %-4d | Name: %-20s | Email: %-30s | Phone: %-12s | NIF: %-9s | Address: %-43s",
                customerSystemID,
                name.toString(),
                email.toString(),
                phoneNumber.toString(),
                nif.toString(),
                address.toString()
        );
    }

    public void addRepresentative(CustomerRepresentative rep) {
        representatives.add(rep);
        rep.defineCostumer(this);
    }

    public List<CustomerRepresentative> getRepresentatives() {
        return representatives;
    }

}
