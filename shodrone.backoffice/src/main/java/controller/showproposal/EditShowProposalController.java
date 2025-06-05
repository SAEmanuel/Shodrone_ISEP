package controller.showproposal;

import domain.entity.ShowProposal;
import domain.entity.Figure;
import domain.valueObjects.Content;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;
import proposal_template.validators.TemplatePlugin;
import utils.AuthUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EditShowProposalController {
    private final ShowProposalRepository repository;

    public EditShowProposalController() {
        repository = RepositoryProvider.showProposalRepository();
    }


    public Optional<ShowProposal> editShowProposal(ShowProposal proposal, ShowProposal newProposal) {
        Content content = new Content(
                newProposal.getShowRequest().getCostumer(),
                newProposal.getShowDate(),
                newProposal.getLocation(),
                newProposal.getShowDuration(),
                buildFiguresMap(newProposal.getSequenceFigues()),
                newProposal.getModelsUsed(),
                AuthUtils.getCurrentUserName()
        );


        Map<String, String> placeholders = TemplatePlugin.buildPlaceholderMap(content);
        List<String> filled = TemplatePlugin.replacePlaceholders(newProposal.template().text(), placeholders);
        content.changeText(filled);
        newProposal.setText(filled);

        List<String> missingFields = new TemplatePlugin(newProposal.template().text()).validate();
        if (!missingFields.isEmpty()) {
            return Optional.empty();
        }

        return repository.updateInStoreProposal(newProposal);
    }

    private Map<Integer, Figure> buildFiguresMap(List<Figure> figures) {
        Map<Integer, Figure> map = new HashMap<>();
        for (int i = 0; i < figures.size(); i++) {
            map.put(i + 1, figures.get(i));
        }
        return map;
    }

}
