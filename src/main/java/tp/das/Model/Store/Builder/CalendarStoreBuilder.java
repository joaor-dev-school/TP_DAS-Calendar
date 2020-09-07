package tp.das.Model.Store.Builder;

import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Notifications.NotificationModel;
import tp.das.Model.Store.Model.CalendarStoreData;
import tp.das.Model.Store.Model.ICalendarStoreData;
import tp.das.Model.Utilizador.UserModel;

import java.util.List;

public class CalendarStoreBuilder {
    private List<AuthModel> accounts;
    private List<EventModel> events;
    private List<UserModel> users;
    private List<NotificationModel> notifications;

    public CalendarStoreBuilder setAccounts(List<AuthModel> accounts) {
        this.accounts = accounts;
        return this;
    }

    public CalendarStoreBuilder setEvents(List<EventModel> events) {
        this.events = events;
        return this;
    }

    public CalendarStoreBuilder setUsers(List<UserModel> users) {
        this.users = users;
        return this;
    }

    public CalendarStoreBuilder setNotifications(List<NotificationModel> notifications) {
        this.notifications = notifications;
        return this;
    }

    public ICalendarStoreData getCalendarData() {
        if (isCalendarDataCompleted())
            return createCalendarData();
        throw new RuntimeException("Internal error getting the calendar data.");
    }

    private boolean isCalendarDataCompleted() {
        return accounts != null && events != null
                && users != null && notifications != null;
    }

    private CalendarStoreData createCalendarData() {
        return new CalendarStoreData(accounts, events, users, notifications);
    }
}
