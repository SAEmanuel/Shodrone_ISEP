package controller.show;

import controller.customerRepresentative.FindCustomerOfRepresentativeController;
import controller.network.AuthenticationController;
import domain.entity.Show;
import domain.valueObjects.NIF;

import java.util.List;
import java.util.Optional;

public class CheckShowDatesController {
    private final FindCustomerOfRepresentativeController findCustomerOfRepresentativeController;
    private final FindShows4CustomerController findShows4CustomerController;

    public CheckShowDatesController(AuthenticationController authController) {
        findCustomerOfRepresentativeController = new FindCustomerOfRepresentativeController(authController);
        findShows4CustomerController = new FindShows4CustomerController(authController);
    }

    public Optional<List<Show>> getShowsForCustomer(String email){
        Optional<NIF> foundCustomer = findCustomerOfRepresentativeController.getCustomerIDbyHisEmail(email);

        if(foundCustomer.isEmpty()){
            throw new RuntimeException("✖ No was found Customer for the corresponding representative Logged in.");
        }

        NIF customerNIF = foundCustomer.get();
        System.out.println("Customer: " + customerNIF);
        Optional<List<Show>> showList4Customer = findShows4CustomerController.getShows4Customer(customerNIF);

        if(showList4Customer.isEmpty()){
            throw new RuntimeException("✖ No shows found for costumer with NIF [" + customerNIF + "].");
        }

        return showList4Customer;
    }


}
