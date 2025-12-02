package persistence;


import domain.entity.ProposalTemplate;

import java.util.List;
import java.util.Optional;

public interface ProposalTemplateRepository {

    Optional<ProposalTemplate> save (ProposalTemplate template);

    List<ProposalTemplate> findAll();

    Optional<ProposalTemplate> findByName(String name);

}
