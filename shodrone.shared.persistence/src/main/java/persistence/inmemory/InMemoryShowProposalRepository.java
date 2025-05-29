package persistence.inmemory;

import domain.entity.ShowProposal;
import persistence.ShowProposalRepository;

import java.util.*;

public class InMemoryShowProposalRepository implements ShowProposalRepository{
    private static Map<Long, List<ShowProposal>> store = new HashMap<>();
    private static long LAST_SHOW_REQUEST_ID = 1L;

    @Override
    public Optional<ShowProposal> saveInStore(Long showRequestID,ShowProposal entity) {
        if(entity == null ){
            return Optional.empty();
        }

        if(entity.identity() == null){
            entity.setShowProposalID(LAST_SHOW_REQUEST_ID++);
        }

        if(store.get(showRequestID) == null){
            store.put(showRequestID,new ArrayList<>());
            store.get(showRequestID).add(entity);
        }else{
            store.get(showRequestID).add(entity);
        }

        return Optional.of(entity);
    }


}
