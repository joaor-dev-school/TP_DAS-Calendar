package tp.das.Model.Database;

import java.util.HashSet;
import java.util.Set;

public class UnitOfWork {
    private Set<Object> create = new HashSet<>();
    private Set<Object> update = new HashSet<>();
    private Set<Object> delete = new HashSet<>();

    public void registerNew(Object data) {
        if(update.contains(data) || delete.contains(data)) {
            throw new RuntimeException("Error trying to register new data: "+data);
        }
        create.add(data);
    }

    public void registerDirty(Object data) {
        if(delete.contains(data)) {
            throw new RuntimeException("Error trying to register update data: "+data);
        }
        create.remove(data);
        update.add(data);
    }

    public void registerClean(Object data) {
        create.remove(data);
        update.remove(data);
        delete.remove(data);
    }

    public void registerDeleted(Object data) {
        create.remove(data);
        update.remove(data);
        delete.add(data);
    }

    public void rollback() {
        create.clear();
        update.clear();
        delete.clear();
    }

    public void commit() {
        // Todo: Complete this.
    }
}
