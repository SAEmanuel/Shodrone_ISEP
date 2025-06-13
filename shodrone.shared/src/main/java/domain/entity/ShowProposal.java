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

@Entity
public class ShowProposal extends DomainEntityBase<Long> implements Serializable, IdentifiableEntity<Long> {

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "show_proposal_template")
    private ProposalTemplate template;


    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "show_proposal_figures",
            joinColumns = @JoinColumn(name = "Show_Proposal_ID"),
            inverseJoinColumns = @JoinColumn(name = "figureId")
    )
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

    /**
     * Planned duration of the show.
     */
    @Setter
    @Getter
    @Column(name = "Show_Duration", nullable = false)
    private Duration showDuration;

    @Setter
    @Getter
    @Column(name = "Video", nullable = true)
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
    @Column(name = "Name_Proposal", nullable = false)
    private Name nameProposal;

    @Setter
    @Getter
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> text;

    public ShowProposal() {
    }

    public ShowProposal(Name name, ShowRequest showRequest, ProposalTemplate template, List<Figure> sequenceFigures
            , Description description, Location location, LocalDateTime showDate, int numberOfDrones, Duration showDuration, String creationAuthor, LocalDateTime creationDate,Map<DroneModel, Integer> modelsUsed ) {

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
        text = new ArrayList<>();
    }

    @Override
    public Long identity() {
        return showProposalID;
    }

    public void editShowProposalID(long l) { this.showProposalID = l; }

    public void editVideo(Video video) {
        this.status = ShowProposalStatus.STAND_BY;
        this.video = video;
    }

    public Description getDescription() { return description; }

    public void changeDescription(Description newDescription) {
        this.description = newDescription;
    }

    public void changeTemplate(ProposalTemplate newTemplate) {
        this.template = newTemplate;
    }

    public ProposalTemplate template() {
        return template;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof ShowProposal)) return false;
        ShowProposal that = (ShowProposal) other;
        return Objects.equals(identity(), that.identity());
    }

    public ShowProposal cloneProposal() {
        return new ShowProposal(
                this.nameProposal,
                this.showRequest,
                this.template,
                new ArrayList<>(this.sequenceFigues),
                this.description,
                this.location,
                this.showDate,
                this.numberOfDrones,
                this.showDuration,
                this.creationAuthor,
                this.creationDate,
                new HashMap<>(this.modelsUsed)
        );
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
