package persistence.inmemory;

import domain.entity.ShowProposal;
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

        Long idShowRequest = entity.getShowRequestID();

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


}
