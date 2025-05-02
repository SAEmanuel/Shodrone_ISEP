package factories.impl;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import domain.valueObjects.Description;
import domain.valueObjects.Location;
import domain.valueObjects.ShowRequestStatus;
import eapli.framework.domain.model.DomainFactory;
import utils.AuthUtils;
import utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ShowRequestFactoryImpl implements DomainFactory<ShowRequest> {

    private LocalDateTime requestSubmissionDate;
    private String responsibleCollaborator;
    private Costumer costumerSelected;
    private List<Figure> figuresSelected;
    private Description descriptionOfShowRequest;
    private Location locationOfShow;
    private LocalDateTime showDate;
    private int numberOfDrones;
    private Duration showDuration;
    private ShowRequestStatus status;

    @Override
    public ShowRequest build() {
        return new ShowRequest(
                requestSubmissionDate,
                status,
                responsibleCollaborator,
                costumerSelected,
                figuresSelected,
                descriptionOfShowRequest,
                locationOfShow,
                showDate,
                numberOfDrones,
                showDuration
        );
    }

    public Optional<ShowRequest> automaticBuild(Costumer costumer, List<Figure> figures,
                                                Description description, Location location,
                                                LocalDateTime showDate, int numberOfDrones,
                                                Duration showDuration) {
        try {
            validateShowDate(showDate);
            validateFigures(figures);
            validateNumberOfDrones(numberOfDrones);

            this.requestSubmissionDate = LocalDateTime.now();
            this.status = ShowRequestStatus.PENDING;
            this.responsibleCollaborator = AuthUtils.getCurrentUserEmail();
            this.costumerSelected = costumer;
            this.figuresSelected = figures;
            this.descriptionOfShowRequest = description;
            this.locationOfShow = location;
            this.showDate = showDate;
            this.numberOfDrones = numberOfDrones;
            this.showDuration = showDuration;

            return Optional.of(build());

        } catch (IllegalArgumentException e) {
            Utils.printFailMessage("Failed to create ShowRequest: " + e.getMessage());
            return Optional.empty();
        }
    }

    private void validateShowDate(LocalDateTime showDate) {
        LocalDateTime minAllowedDate = LocalDateTime.now().plusHours(72);
        if (showDate.isBefore(minAllowedDate)) {
            throw new IllegalArgumentException("Show date must be at least 72 hours in the future.");
        }
    }

    private void validateFigures(List<Figure> figures) {
        if (figures == null || figures.isEmpty()) {
            throw new IllegalArgumentException("At least one figure must be selected.");
        }
    }

    private void validateNumberOfDrones(int numberOfDrones) {
        if (numberOfDrones <= 0) {
            throw new IllegalArgumentException("The number of drones must be greater than zero.");
        }
    }
}
