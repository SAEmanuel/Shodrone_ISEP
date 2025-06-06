package persistence;

import domain.entity.Costumer;
import domain.entity.ShowProposal;
import domain.valueObjects.Video;

import java.util.List;
import java.util.Optional;

public interface ShowProposalRepository {

    public Optional<ShowProposal> saveInStore(ShowProposal entity);

    public Optional<List<ShowProposal>> getAllProposals();

    public Optional<ShowProposal> updateInStoreProposal(ShowProposal entity);

    public Optional<List<ShowProposal>> findByCostumer(Costumer costumer);

    public Optional<Video> getVideoBytesByShowProposal(ShowProposal showProposal);

}
