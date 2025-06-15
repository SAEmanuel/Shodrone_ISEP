package persistence;

import domain.entity.Costumer;
import domain.entity.ShowProposal;
import domain.valueObjects.Video;

import java.util.List;
import java.util.Optional;

public interface ShowProposalRepository {

    public Optional<ShowProposal> saveInStore(ShowProposal entity);

    public Optional<List<ShowProposal>> getAllProposals();

    public Optional<List<ShowProposal>> findAllCostumerProposals(Costumer costumer);

    public Optional<Video> getVideoBytesByShowProposal(ShowProposal showProposal);

    public Optional<ShowProposal> findByID(Long id);

    public Optional<ShowProposal> findByName(String nameProposal);

    public Optional<List<ShowProposal>> getUpdatedProposals();

    public Optional<List<ShowProposal>> getStandbyProposals();

    public Optional<List<ShowProposal>> getCreatedProposals();
}
