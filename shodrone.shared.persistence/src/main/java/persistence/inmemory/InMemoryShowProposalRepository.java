package persistence.inmemory;

import domain.entity.Costumer;
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
    public Optional<ShowProposal> updateInStoreProposal(ShowProposal entity) {
        if (entity == null || entity.identity() == null || entity.getShowRequest() == null) {
            return Optional.empty();
        }

        Long idShowRequest = entity.getShowRequest().identity();

        // Obtém a lista de propostas para o ShowRequest
        List<ShowProposal> proposals = store.get(idShowRequest);

        if (proposals == null) {
            return Optional.empty();
        }

        // Encontra o índice da proposta com o mesmo identity
        for (int i = 0; i < proposals.size(); i++) {
            ShowProposal proposal = proposals.get(i);
            if (proposal.identity().equals(entity.identity())) {
                proposals.set(i, entity);
                return Optional.of(entity);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<ShowProposal>> findByCostumer(Costumer costumer) {
        if (store.containsKey(costumer.identity())) {
            return Optional.of(store.get(costumer.identity()));
        }
        return Optional.empty();
    }


}
