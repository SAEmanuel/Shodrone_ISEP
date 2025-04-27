package domain.entity;

import domain.model.DomainEntityBase;

public class ShowRequest extends DomainEntityBase<Long> {
    @Override
    public Long identity() {
        return 0L;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public int compareTo(Long other) {
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
