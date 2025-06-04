package persistence.jpa.JPAImpl;


import domain.entity.ProposalTemplate;
import persistence.ProposalTemplateRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class ProposalTemplateJPAImpl extends JpaBaseRepository<ProposalTemplate, Long> implements ProposalTemplateRepository {

    @Override
    public Optional<ProposalTemplate> save(ProposalTemplate template) {
        Optional<ProposalTemplate> checkExistence = findByName(template.name().name());
        if (checkExistence.isEmpty()) {
            ProposalTemplate saved = this.add(template);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProposalTemplate> findByName(String name) {
        List<ProposalTemplate> results = entityManager()
                .createQuery("SELECT pt FROM ProposalTemplate pt WHERE LOWER(pt.name.name) = :name", ProposalTemplate.class)
                .setParameter("name", name.toLowerCase())
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public List<ProposalTemplate> findAll() {
        return entityManager()
                .createQuery("SELECT pt FROM ProposalTemplate pt ORDER BY LOWER(pt.name.name) ASC", ProposalTemplate.class)
                .getResultList();
    }


}
