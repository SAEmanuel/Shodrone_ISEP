package domain.entity;

import domain.model.DomainEntityBase;
import domain.valueObjects.Location;
import domain.valueObjects.ShowRequestStatus;
import eapli.framework.general.domain.model.Description;
import jakarta.persistence.*;
import pt.isep.lei.esoft.auth.domain.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Show Request")
public class ShowRequest extends DomainEntityBase<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Identification",nullable = false,unique = true)
    private Long ShowRequestId;
    @Column(nullable = false,name = "Submission Date")
    private LocalDateTime submissionDate;
    @Column(nullable = false,name = "Status")
    private ShowRequestStatus status;
    @Column(nullable = false,name = "Submission Author")
    private String submissionAuthor; //WARNING

    @Embedded
    private Costumer costumer;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Figures")
    @Column(nullable = false,name = "Figures")
    private List<Figure> figures;


    @Column(nullable = true,name = "Description")
    private Description description;
    @Column(nullable = false,name = "Location")
    private Location location;
    @Column(nullable = false,name = "Show Date")
    private LocalDateTime showDate;
    @Column(nullable = false, name = "Number of Drones")
    private int numberOfDrones;
    @Column(nullable = false, name = "Show Duration")
    private Duration numberOfShips;

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
