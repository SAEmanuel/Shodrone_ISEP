package controller.showproposal;

import domain.entity.Figure;
import domain.entity.ShowProposal;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class AddFiguresToProposalController {


    public AddFiguresToProposalController() {
    }

    public List<ShowProposal> getAllProposals(){
        Optional<List<ShowProposal>> optionalResult = RepositoryProvider.showProposalRepository().getAllProposals();

        if(optionalResult.isEmpty()){
            throw new RuntimeException("No Show Proposal's were found on the system.");
        }
        return optionalResult.get();
    }

    public Optional<ShowProposal> saveNewImagesInProposal(ShowProposal showProposal ,List<Figure> sequenceOfFigures){
        showProposal.setSequenceFigues(sequenceOfFigures);
        return RepositoryProvider.showProposalRepository().updateInStoreProposal(showProposal);
    }
}
