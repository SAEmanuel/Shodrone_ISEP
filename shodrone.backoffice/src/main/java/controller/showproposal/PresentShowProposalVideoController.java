package controller.showproposal;

import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

public class PresentShowProposalVideoController {

    public PresentShowProposalVideoController() {
    }

    public List<ShowProposal> getAllProposals(){
        Optional<List<ShowProposal>> optionalResult = RepositoryProvider.showProposalRepository().getAllProposals();

        if(optionalResult.isEmpty()){
            throw new RuntimeException("No Show Proposal's were found on the system.");
        }
        return optionalResult.get();
    }

    public Optional<Video> getVideoOfShowProposal(ShowProposal showProposal) {
        return RepositoryProvider.showProposalRepository().getVideoBytesByShowProposal(showProposal);
    }
}
