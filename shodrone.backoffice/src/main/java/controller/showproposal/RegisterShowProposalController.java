package controller.showproposal;

import domain.entity.*;
import domain.valueObjects.Content;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;
import proposal_template.validators.TemplatePlugin;
import utils.AuthUtils;

import java.time.LocalDateTime;
import java.util.*;


public class RegisterShowProposalController {
    private final ShowProposalRepository repository;

    public RegisterShowProposalController() {
        this.repository = RepositoryProvider.showProposalRepository();
    }


    public Optional<ShowProposal> generateShowProposal(ShowRequest showRequest, ProposalTemplate template, Map<DroneModel, Integer> droneModels, int numberOfDrones) {
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

        ShowProposal showProposal = new ShowProposal(showRequest, template, new ArrayList<>(figures.values()), showRequest.getDescription(), showRequest.getLocation(), showRequest.getShowDate(), numberOfDrones, showRequest.getShowDuration(), AuthUtils.getCurrentUserName(), LocalDateTime.now(), droneModels);
        showProposal.setText(filled);

        return repository.saveInStore(showProposal);

    }


}
