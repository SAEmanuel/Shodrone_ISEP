package persistence.jpa.JPAImpl;

import domain.entity.ShowProposal;
import persistence.ShowProposalRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class ShowProposalJPAImpl extends JpaBaseRepository<ShowProposal, Long> implements ShowProposalRepository {

    @Override
    public Optional<ShowProposal> saveInStore(ShowProposal entity) {

        if (entity.identity() == null || findById(entity.identity()) == null) {
            return Optional.empty();
        }

        add(entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<List<ShowProposal>> getAllProposals(){
        List<ShowProposal> listShowProposals = findAll();

        return listShowProposals.isEmpty() ? Optional.empty() : Optional.of(listShowProposals);
    }

}



