package controller.showproposal;

import domain.entity.ProposalTemplate;
import persistence.ProposalTemplateRepository;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

public class GetAllProposalTemplatesController {
    private final ProposalTemplateRepository repository;

    public GetAllProposalTemplatesController() {
        this.repository = RepositoryProvider.proposalTemplateRepository();
    }


    public Optional<List<ProposalTemplate>> getAllProposalTemplates() {
        List<ProposalTemplate> list = repository.findAll();
        return Optional.ofNullable(list);
    }
}
