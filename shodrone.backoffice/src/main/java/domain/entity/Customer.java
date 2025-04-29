package domain.entity;

import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerSystemID;

    @Column(unique = false, nullable = false, length = 100, name = "name")
    private Name name;
    @Column(unique = false, nullable = false, length = 100, name = "email_address")
    private EmailAddress email;
    @Column(unique = true, nullable = false, name = "phone_number")
    private PhoneNumber phoneNumber;
    @Column(unique = true, nullable = false, name = "nif")
    private NIF nif;
    @Column(unique = false, nullable = true, name = "address")
    private Address address;

    public Customer() {

    }


}
