package tp.das.Model.Database;

import java.util.List;

public interface DataMapper<T> {
    void create(T entity) throws Exception;
    void update(Object id, T entity) throws Exception;
    void delete(Object id) throws Exception;
    List<T> findAll() throws Exception;
    T find(Object id) throws Exception;
}
