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
     * @param numberOfDrones total number of drones
     * @param droneModels map of DroneModel to their quantities selected for the show
     * @return optional ShowProposal if successful
     * @throws IOException if saving fails
     */
    public Optional<ShowProposal> registerShowProposal(
            Name proposalName,
            ShowRequest showRequest,
            ProposalTemplate template,
            int numberOfDrones,
            Map<DroneModel, Integer> droneModels
    ) throws IOException {
        for(ShowProposal p : getShowProposal().orElse(new ArrayList<>())) {
            if(p.getNameProposal().equals(proposalName)) {
                Utils.printFailMessage("❌ Failed to register the show proposal! Show proposal already exists with that name!");
                return Optional.empty();
            }
        }

        Map<Integer, Figure> figures = new HashMap<>();
        for (int i = 0; i < showRequest.getFigures().size(); i++) {
            Figure figure = showRequest.getFigures().get(i);
            if (figure != null) {
                figures.put(i + 1, figure);
            }
        }

        Content content = new Content(
                showRequest.getCostumer(),
                showRequest.getShowDate(),
                showRequest.getLocation(),
                showRequest.getShowDuration(),
                figures,
                droneModels,
                AuthUtils.getCurrentUserName());

        Map<String, String> placeholders = TemplatePlugin.buildPlaceholderMap(content);

        List<String> filled = TemplatePlugin.replacePlaceholders(template.text(), placeholders);
        content.changeText(filled);

        // Use the factory to build the proposal with full validation
        Optional<ShowProposal> result = FactoryProvider.getShowProposalFactory().automaticBuild(
                showRequest,
                new ArrayList<>(figures.values()),
                showRequest.getDescription(),
                showRequest.getLocation(),
                showRequest.getShowDate(),
                numberOfDrones,
                showRequest.getShowDuration(),
                droneModels,
                proposalName,
                template
        );

        if (result.isPresent()) {
            result.get().setText(filled);
            result = RepositoryProvider.showProposalRepository().saveInStore(result.get());
            if (result.isEmpty()) {
                Utils.printFailMessage("❌ Failed during save process of the show proposal.");
            }
        } else {
            return Optional.empty();
        }

        HistoryLogger<ShowProposal, Long> loggerEditor = new HistoryLogger<>();
        loggerEditor.logCreation(result.get(), AuthUtils.getCurrentUserEmail());

        return result;
    }

    private Optional<List<ShowProposal>> getShowProposal(){
        return RepositoryProvider.showProposalRepository().getAllProposals();
    }
}
