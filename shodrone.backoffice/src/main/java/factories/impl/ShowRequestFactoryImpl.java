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

/**
 * The ShowRequestFactoryImpl class is responsible for creating instances of ShowRequest entities.
 * It provides the logic to validate the input fields and build a ShowRequest with the necessary attributes,
 * such as the customer, figures selected, show description, location, date, number of drones, and show duration.
 * <p>
 * The factory ensures that the ShowRequest adheres to certain business rules, such as validating that the
 * show date is at least 72 hours in the future and that a valid number of drones is specified.
 * </p>
 */
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

    /**
     * Builds and returns a new ShowRequest object with the current factory values.
     *
     * @return a new ShowRequest instance with the current attributes.
     */
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

    /**
     * Automatically creates a ShowRequest based on the provided inputs and validates them.
     * If all inputs are valid, it builds and returns a ShowRequest object.
     *
     * <p>
     * This method sets the request submission date to the current time, assigns the status to PENDING,
     * and retrieves the current user's email as the responsible collaborator. It also validates key fields
     * such as the show date, figures selected, and the number of drones. If any validation fails, it prints an error
     * message and returns an empty Optional.
     * </p>
     *
     * @param costumer the customer requesting the show.
     * @param figures a list of figures selected for the show.
     * @param description the description of the show request.
     * @param location the location where the show will take place.
     * @param showDate the scheduled date and time for the show.
     * @param numberOfDrones the number of drones involved in the show.
     * @param showDuration the duration of the show.
     * @return an Optional containing the created ShowRequest if valid, or an empty Optional if validation fails.
     */
    public Optional<ShowRequest> automaticBuild(Costumer costumer, List<Figure> figures,
                                                Description description, Location location,
                                                LocalDateTime showDate, int numberOfDrones,
                                                Duration showDuration) {
        try {
            validateShowDate(showDate);
            validateFigures(figures);
            validateNumberOfDrones(numberOfDrones);

            // Set the values of the factory fields
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

            // Return the created ShowRequest
            return Optional.of(build());

        } catch (IllegalArgumentException e) {
            Utils.printFailMessage("Failed to create ShowRequest: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Validates the show date to ensure it is at least 72 hours in the future.
     *
     * @param showDate the date of the show to validate.
     * @throws IllegalArgumentException if the show date is less than 72 hours from the current time.
     */
    private void validateShowDate(LocalDateTime showDate) {
        LocalDateTime minAllowedDate = LocalDateTime.now().plusHours(72);
        if (showDate.isBefore(minAllowedDate)) {
            throw new IllegalArgumentException("Show date must be at least 72 hours in the future.");
        }
    }

    /**
     * Validates the list of figures selected for the show.
     *
     * @param figures the list of figures to validate.
     * @throws IllegalArgumentException if the list of figures is null or empty.
     */
    private void validateFigures(List<Figure> figures) {
        if (figures == null || figures.isEmpty()) {
            throw new IllegalArgumentException("At least one figure must be selected.");
        }
    }

    /**
     * Validates the number of drones to ensure it is greater than zero.
     *
     * @param numberOfDrones the number of drones to validate.
     * @throws IllegalArgumentException if the number of drones is less than or equal to zero.
     */
    private void validateNumberOfDrones(int numberOfDrones) {
        if (numberOfDrones <= 0) {
            throw new IllegalArgumentException("The number of drones must be greater than zero.");
        }
    }
}
