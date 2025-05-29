package persistence;

import domain.entity.ShowProposal;

import java.util.Optional;

public interface ShowProposalRepository {

    public Optional<ShowProposal> saveInStore(Long showRequestID,ShowProposal entity);

}
