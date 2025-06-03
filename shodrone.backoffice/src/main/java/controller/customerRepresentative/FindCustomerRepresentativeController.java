package controller.customerRepresentative;

import domain.entity.Costumer;
import persistence.RepositoryProvider;

import java.util.Optional;

public class FindCustomerRepresentativeController {

    public FindCustomerRepresentativeController(){}

    public Optional<Costumer> getCustomerIDbyHisEmail(String email){
        return RepositoryProvider.customerRepresentativeRepository().getAssociatedCustomer(email);
    }
}
