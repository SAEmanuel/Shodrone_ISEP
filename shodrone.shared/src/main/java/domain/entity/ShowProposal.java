package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.Location;
import domain.valueObjects.ShowProposalStatus;
import domain.valueObjects.ShowRequestStatus;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.IdentifiableEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


public class ShowProposal extends DomainEntityBase<Long> implements AggregateRoot<Long>, Serializable, IdentifiableEntity<Long> {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final LocalDateTime DEFAULT_SENT_DATE = null;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Show_Proposal_ID", nullable = false, unique = true)
    private Long showProposalID;

    @Column(name = "Show_Request_Associated", nullable = false)
    private Long showRequestID;

    @Column(name = "Show_Proposal_Template", nullable = false)
    private ShowTemplate template;

    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "List_Figures")
    private List<Figure> sequenceFigues;

    @Column(name = "Show_Description")
    @Embedded
    private Description description;

    @Setter
    @Getter
    @Embedded
    @Column(name = "Show_Location", nullable = false)
    private Location location;

    @Setter
    @Getter
    @Column(name = "Show_date", nullable = false)
    private LocalDateTime showDate;


    /**
     * Number of drones planned for the show.
     */
    @Setter
    @Getter
    @Column(name = "Number_of_Drones", nullable = false)
    private int numberOfDrones;

    /**
     * Planned duration of the show.
     */
    @Setter
    @Getter
    @Column(name = "Show_Duration", nullable = false)
    private Duration showDuration;

    @Setter
    @Getter
    @Column(name = "Show_Video", nullable = false)
    private Object video;

    //-------------------
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "Show_Proposal_Status", nullable = false)
    private ShowProposalStatus status;

    @Setter
    @Getter
    @Column(name = "Creation_Author")
    private String creationAuthor;

    @Setter
    @Getter
    @Column(name = "Creation_Date")
    private LocalDateTime creationDate;

    @Setter
    @Getter
    @Column(name = "Sent_Date")
    private LocalDateTime sentDate;


    protected ShowProposal() {
    }

    public ShowProposal(Long showRequestID, ShowTemplate template, List<Figure> sequenceFigues
            , Description description, Location location, LocalDateTime showDate, int numberOfDrones, Duration showDuration,
                        Object video, String creationAuthor, LocalDateTime creationDate) {

        this.showRequestID = showRequestID;
        this.template = template;
        this.sequenceFigues = sequenceFigues;
        this.description = description;
        this.location = location;
        this.showDate = showDate;
        this.numberOfDrones = numberOfDrones;
        this.showDuration = showDuration;
        this.video = video;
        this.creationAuthor = creationAuthor;
        this.creationDate = creationDate;
    }

    @Override
    public Long identity() {
        return showProposalID;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof ShowProposal)) return false;
        ShowProposal that = (ShowProposal) other;
        return Objects.equals(identity(), that.identity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(showProposalID);
    }

}
