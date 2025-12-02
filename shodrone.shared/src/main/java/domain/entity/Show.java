package domain.entity;

import domain.valueObjects.Location;
import domain.valueObjects.ShowStatus;
import eapli.framework.domain.model.DomainEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity class representing a confirmed Drone Show.
 * <p>
 * A Show is based on an accepted proposal, has a scheduled date, location,
 * number of drones, duration, and a status. It also belongs to a specific customer.
 */
@Entity
public class Show extends DomainEntityBase<Long> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** Auto-generated primary key for the Show entity. */
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Show_ID", nullable = false, unique = true)
    private Long showID;

    /** The accepted show proposal on which this show is based. */
    @Getter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "Show_Proposal_Accepted_ID")
    private ShowProposal showProposalAcceptedID;

    /** Sequence of Figures (animations or steps) associated with the Show. */
    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "show_request_id")
    private List<Figure> sequenceFigures = new ArrayList<>();

    /** Location where the show will be held. */
    @Getter
    @Embedded
    private Location location;

    /** Scheduled date and time for the show. */
    @Getter
    @Column(name = "Show_Date", nullable = false)
    private LocalDateTime showDate;

    /** Number of drones involved in the show. */
    @Getter
    @Column(name = "Number_of_Drones", nullable = false)
    private int numberOfDrones;

    /** Duration of the show. */
    @Getter
    @Column(name = "Show_Duration", nullable = false)
    private Duration showDuration;

    /** Current status of the show (e.g., PLANNED, COMPLETED, CANCELLED). */
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "Show_Status", nullable = false)
    private ShowStatus status;

    /** ID of the customer associated with the show. */
    @Getter
    @Column(name = "Customer_ID")
    private Long customerID;

    /**
     * Default constructor for JPA.
     */
    protected Show() {
    }

    /**
     * Constructs a new Show based on an accepted proposal.
     *
     * @param showProposalAcceptedID the accepted proposal
     * @param location               the location of the show
     * @param showDate               the date/time of the show
     * @param numberOfDrones         how many drones are used
     * @param showDuration           how long the show lasts
     * @param status                 current status of the show
     * @param customerID             the ID of the customer
     */
    public Show(ShowProposal showProposalAcceptedID, Location location, LocalDateTime showDate,
                int numberOfDrones, Duration showDuration, ShowStatus status, Long customerID) {
        this.showProposalAcceptedID = showProposalAcceptedID;
        this.location = location;
        this.showDate = showDate;
        this.numberOfDrones = numberOfDrones;
        this.showDuration = showDuration;
        this.status = status;
        this.customerID = customerID;
    }

    /**
     * Gets the identity (primary key) of the entity.
     *
     * @return the Show ID.
     */
    @Override
    public Long identity() {
        return showID;
    }

    /**
     * Compares this show with another object to determine logical equality.
     *
     * @param other the other object
     * @return true if both are shows with the same ID
     */
    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Show)) return false;
        Show that = (Show) other;
        return Objects.equals(this.showID, that.showID);
    }

    /**
     * Computes the hash code of the entity based on the ID.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(showID);
    }

    /**
     * Compares this object with another for strict equality.
     *
     * @param obj the other object
     * @return true if both are the same instance or same ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Show)) return false;
        Show that = (Show) obj;
        return Objects.equals(this.showID, that.showID);
    }

    /**
     * Returns a textual representation of the show.
     *
     * @return the string format of the show.
     */
    @Override
    public String toString() {
        return "Show{" +
                "showID=" + showID +
                ", proposalAccepted=" + (showProposalAcceptedID != null ? showProposalAcceptedID.identity() : null) +
                ", location=" + location +
                ", showDate=" + showDate +
                ", numberOfDrones=" + numberOfDrones +
                ", duration=" + showDuration +
                ", status=" + status +
                ", customerID=" + customerID +
                '}';
    }
}
