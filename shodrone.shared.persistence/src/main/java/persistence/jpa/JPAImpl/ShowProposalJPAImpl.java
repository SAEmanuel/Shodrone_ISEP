package persistence.jpa.JPAImpl;

import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.NotImplementedException;
import persistence.ShowProposalRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Objects;
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
                        +"AND sp.nameProposal = :name ", ShowProposal.class);

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
    public Optional<List<ShowProposal>> getAllProposals(){
        List<ShowProposal> listShowProposals = findAll();

        return listShowProposals.isEmpty() ? Optional.empty() : Optional.of(listShowProposals);
    }

    @Override
    public Optional<ShowProposal> updateInStoreProposal(ShowProposal entity) {
        if (entity == null || entity.identity() == null) {
            return Optional.empty();
        }
        Optional<ShowProposal> existing = Optional.ofNullable(findById(entity.identity()));
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        ShowProposal updated = update(entity);
        return Optional.ofNullable(updated);
    }

    @Override
    public Optional<Video> getVideoBytesByShowProposal(ShowProposal proposal) {
        ShowProposal request = findById(proposal.identity());
        if (request != null && request.getVideo() != null) {
            return Optional.of(request.getVideo());
        }
        return Optional.empty();
    }
}



