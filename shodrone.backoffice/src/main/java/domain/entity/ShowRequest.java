package domain.entity;

import domain.valueObjects.Location;
import domain.valueObjects.ShowRequestStatus;
import eapli.framework.domain.model.DomainEntityBase;
import domain.valueObjects.Description;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "show_request")
public class ShowRequest extends DomainEntityBase<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "identification", nullable = false, unique = true)
    private Long showRequestId;

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowRequestStatus status;

    @Column(name = "submission_author", nullable = false)
    private String submissionAuthor;

    @OneToOne
    @JoinColumn(name = "costumer_id", nullable = false)
    private Costumer costumer;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "show_request_id")
    private List<Figure> figures;

    @Embedded
    private Description description;

    @Embedded
    private Location location;

    @Column(name = "show_date", nullable = false)
    private LocalDateTime showDate;

    @Column(name = "number_of_drones", nullable = false)
    private int numberOfDrones;

    @Column(name = "show_duration", nullable = false)
    private Duration showDuration;

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
    }


    public Duration getShowDuration() {
        return showDuration;
    }

    public int getNumberOfDrones() {
        return numberOfDrones;
    }

    public LocalDateTime getShowDate() {
        return showDate;
    }

    public Location getLocation() {
        return location;
    }

    public Description getDescription() {
        return description;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public String getSubmissionAuthor() {
        return submissionAuthor;
    }

    public ShowRequestStatus getStatus() {
        return status;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public Long getShowRequestId() {
        return showRequestId;
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
}
