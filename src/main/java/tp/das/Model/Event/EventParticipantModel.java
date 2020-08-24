package tp.das.Model.Event;

import tp.das.Model.Utilizador.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventParticipantModel {
    private UserModel userModel;
    private EventParticipantStatesEnum state;
    private List<Long> preferredTimestamps;
    private List<Long> acceptableTimestamps;

    public EventParticipantModel(UserModel userModel, EventParticipantStatesEnum state) {
        this(userModel, state, new ArrayList<>(), new ArrayList<>());
        this.userModel = userModel;
        this.state = state;
    }

    public EventParticipantModel(UserModel userModel, EventParticipantStatesEnum state,
                                 List<Long> preferredTimestamps, List<Long> acceptableTimestamps) {
        this.userModel = userModel;
        this.state = state;
        this.preferredTimestamps = preferredTimestamps;
        this.acceptableTimestamps = acceptableTimestamps;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public EventParticipantStatesEnum getState() {
        return state;
    }

    public void setState(EventParticipantStatesEnum state) {
        this.state = state;
    }

    public List<Long> getPreferredTimestamps() {
        return preferredTimestamps;
    }

    public void setPreferredTimestamps(List<Long> preferredTimestamps) {
        this.preferredTimestamps = preferredTimestamps;
    }

    public List<Long> getAcceptableTimestamps() {
        return acceptableTimestamps;
    }

    public void setAcceptableTimestamps(List<Long> acceptableTimestamps) {
        this.acceptableTimestamps = acceptableTimestamps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventParticipantModel that = (EventParticipantModel) o;
        return userModel.getId().equals(that.userModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userModel.getId());
    }
}
