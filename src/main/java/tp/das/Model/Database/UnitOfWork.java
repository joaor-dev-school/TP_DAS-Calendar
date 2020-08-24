package tp.das.Model.Database;

import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Database.DataMappers.AuthDataMapper;
import tp.das.Model.Database.DataMappers.EventsDataMapper;
import tp.das.Model.Database.DataMappers.UsersDataMapper;
import tp.das.Model.Event.Decorators.PeriodicEventDecorator;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Event.EventSchedulingModel;
import tp.das.Model.Utilizador.UserModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnitOfWork {
    private static final Class<?>[] classListOrderedForCommit = new Class[]{
            EventModel.class,
            EventSchedulingModel.class,
            PeriodicEventDecorator.class,
            AuthModel.class,
            UserModel.class
    };
    private Set<EntityModel> create = new HashSet<>();
    private Set<EntityModel> update = new HashSet<>();
    private Set<EntityModel> delete = new HashSet<>();

    public void registerNew(EntityModel data) {
        if (update.contains(data) || delete.contains(data)) {
            throw new RuntimeException("Error trying to register new data: " + data);
        }
        create.add(data);
    }

    public void registerDirty(EntityModel data) {
        if (create.contains(data) || delete.contains(data)) {
            throw new RuntimeException("Error trying to register update data: " + data);
        }
        update.add(data);
    }

    public void registerClean(EntityModel data) {
        create.remove(data);
        update.remove(data);
        delete.remove(data);
    }

    public void registerDeleted(EntityModel data) {
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
        final List<EntityModel> oldEntitiesCreated = new ArrayList<>();
        final List<EntityModel> oldEntitiesUpdated = new ArrayList<>();
        final List<EntityModel> oldEntitiesDeleted = new ArrayList<>();
        try {
            for (Class<?> c : classListOrderedForCommit) {
                final DataMapper dataMapper = this.resolveDataMapper(c);

                for (EntityModel o : this.delete) {
                    if (c.equals(o.getClass())) {
                        dataMapper.delete(o.getId());
                        oldEntitiesDeleted.add(o);
                    }
                }

                for (EntityModel o : this.create) {
                    if (c.equals(o.getClass())) {
                        dataMapper.create(o);
                        oldEntitiesCreated.add(o);
                    }
                }

                for (EntityModel o : this.update) {
                    if (c.equals(o.getClass())) {
                        dataMapper.update(o.getId(), o);
                        oldEntitiesUpdated.add(o);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error on commit unit of work");
            ex.printStackTrace();
            this.rollbackOnCommit(oldEntitiesCreated, oldEntitiesUpdated, oldEntitiesDeleted);
            throw new RuntimeException(ex);
        } finally {
            this.create.clear();
            this.delete.clear();
            this.update.clear();
        }
    }

    private void rollbackOnCommit(List<EntityModel> oldEntitiesCreated, List<EntityModel> oldEntitiesUpdated,
                                  List<EntityModel> oldEntitiesDeleted) {
        try {
            List<Class<?>> classListOrderedForCommitReversed = new ArrayList<>();
            for (int i = classListOrderedForCommit.length - 1; i > -1; i--) {
                classListOrderedForCommitReversed.add(classListOrderedForCommit[i]);
            }
            for (Class<?> c : classListOrderedForCommitReversed) {
                final DataMapper dataMapper;

                try {
                    dataMapper = this.resolveDataMapper(c);
                } catch (Exception e) {
                    continue;
                }

                for (EntityModel o : oldEntitiesUpdated) {
                    if (c.equals(o.getClass())) {
                        dataMapper.update(o.getId(), o);
                    }
                }

                for (EntityModel o : oldEntitiesDeleted) {
                    if (c.equals(o.getClass())) {
                        dataMapper.create(o);
                    }
                }

                for (EntityModel o : oldEntitiesCreated) {
                    if (c.equals(o.getClass())) {
                        dataMapper.delete(o.getId());
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error on commit unit of work");
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            this.create.clear();
            this.delete.clear();
            this.update.clear();
        }
    }

    private DataMapper resolveDataMapper(Class<?> c) {
        if (c.equals(EventModel.class)
                || c.equals(EventSchedulingModel.class)
                || c.equals(PeriodicEventDecorator.class)) {
            return EventsDataMapper.getInstance();
        }
        if (c.equals(UserModel.class)) {
            return UsersDataMapper.getInstance();
        }
        if (c.equals(AuthModel.class)) {
            return AuthDataMapper.getInstance();
        }
        throw new RuntimeException("No data mapper for entity object " + c);
    }
}
