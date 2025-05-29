package controller.showproposal;

import domain.entity.ShowProposal;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

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
}
