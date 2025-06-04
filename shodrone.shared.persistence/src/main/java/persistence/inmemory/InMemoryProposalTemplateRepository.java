package persistence.inmemory;

import domain.entity.ProposalTemplate;
import persistence.ProposalTemplateRepository;

import java.util.*;

public class InMemoryProposalTemplateRepository implements ProposalTemplateRepository {
    private final Map<String, ProposalTemplate> store = new HashMap<>();

    public InMemoryProposalTemplateRepository() {
        super();
    }


    @Override
    public Optional<ProposalTemplate> save(ProposalTemplate template) {
        String key = template.name().name().toLowerCase();
        if (store.containsKey(key)) {
            return Optional.empty();
        } else {
            store.put(key, template);
            return Optional.of(template);
        }
    }

    @Override
    public List<ProposalTemplate> findAll() {
        List<ProposalTemplate> allTemplates = new ArrayList<>(store.values());
        allTemplates.sort(Comparator.comparing(c -> c.name().name().toLowerCase()));
        return allTemplates;
    }

    @Override
    public Optional<ProposalTemplate> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }
}
