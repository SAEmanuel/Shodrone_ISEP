package controller.show;

import controller.customerRepresentative.FindCustomerOfRepresentativeController;
import domain.entity.Costumer;
import domain.entity.Show;

import java.util.List;
import java.util.Optional;

public class CheckShowDatesController {
    private final FindCustomerOfRepresentativeController findCustomerOfRepresentativeController;

    public CheckShowDatesController() {
        findCustomerOfRepresentativeController = new FindCustomerOfRepresentativeController();
    }

    public Optional<List<Show>> getShowsForCustomer(String email){
        Optional<Costumer> foundCustomer = findCustomerOfRepresentativeController.getCustomerIDbyHisEmail(email);

        if(foundCustomer.isEmpty()){
            throw new RuntimeException("✖ No was found Customer for the corresponding representative Logged in.");
        }

        Costumer customer = foundCustomer.get();
        System.out.println("Customer: " + customer);
        Optional<List<Show>> showList4Customer = Optional.empty();
        //Optional<List<Show>> showList4Customer = RepositoryProvider.showRepository().findByCostumer(customer);

        if(showList4Customer.isEmpty()){
            throw new RuntimeException("✖ No shows found for costumer with NIF [" + customer.nif() + "] and Name [" + customer.name() + "].");
        }

        return showList4Customer;
    }


}
