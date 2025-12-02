package controller.showproposal;

import domain.entity.ShowProposal;
import domain.valueObjects.Video;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

public class AddVideoOfSimulationToTheProposalController {
    public AddVideoOfSimulationToTheProposalController(){}

    public List<ShowProposal> getAllProposals(){
        Optional<List<ShowProposal>> optionalResult = RepositoryProvider.showProposalRepository().getAllProposals();

        return optionalResult.orElse(null);
    }

    public Optional<ShowProposal> addVideoToShowProposal(ShowProposal showProposal, Video video){
        showProposal.editVideo(video);
        return RepositoryProvider.showProposalRepository().saveInStore(showProposal);
    }
}
