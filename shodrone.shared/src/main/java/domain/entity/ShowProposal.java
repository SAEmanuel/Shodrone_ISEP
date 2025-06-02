package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.Location;
import domain.valueObjects.ShowProposalStatus;
import domain.valueObjects.Video;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import static java.lang.String.format;
import static more.ColorfulOutput.*;

@Entity
public class ShowProposal extends DomainEntityBase<Long> implements AggregateRoot<Long>, Serializable, IdentifiableEntity<Long> {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final LocalDateTime DEFAULT_SENT_DATE = null;

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Show_Proposal_ID", nullable = false, unique = true)
    private Long showProposalID;

    @Setter
    @Getter
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Show_Request", nullable = false)
    private ShowRequest showRequest;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "Show_Proposal_Template")
//    private ShowTemplate template;

    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Show_Proposal")
    private List<Figure> sequenceFigues;

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
    @Column(name = "Video_Path", nullable = true)
    @Embedded
    private Video video;


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

    @Setter
    @Getter
    @Column(name = "Name_Proposal")
    private String nameProposal;

    protected ShowProposal() {
    }

    public ShowProposal(ShowRequest showRequest, ShowTemplate template, List<Figure> sequenceFigues
            , Description description, Location location, LocalDateTime showDate, int numberOfDrones, Duration showDuration, String creationAuthor, LocalDateTime creationDate,String version ) {

        this.showRequest = showRequest;
//        this.template = template;
        this.sequenceFigues = sequenceFigues;
        this.description = description;
        this.location = location;
        this.showDate = showDate;
        this.numberOfDrones = numberOfDrones;
        this.showDuration = showDuration;
        this.creationAuthor = creationAuthor;
        this.creationDate = creationDate;
        this.status = ShowProposalStatus.CREATED;
        this.nameProposal = version;
    }

    @Override
    public Long identity() {
        return showProposalID;
    }

    public void editShowProposalID(long l) { this.showProposalID = l; }

    public void editVideo(Video video) { this.video = video; }

    public Description getDescription() { return description; }

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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format(
                "%s%sID_SP =%s %d %s| %s%sID_SR =%s %s %s| %s%sStatus =%s%s %s%s | %s%sDrones =%s %-4d %s| %s%sDuration =%s %-8s %s| %s%sAuthor =%s %s %s| %s%sCreation Date =%s %s %s| %s%sStart Date =%s %s %s| %s%sLocation =%s %s %s",
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, identity(), ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, getShowRequest().identity() != null ? getShowRequest().identity() : "N/A", ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, ANSI_PURPLE, getStatus(), ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, getNumberOfDrones(), ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, getShowDuration().toMinutes() + " min", ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, getCreationAuthor(), ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, getCreationDate().format(formatter), ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, getShowDate() != null ? getShowDate().format(formatter) : "N/A", ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_BOLD, ANSI_RESET, getLocation() != null ? getLocation().toString() : "N/A", ANSI_RESET
        );
    }

}
