package persistence.jpa;

import java.io.Serializable;

public interface GenericRepository<T, ID extends Serializable> {

    T update(T entity);

    void delete(T entity);


}
