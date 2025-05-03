package domain.entity;

import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import jakarta.persistence.*;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
@Entity
public final class Costumer implements AggregateRoot<Long>, Serializable {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerSystemID;
    @Column(nullable = false, length = 100)
    private Name name;
    @Column(nullable = false, length = 100)
    private EmailAddress email;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "number", column = @Column(name = "phone_number", nullable = false, unique = true))})
    private PhoneNumber phoneNumber;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "nif", nullable = false, unique = true))})
    private NIF nif;
    @Embedded
    private Address address;

    protected Costumer() {}

    public Costumer(final Name name, final EmailAddress email,
                    final PhoneNumber phoneNumber, final NIF nif,
                    final Address address) {
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
        this.nif = Objects.requireNonNull(nif);
        this.address = address;
    }

    public Costumer(final long costumerID,final Name name, final EmailAddress email,
                    final PhoneNumber phoneNumber, final NIF nif,
                    final Address address) {
        this.customerSystemID = costumerID;
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.phoneNumber = Objects.requireNonNull(phoneNumber);
        this.nif = Objects.requireNonNull(nif);
        this.address = address;
    }

    public Long identity() {
        return customerSystemID;
    }

    public Name name() {
        return name;
    }

    public EmailAddress email() {
        return email;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public NIF nif() {
        return nif;
    }

    public Address address() {
        return address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Costumer)) return false;
        final Costumer other = (Costumer) o;
        return nif.equals(other.nif); // assuming NIF is unique
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif);
    }

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

//    @Override
//    public String toString() {
//        return String.format(
//                "ID:     %-4d%n" +
//                        "Name:   %-20s%n" +
//                        "Email:  %-30s%n" +
//                        "Phone:  %-12s%n" +
//                        "NIF:    %-9s%n" +
//                        "Address:%-40s%n",
//                customerSystemID,
//                name.toString(),
//                email.toString(),
//                phoneNumber.toString(),
//                nif.toString(),
//                address.toString()
//        );
//    }


}
