package domain.history;

import java.io.Serializable;

public interface IdentifiableEntity<ID extends Serializable> {
    ID identity();
}


