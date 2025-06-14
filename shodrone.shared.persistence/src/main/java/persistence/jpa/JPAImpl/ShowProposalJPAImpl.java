package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
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
    public Optional<ShowProposal> saveInStore(ShowProposal entity) {
        if (entity.identity() != null) {
            Optional<ShowProposal> existing = Optional.ofNullable(findById(entity.identity()));
            if (existing.isPresent()) {
                update(entity);
                return Optional.of(entity);
            } else {
                add(entity);
                return Optional.of(entity);
            }
        }

        Optional<ShowProposal> duplicate = findDuplicateProposal(entity);
        if (duplicate.isPresent()) {
            return duplicate;
        }

        add(entity);
        return Optional.of(entity);
    }

    private Optional<ShowProposal> findDuplicateProposal(ShowProposal entity) {
        TypedQuery<ShowProposal> query = entityManager().createQuery(
                "SELECT sp FROM ShowProposal sp WHERE sp.showRequest.id = :showRequest "
                        + "AND sp.nameProposal = :name ", ShowProposal.class);

        query.setParameter("showRequest", entity.getShowRequest().identity());
        query.setParameter("name", entity.getNameProposal());


        List<ShowProposal> results = query.getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public Optional<List<ShowProposal>> getAllProposals() {
        List<ShowProposal> listShowProposals = findAll();

        return listShowProposals.isEmpty() ? Optional.empty() : Optional.of(listShowProposals);
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
                .setParameter("status", ShowProposalStatus.PASSED_SIMULATION)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results);
    }


    public Optional<Video> getVideoBytesByShowProposal(ShowProposal proposal) {
        ShowProposal request = findById(proposal.identity());
        if (request != null && request.getVideo() != null) {
            return Optional.of(request.getVideo());
        }
        return Optional.empty();

    }

    @Override
    public Optional<ShowProposal> findByID(Long id) {
        return Optional.ofNullable(entityManager().find(ShowProposal.class, id));
    }

    @Override
    public Optional<List<ShowProposal>> getUpdatedProposals() {
        List<ShowProposal> listShowProposals = entityManager().createQuery(
                        "SELECT DISTINCT p FROM ShowProposal p LEFT JOIN FETCH p.sequenceFigues", ShowProposal.class)
                .setHint("javax.persistence.cache.storeMode", "REFRESH")
                .getResultList();

        return listShowProposals.isEmpty() ? Optional.empty() : Optional.of(listShowProposals);
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

    @Override
    public Optional<List<ShowProposal>> getStandbyProposals() {
        List<ShowProposal> results = entityManager().createQuery(
                        "SELECT s FROM ShowProposal s WHERE s.status = :status", ShowProposal.class)
                .setParameter("status", ShowProposalStatus.STAND_BY)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results);
    }
}



