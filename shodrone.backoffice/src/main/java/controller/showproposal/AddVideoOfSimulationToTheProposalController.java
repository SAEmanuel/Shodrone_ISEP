package controller.showproposal;

import domain.entity.Figure;
import domain.entity.ShowProposal;
import domain.valueObjects.Drone3DSimulation;
import domain.valueObjects.Video;
import javafx.application.Application;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

public class AddVideoOfSimulationToTheProposalController {
    public AddVideoOfSimulationToTheProposalController(){}

    public List<ShowProposal> getAllProposals(){
        Optional<List<ShowProposal>> optionalResult = RepositoryProvider.showProposalRepository().getAllProposals();

        if(optionalResult.isEmpty()){
            throw new RuntimeException("No Show Proposal's were found on the system.");
        }
        return optionalResult.get();
    }

    public Optional<ShowProposal> addVideoToShowProposal(ShowProposal showProposal, Video video){
        showProposal.editVideo(video);
        return RepositoryProvider.showProposalRepository().updateInStoreProposal(showProposal);
    }
}
