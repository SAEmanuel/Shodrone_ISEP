package domain.entity;

import domain.valueObjects.Location;
import domain.valueObjects.ShowRequestStatus;
import eapli.framework.domain.model.DomainEntityBase;
import domain.valueObjects.Description;
import jakarta.persistence.*;
import lombok.Getter;

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

    @Getter
    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowRequestStatus status;

    @Getter
    @Column(name = "submission_author", nullable = false)
    private String submissionAuthor;

    @Getter
    @OneToOne
    @JoinColumn(name = "costumer_id", nullable = false)
    private Costumer costumer;

    @Getter
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "show_request_id")
    private List<Figure> figures;

    @Getter
    @Embedded
    private Description description;

    @Getter
    @Embedded
    private Location location;

    @Getter
    @Column(name = "show_date", nullable = false)
    private LocalDateTime showDate;

    @Getter
    @Column(name = "number_of_drones", nullable = false)
    private int numberOfDrones;

    @Getter
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
}
