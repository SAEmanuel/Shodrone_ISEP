package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.Location;
import domain.valueObjects.ShowProposalStatus;
import domain.valueObjects.ShowStatus;
import eapli.framework.domain.model.AggregateRoot;
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

@Entity
public class Show extends DomainEntityBase<Long> implements AggregateRoot<Long>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Show_ID", nullable = false, unique = true)
    private Long showID;

    @Getter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "Show_Proposal_Accepted_ID")
    private ShowProposal showProposalAcceptedID;

    @Getter
    @Setter
    @Column(name = "Figures",nullable = false)
    private List<Figure> sequenceFigures = new ArrayList<>();

    @Getter
    @Embedded
    private Location location;

    @Getter
    @Column(name = "Show_Date", nullable = false)
    private LocalDateTime showDate;

    @Getter
    @Column(name = "Number_of_Drones", nullable = false)
    private int numberOfDrones;

    @Getter
    @Column(name = "Show_Duration", nullable = false)
    private Duration showDuration;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "Show_Status", nullable = false)
    private ShowStatus status;

    @Getter
    @Column(name = "Customer_ID")
    private Long customerID;

    // Construtores
    protected Show() {
        // for ORM
    }

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

    // Identidade
    @Override
    public Long identity() {
        return showID;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Show)) return false;
        Show that = (Show) other;
        return Objects.equals(this.showID, that.showID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Show)) return false;
        Show that = (Show) obj;
        return Objects.equals(this.showID, that.showID);
    }

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
