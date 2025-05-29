package persistence.jpa.JPAImpl;

import domain.entity.ShowProposal;
import persistence.ShowProposalRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.Optional;

public class ShowProposalJPAImpl extends JpaBaseRepository<ShowProposal, Long> implements ShowProposalRepository {

    @Override
    public Optional<ShowProposal> saveInStore(Long showRequestID, ShowProposal entity) {

        if (entity.identity() == null || findById(entity.identity()) == null) {
            return Optional.empty();
        }

        add(entity);
        return Optional.of(entity);
    }
}



