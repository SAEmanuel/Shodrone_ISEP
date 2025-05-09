package persistence.inmemory;

import authz.Email;
import domain.entity.CustomerRepresentative;
import eapli.framework.domain.repositories.DomainRepository;
import persistence.interfaces.CustomerRepresentativeRepository;

import java.util.*;

public class InMemoryCustomerRepresentativeRepository implements CustomerRepresentativeRepository {

    private final Map<Long, CustomerRepresentative> idMap = new HashMap<>();
    private final Map<Email, CustomerRepresentative> emailMap = new HashMap<>();
    private long nextId = 1L;

    @Override
    public CustomerRepresentative save(CustomerRepresentative entity) {
        if (entity.identity() == null) {
            try {
                var field = CustomerRepresentative.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(entity, nextId);
            } catch (Exception e) {
                throw new RuntimeException("Failed to set ID", e);
            }
        }
        idMap.put(entity.identity(), entity);
        emailMap.put(entity.getEmail(), entity);
        return entity;
    }

    @Override
    public Optional<CustomerRepresentative> ofIdentity(Long id) {
        return Optional.ofNullable(idMap.get(id));
    }

    @Override
    public Iterable<CustomerRepresentative> findAll() {
        return idMap.values();
    }

    @Override
    public void delete(CustomerRepresentative entity) {
        idMap.remove(entity.identity());
        emailMap.remove(entity.getEmail());
    }

    @Override
    public void deleteOfIdentity(Long entityId) {
        ofIdentity(entityId).ifPresent(this::delete);
    }

    @Override
    public long count() {
        return idMap.size();
    }
}
