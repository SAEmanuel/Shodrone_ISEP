package factories.impl;

import domain.entity.ShowProposal;
import domain.entity.ShowRequest;
import domain.entity.Figure;
import domain.valueObjects.Description;
import domain.valueObjects.Location;
import eapli.framework.domain.model.DomainFactory;
import utils.AuthUtils;
import utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The ShowProposalFactoryImpl class is responsible for creating instances of ShowProposal entities.
 * It provides logic to validate input fields and build a ShowProposal with required attributes.
 */
public class ShowProposalFactoryImpl implements DomainFactory<ShowProposal> {

    private ShowRequest showRequest;
    private List<Figure> sequenceFigures;
    private Description description;
    private Location location;
    private LocalDateTime showDate;
    private int numberOfDrones;
    private Duration showDuration;
    private String creationAuthor;
    private LocalDateTime creationDate;
    private String version;

    /**
     * Builds and returns a new ShowProposal object with the current factory values.
     *
     * @return a new ShowProposal instance.
     */
    @Override
    public ShowProposal build() {
        return new ShowProposal(
                showRequest,
                null, // Template is not currently used
                sequenceFigures,
                description,
                location,
                showDate,
                numberOfDrones,
                showDuration,
                creationAuthor,
                creationDate,
                version
        );
    }

    /**
     * Automatically creates a ShowProposal with validation and metadata.
     *
     * @param showRequest      the associated show request.
     * @param sequenceFigures  list of figures in the sequence.
     * @param description      description of the proposal.
     * @param location         show location.
     * @param showDate         scheduled date/time for the show.
     * @param numberOfDrones   drone count.
     * @param showDuration     duration of the show.
     * @param version          version name/label of the proposal.
     * @return an Optional with the created ShowProposal or empty if validation fails.
     */
    public Optional<ShowProposal> automaticBuild(
            ShowRequest showRequest,
            List<Figure> sequenceFigures,
            Description description,
            Location location,
            LocalDateTime showDate,
            int numberOfDrones,
            Duration showDuration,
            String version
    ) {
        try {
            validateShowDate(showDate);
            validateSequenceFigures(sequenceFigures);
            validateNumberOfDrones(numberOfDrones);
            validateVersion(version);

            this.showRequest = showRequest;
            this.sequenceFigures = sequenceFigures;
            this.description = description;
            this.location = location;
            this.showDate = showDate;
            this.numberOfDrones = numberOfDrones;
            this.showDuration = showDuration;
            this.creationAuthor = AuthUtils.getCurrentUserEmail();
            this.creationDate = LocalDateTime.now();
            this.version = version;

            return Optional.of(build());

        } catch (IllegalArgumentException e) {
            Utils.printFailMessage("❌ Failed to create ShowProposal: " + e.getMessage());
            return Optional.empty();
        }
    }

    private void validateShowDate(LocalDateTime showDate) {
        if (showDate == null || showDate.isBefore(LocalDateTime.now().plusHours(72))) {
            throw new IllegalArgumentException("Show date must be at least 72 hours in the future.");
        }
    }

    private void validateSequenceFigures(List<Figure> sequenceFigures) {
        if (sequenceFigures == null || sequenceFigures.isEmpty()) {
            throw new IllegalArgumentException("Sequence figures cannot be empty.");
        }
    }

    private void validateNumberOfDrones(int numberOfDrones) {
        if (numberOfDrones <= 0) {
            throw new IllegalArgumentException("Number of drones must be greater than zero.");
        }
    }

    private void validateVersion(String version) {
        if (version == null || version.trim().isEmpty()) {
            throw new IllegalArgumentException("Proposal version name cannot be empty.");
        }
    }
}
