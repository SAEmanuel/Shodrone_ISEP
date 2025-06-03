package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.entity.CustomerRepresentative;
import domain.entity.ShowRequest;
import persistence.CustomerRepresentativeRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the {@link CustomerRepresentativeRepository} interface.
 * Provides automatic transactional support for managing {@link CustomerRepresentative} entities.
 * Uses the "shodrone_backoffice" persistence unit and "id" as the identity field.
 */
public class CustomerRepresentativeRepositoryJPAImpl
        extends JpaBaseRepository<CustomerRepresentative, Long>
        implements CustomerRepresentativeRepository {

    @Override
    public Optional<CustomerRepresentative> saveInStore(CustomerRepresentative entity){
        if (entity.identity() != null) {
            return Optional.empty();
        }

        if(!findCostumer(entity.getCostumer())){
            return Optional.empty();
        }

        add(entity);
        return Optional.of(entity);
    }
    @Override
    public Optional<Costumer> getAssociatedCustomer(String emailOfRepresentative){
        if (emailOfRepresentative == null) return Optional.empty();

        List<CustomerRepresentative> requests = entityManager()
                .createQuery("SELECT s FROM CustomerRepresentative s WHERE s.costumer.email = :email", CustomerRepresentative.class)
                .setParameter("email", emailOfRepresentative)
                .getResultList();

        if(requests.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(requests.get(0).getCostumer());
    }


    public boolean findCostumer(Costumer costumer) {
        if (costumer == null) return false;

        List<CustomerRepresentative> requests = entityManager()
                .createQuery("SELECT s FROM CustomerRepresentative s WHERE s.costumer.id = :costumer", CustomerRepresentative.class)
                .setParameter("costumer", costumer.identity())
                .getResultList();

        return requests.isEmpty();
    }



}
