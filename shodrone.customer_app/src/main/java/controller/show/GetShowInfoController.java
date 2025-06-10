package controller.show;

import controller.customerRepresentative.FindCustomerOfRepresentativeController;
import controller.network.AuthenticationController;
import domain.valueObjects.NIF;
import network.ShowDTO;

import java.util.List;
import java.util.Optional;

public class GetShowInfoController {
    private final FindCustomerOfRepresentativeController findCustomerOfRepresentativeController;
    private final FindShows4CustomerController findShows4CustomerController;

    public GetShowInfoController(AuthenticationController authController) {
        findCustomerOfRepresentativeController = new FindCustomerOfRepresentativeController(authController);
        findShows4CustomerController = new FindShows4CustomerController(authController);
    }

    public NIF getCustomerNIFOfTheRepresentativeAssociated(String email){
        Optional<NIF> foundCustomer = findCustomerOfRepresentativeController.getCustomerIDbyHisEmail(email);

        if(foundCustomer.isEmpty()){
            throw new RuntimeException("✖ No was found Customer for the corresponding representative Logged in.");
        }

        return foundCustomer.get();
    }

    public Optional<List<ShowDTO>> getShowsForCustomer(NIF customerNIF){

        Optional<List<ShowDTO>> showList4Customer = findShows4CustomerController.getShows4Customer(customerNIF);

        if(showList4Customer.isEmpty()){
            throw new RuntimeException("✖ No shows found for costumer with NIF [" + customerNIF + "].");
        }

        return showList4Customer;
    }


}
