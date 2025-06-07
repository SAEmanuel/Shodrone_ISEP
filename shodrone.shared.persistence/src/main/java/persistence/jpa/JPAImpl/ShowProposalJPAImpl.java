package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.entity.FigureCategory;
import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import domain.valueObjects.Video;
import jakarta.persistence.TypedQuery;
import persistence.ShowProposalRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class ShowProposalJPAImpl extends JpaBaseRepository<ShowProposal, Long> implements ShowProposalRepository {

    @Override
    public Optional<ShowProposal> saveInStore(ShowProposal proposal) {
        Optional<ShowProposal> checkExistence = findByName(proposal.getNameProposal().name());
        if (checkExistence.isEmpty()) {
            ShowProposal saved = this.add(proposal);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<ShowProposal>> getAllProposals() {
        List<ShowProposal> listShowProposals = findAll();
        return listShowProposals.isEmpty() ? Optional.empty() : Optional.of(listShowProposals);
    }

    @Override
    public Optional<ShowProposal> updateInStoreProposal(ShowProposal entity) {
        if (entity == null || entity.getNameProposal() == null) {
            return Optional.empty();
        }

        Optional<ShowProposal> existing = findByName(entity.getNameProposal().name());
        if (existing.isEmpty()) {
            return Optional.empty();
        }

        ShowProposal updated = update(entity);
        return Optional.ofNullable(updated);
    }

    @Override
    public Optional<List<ShowProposal>> findAllCostumerProposals(Costumer costumer) {
        if (costumer == null) {
            return Optional.empty();
        }

        List<ShowProposal> results = entityManager().createQuery(
                        "SELECT s FROM ShowProposal s WHERE s.showRequest.costumer = :costumer AND s.status = :status",
                        ShowProposal.class)
                .setParameter("costumer", costumer)
                .setParameter("status", ShowProposalStatus.STAND_BY)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results);
    }

    @Override
    public Optional<Video> getVideoBytesByShowProposal(ShowProposal proposal) {
        ShowProposal sp = findById(proposal.identity());
        if (sp != null && sp.getVideo() != null) {
            return Optional.of(sp.getVideo());
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShowProposal> findByID(Long id) {
        return Optional.ofNullable(entityManager().find(ShowProposal.class, id));
    }


    @Override
    public Optional<ShowProposal> findByName(String name) {
        List<ShowProposal> results = entityManager()
                .createQuery("SELECT f FROM ShowProposal f WHERE LOWER(f.nameProposal.name) = :name", ShowProposal.class)
                .setParameter("name", name.toLowerCase())
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }
}