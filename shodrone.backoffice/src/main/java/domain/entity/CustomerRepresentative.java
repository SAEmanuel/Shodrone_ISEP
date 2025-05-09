package domain.entity;

import authz.Email;
import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import domain.valueObjects.*;

@Entity
public class CustomerRepresentative implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "costumer_id", nullable = false)
    private Costumer costumer;

    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "rep_first_name", nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "rep_last_name", nullable = false))
    })
    private Name name;

    @Embedded
    private Email email;

    @Embedded
    private PhoneNumber phone;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private boolean active = true;

    protected CustomerRepresentative() {
    }

    public CustomerRepresentative(Costumer costumer, Name name, Email email, PhoneNumber phone, String position) {
        this.costumer = costumer;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
    }

    public void disable() {
        this.active = false;
    }

    public void enable() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean sameAs(Object other) {
        return equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerRepresentative)) return false;
        CustomerRepresentative obj = (CustomerRepresentative) o;
        return email.equals(obj.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Rep: %-20s | Email: %-25s | Phone: %-10s | Position: %-15s | Active: %s",
                name, email, phone, position, active ? "Yes" : "No");
    }

    public void defineCostumer(Costumer costumer) {
        this.costumer = costumer;
    }
}

