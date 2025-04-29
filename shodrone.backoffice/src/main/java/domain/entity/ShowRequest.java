package domain.entity;

import domain.model.DomainEntityBase;
import domain.valueObjects.ShowRequestStatus;
import eapli.framework.general.domain.model.Description;
import jakarta.persistence.*;

@Entity
@Table(name = "Show Request")
public class ShowRequest extends DomainEntityBase<Long> {
     private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Identification")
    private Long ShowRequestId;

    //@Column(nullable = false,name = "Costumer")
    //private Costumer costumer;

    @Column(nullable = true,name = "Description")
    private Description description;

    @Column(nullable = false,name = "Status")
    private ShowRequestStatus status;

    //@Column(nullable = false,name = "CRM Collaborator Author")


    public ShowRequest() {
    }








    @Override
    public Long identity() {
        return 0L;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public int compareTo(Long other) {
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
