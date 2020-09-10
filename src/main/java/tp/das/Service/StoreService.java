package tp.das.Service;

import tp.das.DTOs.Auth.LoginDTO;
import tp.das.DTOs.Auth.RegisterDTO;
import tp.das.DTOs.Store.FilenamesListResponseDTO;
import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Database.DataMappers.AuthDataMapper;
import tp.das.Model.Database.DataMappers.EventsDataMapper;
import tp.das.Model.Database.DataMappers.NotificationsDataMapper;
import tp.das.Model.Database.DataMappers.UsersDataMapper;
import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Notifications.NotificationModel;
import tp.das.Model.Store.Adapter.ICalendarDataStoreAdapter;
import tp.das.Model.Store.Adapter.TextCalendarDataStoreAdapter;
import tp.das.Model.Store.Builder.CalendarStoreBuilder;
import tp.das.Model.Store.Model.ICalendarStoreData;
import tp.das.Model.Utilizador.UserModel;

import java.util.List;

public class StoreService {
    private static ICalendarDataStoreAdapter storeAdapter = new TextCalendarDataStoreAdapter();

    public static FilenamesListResponseDTO filenames() {
        try {
            return new FilenamesListResponseDTO(storeAdapter.filenames());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static void save(String filename) {
        try {
            storeAdapter.write(filename, new CalendarStoreBuilder()
                    .setAccounts(AuthDataMapper.getInstance().findAll())
                    .setEvents(EventsDataMapper.getInstance().findAll())
                    .setNotifications(NotificationsDataMapper.getInstance().findAll())
                    .setUsers(UsersDataMapper.getInstance().findAll())
                    .getCalendarData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void load(String filename) {
        try {
            final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
            final ICalendarStoreData data = storeAdapter.read(filename);
            for (AuthModel authModel : AuthDataMapper.getInstance().findAll()) {
                unitOfWork.registerDeleted(authModel);
            }
            for (AuthModel authModel : data.getAccounts()) {
                unitOfWork.registerNew(authModel);
            }
            for (UserModel userModel : UsersDataMapper.getInstance().findAll()) {
                unitOfWork.registerDeleted(userModel);
            }
            for (UserModel userModel : data.getUsers()) {
                unitOfWork.registerNew(userModel);
            }
            for (EventModel eventModel : EventsDataMapper.getInstance().findAll()) {
                unitOfWork.registerDeleted(eventModel);
            }
            for (EventModel eventModel : data.getEvents()) {
                unitOfWork.registerNew(eventModel);
            }
            for (NotificationModel notificationModel : NotificationsDataMapper.getInstance().findAll()) {
                unitOfWork.registerDeleted(notificationModel);
            }
            for (NotificationModel notificationModel : data.getNotifications()) {
                unitOfWork.registerNew(notificationModel);
            }
            unitOfWork.commit();
            NotificationsService.getInstance().clearCache();
            EventsService.getInstance().cleanAllUsersRecord();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void delete(String filename) {
        try {
            storeAdapter.deleteFile(filename);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void reset() {
        try {
            final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
            for (EventModel eventModel : EventsDataMapper.getInstance().findAll()) {
                unitOfWork.registerDeleted(eventModel);
            }
            for (NotificationModel notificationModel : NotificationsDataMapper.getInstance().findAll()) {
                unitOfWork.registerDeleted(notificationModel);
            }
            unitOfWork.commit();
            NotificationsService.getInstance().clearCache();
            EventsService.getInstance().cleanAllUsersRecord();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
