package domain.entity;

import domain.valueObjects.*;
import eapli.framework.domain.model.DomainEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persistence.IdentifiableEntity;
import utils.StringListConverter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static more.ColorfulOutput.*;

/**
 * Entity class representing a Show Proposal.
 * <p>
 * A ShowProposal is created in response to a ShowRequest, contains all planned parameters
 * for a possible show, including figures, location, number of drones, duration, authorship,
 * and technical specifications such as scripts or drone programming languages.
 */
@Entity
public class ShowProposal extends DomainEntityBase<Long> implements Serializable, IdentifiableEntity<Long> {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Default value for sent date when none is defined. */
    private static final LocalDateTime DEFAULT_SENT_DATE = null;

    /** Primary key - auto-generated ID for the proposal. */
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Show_Proposal_ID", nullable = false, unique = true)
    private Long showProposalID;

    /** Reference to the originating ShowRequest. */
    @Setter
    @Getter
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Show_Request", nullable = false)
    private ShowRequest showRequest;

    /** Template used as a base for this proposal. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "show_proposal_template")
    private ProposalTemplate template;

    /** Ordered list of Figures included in this proposal. */
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "show_proposal_figures",
            joinColumns = @JoinColumn(name = "Show_Proposal_ID"),
            inverseJoinColumns = @JoinColumn(name = "figureId")
    )
    private List<Figure> sequenceFigues;

    /** Optional description of the proposal. */
    @Embedded
    private Description description;

    /** Location where the show is proposed to occur. */
    @Setter
    @Getter
    @Embedded
    @Column(name = "Show_Location", nullable = false)
    private Location location;

    /** Proposed date and time of the show. */
    @Setter
    @Getter
    @Column(name = "Show_date", nullable = false)
    private LocalDateTime showDate;

    /** Number of drones required for the proposal. */
    @Setter
    @Getter
    @Column(name = "Number_of_Drones", nullable = false)
    private int numberOfDrones;

    /** Mapping of drone models to the number of units needed for the show. */
    @Setter
    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "show_proposal_drone_models",
            joinColumns = @JoinColumn(name = "show_proposal_id")
    )
    @MapKeyJoinColumn(name = "drone_model_id")
    @Column(name = "quantity")
    private Map<DroneModel, Integer> modelsUsed = new HashMap<>();

    /** Proposed total duration of the drone show. */
    @Setter
    @Getter
    @Column(name = "Show_Duration", nullable = false)
    private Duration showDuration;

    /** Optional video preview or simulation associated with the proposal. */
    @Setter
    @Getter
    @Column(name = "Video", nullable = true)
    @Embedded
    private Video video;

    /** Current status of the proposal (e.g., CREATED, SENT, STAND_BY, etc.). */
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "Show_Proposal_Status", nullable = false)
    private ShowProposalStatus status;

    /** Email or identifier of the creator of the proposal. */
    @Setter
    @Getter
    @Column(name = "Creation_Author")
    private String creationAuthor;

    /** Timestamp when the proposal was created. */
    @Setter
    @Getter
    @Column(name = "Creation_Date")
    private LocalDateTime creationDate;

    /** Timestamp when the proposal was submitted. */
    @Setter
    @Getter
    @Column(name = "Sent_Date")
    private LocalDateTime sentDate;

    /** Name/title of the proposal. */
    @Setter
    @Getter
    @Column(name = "Name_Proposal", nullable = false)
    private Name nameProposal;

    /** Additional textual notes associated with the proposal. */
    @Setter
    @Getter
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> text;

    /** Drone programming specifications (language name -> content/script). */
    @Setter
    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "show_proposal_drone_language_specs",
            joinColumns = @JoinColumn(name = "show_proposal_id")
    )
    @MapKeyColumn(name = "language_name")
    @Column(name = "language_specification", columnDefinition = "TEXT")
    private Map<String, String> droneLanguageSpecifications = new HashMap<>();

    /** Main script (e.g., narrative or flight instructions) used for the show. */
    @Setter
    @Getter
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> script;

    /** Required by JPA. */
    public ShowProposal() {}

    /**
     * Creates a new ShowProposal with initial state and required fields.
     *
     * @param name              the name/title of the proposal
     * @param showRequest       the associated show request
     * @param template          the template used
     * @param sequenceFigures   list of figures included
     * @param description       textual description
     * @param location          where the show is proposed
     * @param showDate          scheduled date
     * @param numberOfDrones    number of drones used
     * @param showDuration      total duration
     * @param creationAuthor    author name or email
     * @param creationDate      creation timestamp
     * @param modelsUsed        map of drone models used
     */
    public ShowProposal(Name name, ShowRequest showRequest, ProposalTemplate template, List<Figure> sequenceFigures,
                        Description description, Location location, LocalDateTime showDate, int numberOfDrones,
                        Duration showDuration, String creationAuthor, LocalDateTime creationDate,
                        Map<DroneModel, Integer> modelsUsed) {

        this.nameProposal = name;
        this.showRequest = showRequest;
        this.template = template;
        this.sequenceFigues = sequenceFigures;
        this.description = description;
        this.location = location;
        this.showDate = showDate;
        this.numberOfDrones = numberOfDrones;
        this.showDuration = showDuration;
        this.status = ShowProposalStatus.CREATED;
        this.creationAuthor = creationAuthor;
        this.creationDate = creationDate;
        this.modelsUsed = modelsUsed;
        this.text = new ArrayList<>();
        this.droneLanguageSpecifications = new HashMap<>();
        this.script = new ArrayList<>();
    }

    /** Returns the primary key (proposal ID). */
    @Override
    public Long identity() {
        return showProposalID;
    }

    /** Edit the ID manually if needed. */
    public void editShowProposalID(long l) {
        this.showProposalID = l;
    }

    /** Updates the proposal's video and sets status to STAND_BY. */
    public void editVideo(Video video) {
        this.status = ShowProposalStatus.STAND_BY;
        this.video = video;
    }

    /** Returns the textual description. */
    public Description getDescription() {
        return description;
    }

    /** Changes the description of the proposal. */
    public void changeDescription(Description newDescription) {
        this.description = newDescription;
    }

    /** Changes the proposal template. */
    public void changeTemplate(ProposalTemplate newTemplate) {
        this.template = newTemplate;
    }

    /** Returns the associated template. */
    public ProposalTemplate template() {
        return template;
    }

    /** Logical comparison of two ShowProposal objects. */
    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof ShowProposal)) return false;
        ShowProposal that = (ShowProposal) other;
        return Objects.equals(identity(), that.identity());
    }

    /** Hashcode based on primary key. */
    @Override
    public int hashCode() {
        return Objects.hash(showProposalID);
    }

    /** Textual summary for display in UI or logs. */
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
