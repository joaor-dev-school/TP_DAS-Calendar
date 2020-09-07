package tp.das.Model.Store.Model;

import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Notifications.NotificationModel;
import tp.das.Model.Utilizador.UserModel;

import java.util.List;

public class CalendarStoreData implements ICalendarStoreData {
    private List<AuthModel> accounts;
    private List<EventModel> events;
    private List<UserModel> users;
    private List<NotificationModel> notifications;

    public CalendarStoreData(List<AuthModel> accounts, List<EventModel> events, List<UserModel> users, List<NotificationModel> notifications) {
        this.accounts = accounts;
        this.events = events;
        this.users = users;
        this.notifications = notifications;
    }

    @Override
    public List<AuthModel> getAccounts() {
        return accounts;
    }

    @Override
    public List<EventModel> getEvents() {
        return events;
    }

    @Override
    public List<UserModel> getUsers() {
        return users;
    }

    @Override
    public List<NotificationModel> getNotifications() {
        return notifications;
    }
}
