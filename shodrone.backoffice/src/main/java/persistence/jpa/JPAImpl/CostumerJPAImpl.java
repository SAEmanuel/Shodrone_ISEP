package persistence.jpa.JPAImpl;

import domain.entity.Customer;
import persistence.interfaces.CostumerRepository;
import persistence.jpa.JpaBaseRepository;



public class CostumerJPAImpl extends JpaBaseRepository<Customer, Integer> implements CostumerRepository<Customer> {
}
