package domain.entity;

import domain.history.IdentifiableEntity;
import domain.valueObjects.Location;
import domain.valueObjects.ShowRequestStatus;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntityBase;
import domain.valueObjects.Description;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "show_request")
public class ShowRequest extends DomainEntityBase<Long> implements AggregateRoot<Long>, Serializable, IdentifiableEntity<Long> {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MODIFICATION_AUTHOR = "No author have modified this Show Request yet!";
    private static final LocalDateTime DEFAULT_MODIFICATION_DATE = null;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "identification", nullable = false, unique = true)
    private Long showRequestId;

    @Getter
    @Column(name = "submission_date", nullable = false,updatable = false)
    private LocalDateTime submissionDate;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowRequestStatus status;

    @Getter
    @Column(name = "submission_author", nullable = false,updatable = false)
    private String submissionAuthor;

    @Getter
    @OneToOne
    @JoinColumn(name = "costumer_id", nullable = false)
    private Costumer costumer;

    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "show_request_id")
    private List<Figure> figures;

    @Setter
    @Getter
    @Embedded
    private Description description;

    @Setter
    @Getter
    @Embedded
    private Location location;

    @Setter
    @Getter
    @Column(name = "show_date", nullable = false)
    private LocalDateTime showDate;

    @Setter
    @Getter
    @Column(name = "number_of_drones", nullable = false)
    private int numberOfDrones;

    @Setter
    @Getter
    @Column(name = "show_duration", nullable = false)
    private Duration showDuration;

    @Setter
    @Getter
    @Column(name = "modification_author")
    private String modificationAuthor;

    @Setter
    @Getter
    @Column(name = "last_modification_date")
    private LocalDateTime modificationDate;

    protected ShowRequest() {}

    public ShowRequest(LocalDateTime submissionDate, ShowRequestStatus status, String submissionAuthor, Costumer costumer, List<Figure> figures, Description description, Location location, LocalDateTime showDate, int numberOfDrones, Duration showDuration) {
        this.submissionDate = submissionDate;
        this.status = status;
        this.submissionAuthor = submissionAuthor;
        this.costumer = costumer;
        this.figures = figures;
        this.description = description;
        this.location = location;
        this.showDate = showDate;
        this.numberOfDrones = numberOfDrones;
        this.showDuration = showDuration;
        this.modificationAuthor = DEFAULT_MODIFICATION_AUTHOR;
        this.modificationDate = DEFAULT_MODIFICATION_DATE;
    }

    //WARNING -> Only for testing porpouse
    public ShowRequest(long ShowRequestID, LocalDateTime submissionDate, ShowRequestStatus status, String submissionAuthor, Costumer costumer, List<Figure> figures, Description description, Location location, LocalDateTime showDate, int numberOfDrones, Duration showDuration) {
        this.showRequestId = ShowRequestID;
        this.submissionDate = submissionDate;
        this.status = status;
        this.submissionAuthor = submissionAuthor;
        this.costumer = costumer;
        this.figures = figures;
        this.description = description;
        this.location = location;
        this.showDate = showDate;
        this.numberOfDrones = numberOfDrones;
        this.showDuration = showDuration;
        this.modificationAuthor = DEFAULT_MODIFICATION_AUTHOR;
        this.modificationDate = DEFAULT_MODIFICATION_DATE;
    }


    public void setId(long l) {
        this.showRequestId = l;
    }

    @Override
    public Long identity() {
        return showRequestId;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof ShowRequest)) return false;
        ShowRequest that = (ShowRequest) other;
        return Objects.equals(showRequestId, that.showRequestId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShowRequest)) return false;
        ShowRequest that = (ShowRequest) o;
        return Objects.equals(showRequestId, that.showRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showRequestId);
    }

    @Override
    public ShowRequest clone() {
        return new ShowRequest(
                this.showRequestId,
                this.submissionDate,
                this.status,
                this.submissionAuthor,
                this.costumer,
                this.figures ,
                this.description,
                this.location,
                this.showDate,
                this.numberOfDrones,
                this.showDuration
        );
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format(
                "ShowRequest [ID=%d, Date=%s, Author=%s, Status=%s, Drones=%d, Duration=%s]",
                showRequestId,
                showDate.format(formatter),
                submissionAuthor,
                status,
                numberOfDrones,
                showDuration.toMinutes() + " min"
        );
    }


}
