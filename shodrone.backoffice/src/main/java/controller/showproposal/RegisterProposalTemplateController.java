package controller.showproposal;

import domain.entity.ProposalTemplate;
import domain.valueObjects.Content;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.ProposalTemplateRepository;
import persistence.RepositoryProvider;
import proposal_template.validators.TemplatePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegisterProposalTemplateController {
    private final ProposalTemplateRepository repository;

    public RegisterProposalTemplateController() {
        repository = RepositoryProvider.proposalTemplateRepository();
    }


    public Optional<ProposalTemplate> registerProposalTemplate(Name name, Description description, List<String> text, List<String> missing) {
        TemplatePlugin plugin = new TemplatePlugin(text);
        List<String> missingFields = plugin.validate();

        missing.addAll(missingFields);

        if (!missingFields.isEmpty()) {
            return Optional.empty();
        } else {
            ProposalTemplate template = new ProposalTemplate(name, description, text);
            return repository.save(template);
        }
    }
}
