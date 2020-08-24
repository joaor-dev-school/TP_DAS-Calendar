package tp.das.Model.Database.DataMappers;

import tp.das.Model.Database.DataMapper;
import tp.das.Model.Notifications.NotificationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsDataMapper implements DataMapper<NotificationModel> {
    private static Long identifiersCounter = 1L;
    private static NotificationsDataMapper dataMapperInstance;

    private final Map<Long, NotificationModel> notifications;

    private NotificationsDataMapper() {
        this.notifications = new HashMap<>();
    }

    public static NotificationsDataMapper getInstance() {
        if (dataMapperInstance == null) {
            dataMapperInstance = new NotificationsDataMapper();
        }
        return dataMapperInstance;
    }

    @Override
    public void create(NotificationModel notification) {
        notification.setId(identifiersCounter);
        notifications.put(identifiersCounter, notification);
        identifiersCounter++;
    }

    @Override
    public void update(Object id, NotificationModel notification) {
        Long identifier = (Long) id;
        NotificationModel n = notifications.get(identifier);
        if (n == null) {
            throw new RuntimeException("Error updating notification: no notification with id " + identifier);
        }
        notifications.put(identifier, notification);

    }

    @Override
    public void delete(Object id) {
        Long identifier = (Long) id;
        NotificationModel n = notifications.get(identifier);
        if (n == null) {
            throw new RuntimeException("Error deleting notification: no notification with id " + identifier);
        }
        notifications.remove(identifier);
    }

    @Override
    public List<NotificationModel> findAll() {
        return new ArrayList<>(notifications.values());
    }

    @Override
    public NotificationModel find(Object id) {
        Long identifier = (Long) id;
        return notifications.get(identifier);
    }
}
