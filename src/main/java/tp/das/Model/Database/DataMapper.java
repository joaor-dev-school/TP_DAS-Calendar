package tp.das.Model.Database;

public interface DataMapper<T> {
    void create(T entity) throws Exception;
    void update(Object id, T entity) throws Exception;
    void delete(Object id) throws Exception;
    T find(Object id);
}
