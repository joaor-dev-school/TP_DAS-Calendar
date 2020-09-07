package tp.das.Model.Store.Model;

import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Notifications.NotificationModel;
import tp.das.Model.Utilizador.UserModel;

import java.util.List;

public interface ICalendarStoreData {
    List<AuthModel> getAccounts();


    List<EventModel> getEvents();


    List<UserModel> getUsers();


    List<NotificationModel> getNotifications();

}
