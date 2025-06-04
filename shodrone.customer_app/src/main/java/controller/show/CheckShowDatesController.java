package controller.show;

import controller.customerRepresentative.FindCustomerRepresentativeController;
import domain.entity.Costumer;
import domain.entity.Show;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

public class CheckShowDatesController {
    private final FindCustomerRepresentativeController findCustomerRepresentativeController;

    public CheckShowDatesController() {
        findCustomerRepresentativeController = new FindCustomerRepresentativeController();
    }

    public Optional<List<Show>> getShowsForCustomer(String email){
        Optional<Costumer> foundCustomer = findCustomerRepresentativeController.getCustomerIDbyHisEmail(email);

        if(foundCustomer.isEmpty()){
            throw new RuntimeException("No was found Customer for the corresponding representative Logged in.");
        }

        Costumer customer = foundCustomer.get();
        Optional<List<Show>> showList4Customer = RepositoryProvider.showRepository().findByCostumer(customer);

        if(showList4Customer.isEmpty()){
            throw new RuntimeException("No shows found for costumer with NIF [" + customer.nif() + "] and Name [" + customer.name() + "].");
        }

        return showList4Customer;
    }


}
