package domain.entity;

import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import jakarta.persistence.*;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
public final class Costumer implements Serializable {
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
    public String toString() {
        return String.format("Name=%s, Email=%s, Phone=%s, NIF=%s]", name, email, phoneNumber, nif);
    }
}
