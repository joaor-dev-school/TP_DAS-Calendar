package tp.das.Model.Event;

import tp.das.Model.Database.EntityModel;
import tp.das.Model.Utilizador.UserModel;

import java.util.Collections;
import java.util.List;

public class EventModel implements EntityModel {
    private Long id;
    private String name;
    private UserModel creator;
    private List<EventParticipantModel> participants;
    private List<DateModel> dates;
    private EventTypesEnum type;

    public EventModel() {
        // Empty by design.
    }

    public EventModel(String name, UserModel creator, EventTypesEnum type, List<EventParticipantModel> participants, DateModel date) {
        this(null, name, creator, type, participants, Collections.singletonList(date));
    }

    public EventModel(Long id, String name, UserModel creator, EventTypesEnum type, List<EventParticipantModel> participants, DateModel date) {
        this(id, name, creator, type, participants, Collections.singletonList(date));
    }

    public EventModel(String name, UserModel creator, EventTypesEnum type, List<EventParticipantModel> participants, List<DateModel> dates) {
        this(null, name, creator, type, participants, dates);
    }

    public EventModel(Long id, String name, UserModel creator, EventTypesEnum type, List<EventParticipantModel> participants, List<DateModel> dates) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.participants = participants;
        this.dates = dates;
        this.type = type;
    }

    public EventModel(EventModel event) {
        this.id = event.id;
        this.name = event.name;
        this.creator = event.creator;
        this.participants = event.participants;
        this.dates = event.dates;
        this.type = event.type;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventTypesEnum getType() {
        return type;
    }

    public void setType(EventTypesEnum type) {
        this.type = type;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public List<EventParticipantModel> getParticipants() {
        return participants;
    }

    public void setParticipants(List<EventParticipantModel> participants) {
        this.participants = participants;
    }

    public List<DateModel> getDates() {
        return dates;
    }

    public void setDates(List<DateModel> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "id=" + id +
                ", creator=" + creator +
                ", participants=" + participants +
                ", dates=" + dates +
                ", type=" + type +
                '}';
    }
}
