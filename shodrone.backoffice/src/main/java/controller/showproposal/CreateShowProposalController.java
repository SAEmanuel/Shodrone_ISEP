package controller.showproposal;

import domain.entity.*;
import domain.valueObjects.Content;
import domain.valueObjects.Description;
import domain.valueObjects.Location;
import domain.valueObjects.Name;
import factories.FactoryProvider;
import history.HistoryLogger;
import persistence.RepositoryProvider;
import proposal_template.validators.TemplatePlugin;
import utils.AuthUtils;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class CreateShowProposalController {

    public CreateShowProposalController() {
    }

    /**
     * Registers a show proposal with the given parameters, including the selected drone models.
     *
     * @param showRequest the show request
     * @param template the proposal template
     * @param description optional description of the show
     * @param location location of the show
     * @param showDate date and time of the show
     * @param numberOfDrones total number of drones
     * @param showDuration duration of the show
     * @param version version string used as proposal name
     * @param droneModels map of DroneModel to their quantities selected for the show
     * @return optional ShowProposal if successful
     * @throws IOException if saving fails
     */
    public Optional<ShowProposal> registerShowProposal(
            Name proposalName,
            ShowRequest showRequest,
            ProposalTemplate template,
            Description description,
            Location location,
            LocalDateTime showDate,
            int numberOfDrones,
            Duration showDuration,
            String version,
            Map<DroneModel, Integer> droneModels
    ) throws IOException {
        Map<Integer, Figure> figures = new HashMap<>();
        for (int i = 0; i < showRequest.getFigures().size(); i++) {
            figures.put(i + 1, showRequest.getFigures().get(i));
        }

        Content content = new Content(
                showRequest.getCostumer(),
                showRequest.getShowDate(),
                showRequest.getLocation(),
                showRequest.getShowDuration(),
                figures, droneModels,
                AuthUtils.getCurrentUserName());

        Map<String, String> placeholders = TemplatePlugin.buildPlaceholderMap(content);

        List<String> filled = TemplatePlugin.replacePlaceholders(template.text(), placeholders);
        content.changeText(filled);

        // Use the factory to build the proposal with full validation
        Optional<ShowProposal> result = FactoryProvider.getShowProposalFactory().automaticBuild(
                showRequest,
                new ArrayList<>(figures.values()),
                description,
                location,
                showDate,
                numberOfDrones,
                showDuration,
                version,
                droneModels,
                proposalName,
                template
        );

        if (result.isPresent()) {
            result.get().setText(filled);
            result = RepositoryProvider.showProposalRepository().saveInStore(result.get());
            if (result.isEmpty()) {
                Utils.exitImmediately("❌ Failed during save process of the show proposal.");
            }
        } else {
            Utils.exitImmediately("❌ Failed to register the show proposal. Please check the input data and try again.");
        }

        HistoryLogger<ShowProposal, Long> loggerEditor = new HistoryLogger<>();
        loggerEditor.logCreation(result.get(), AuthUtils.getCurrentUserEmail());

        return result;
    }
}
