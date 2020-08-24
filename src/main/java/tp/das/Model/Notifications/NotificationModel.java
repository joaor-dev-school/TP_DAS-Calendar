package tp.das.Model.Notifications;

import tp.das.Model.Database.EntityModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Utilizador.UserModel;

public class NotificationModel implements EntityModel {
    private Long id;
    private UserModel user;
    private EventModel event;
    private Boolean read;

    public NotificationModel(UserModel user, EventModel event) {
        this.user = user;
        this.event = event;
        this.read = false;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
