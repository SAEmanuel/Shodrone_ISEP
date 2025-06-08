package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import persistence.ShowProposalRepository;

import java.util.*;

public class InMemoryShowProposalRepository implements ShowProposalRepository {
    private static Map<Long, List<ShowProposal>> store = new HashMap<>();
    private static long LAST_SHOW_REQUEST_ID = 1L;

    @Override
    public Optional<ShowProposal> saveInStore(ShowProposal entity) {
        if (entity == null) {
            return Optional.empty();
        }

        if (entity.identity() == null) {
            entity.setShowProposalID(LAST_SHOW_REQUEST_ID++);
        }

        Long idShowRequest = entity.getShowRequest().identity();

        if (store.get(idShowRequest) == null) {
            store.put(idShowRequest, new ArrayList<>());
            store.get(idShowRequest).add(entity);
        } else {
            store.get(idShowRequest).add(entity);
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<List<ShowProposal>> getAllProposals() {
        List<ShowProposal> listShowProposals = new ArrayList<>();

        for (List<ShowProposal> proposals : store.values()) {
            listShowProposals.addAll(proposals);
        }

        return listShowProposals.isEmpty() ? Optional.empty() : Optional.of(listShowProposals);
    }

    @Override
    public Optional<List<ShowProposal>> findAllCostumerProposals(Costumer costumer) {
        if (store.containsKey(costumer.identity())) {
            return Optional.of(store.get(costumer.identity()));
        }
        return Optional.empty();
    }


    public Optional<Video> getVideoBytesByShowProposal(ShowProposal proposal) {
        List<ShowProposal> proposals = store.get(proposal.identity());

        if (proposals == null || proposals.isEmpty()) {
            return Optional.empty();
        }

        for (ShowProposal sp : proposals) {
            if (sp.identity().equals(proposal.identity())) {
                Video video = sp.getVideo();
                if (video != null) {
                    return Optional.of(video);
                } else {
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }

    //todo falta implementar isto...
    @Override
    public Optional<ShowProposal> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ShowProposal> findByName(String nameProposal) {
        return Optional.empty();
    }

    @Override
    public Optional<List<ShowProposal>> getUpdatedProposals() {
        return getAllProposals();
    }

}
