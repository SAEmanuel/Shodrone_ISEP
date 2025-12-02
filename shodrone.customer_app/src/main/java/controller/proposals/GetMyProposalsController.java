package controller.proposals;

import controller.customerRepresentative.FindCustomerOfRepresentativeController;
import controller.network.AuthenticationController;
import domain.valueObjects.NIF;
import network.ShowProposalDTO;

import java.util.List;
import java.util.Optional;

public class GetMyProposalsController {
    private final FindCustomerOfRepresentativeController findCustomerOfRepresentativeController;
    private final SearchSpecificCustomersProposalsController searchController;

    public GetMyProposalsController(AuthenticationController authenticationController) {
        findCustomerOfRepresentativeController = new FindCustomerOfRepresentativeController(authenticationController);
        searchController = new SearchSpecificCustomersProposalsController(authenticationController);
    }


    public Optional<List<ShowProposalDTO>> getMyProposals(String email) {
        NIF customerNIF = findCustomerOfRepresentativeController.getCustomerIDbyHisEmail(email).get();
        Optional<List<ShowProposalDTO>> myProposals = searchController.searchProposals(customerNIF);
        if (myProposals.isEmpty()) {
            throw new RuntimeException("No proposals found for costumer with NIF [" + customerNIF + "].");
        }
        return myProposals;
    }
}
