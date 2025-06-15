package factories.impl;

import domain.entity.*;
import domain.valueObjects.Description;
import domain.valueObjects.Location;
import domain.valueObjects.Name;
import eapli.framework.domain.model.DomainFactory;
import utils.AuthUtils;
import utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ProposalTemplate template;
    private Map<DroneModel, Integer> drones;
    private Name proposalName;

    /**
     * Builds and returns a new ShowProposal object with the current factory values.
     *
     * @return a new ShowProposal instance.
     */
    @Override
    public ShowProposal build() {
        return new ShowProposal(
                proposalName,
                showRequest,
                template,
                sequenceFigures,
                description,
                location,
                showDate,
                numberOfDrones,
                showDuration,
                creationAuthor,
                creationDate,
                drones
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
     * @param drones           map of drone models and their quantities.
     * @param proposalName     the name of the proposal.
     * @param template         proposal template used.
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
            Map<DroneModel, Integer> drones,
            Name proposalName,
            ProposalTemplate template
    ) {
        try {
            validateSequenceFigures(sequenceFigures);
            validateNumberOfDrones(numberOfDrones);
            validateName(proposalName);
            validateTemplate(template);
            validateDroneMap(drones);

            this.showRequest = showRequest;
            this.sequenceFigures = sequenceFigures;
            this.description = description;
            this.location = location;
            this.showDate = showDate;
            this.numberOfDrones = numberOfDrones;
            this.showDuration = showDuration;
            this.creationAuthor = AuthUtils.getCurrentUserEmail();
            this.creationDate = LocalDateTime.now();
            this.drones = drones;
            this.proposalName = proposalName;
            this.template = template;

            return Optional.of(build());

        } catch (IllegalArgumentException e) {
            Utils.printFailMessage("❌ Failed to create ShowProposal: " + e.getMessage());
            return Optional.empty();
        }
    }

    private void validateSequenceFigures(List<Figure> sequenceFigures) {
        if (sequenceFigures == null || sequenceFigures.isEmpty()) {
            throw new IllegalArgumentException("Sequence of show request empty.");
        }
    }

    private void validateNumberOfDrones(int numberOfDrones) {
        if (numberOfDrones <= 0) {
            throw new IllegalArgumentException("Number of drones must be greater than zero.");
        }
    }

    private void validateName(Name proposalName) {
        if (proposalName == null || proposalName.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("Proposal name cannot be empty.");
        }
    }

    private void validateTemplate(ProposalTemplate template) {
        if (template == null) {
            throw new IllegalArgumentException("Proposal template cannot be null.");
        }
    }

    private void validateDroneMap(Map<DroneModel, Integer> drones) {
        if (drones == null || drones.isEmpty()) {
            throw new IllegalArgumentException("Drone model map cannot be empty.");
        }
        if (drones.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalArgumentException("Drone quantities must be greater than zero.");
        }
    }
}
