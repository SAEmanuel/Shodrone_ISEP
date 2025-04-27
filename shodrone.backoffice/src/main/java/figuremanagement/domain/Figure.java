package figuremanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Figure implements AggregateRoot<Long>{
    @Id
    private Long id;

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean equals(final Object other) {
        return this.identity().equals(other);
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Figure)) {
            return false;
        }

        final Figure that = (Figure) other; // ver o q faz ao certo
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
