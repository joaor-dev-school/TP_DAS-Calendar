package tp.das.Model.Event;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.Strategy.ISchedulingStrategy;
import tp.das.Model.Utilizador.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EventSchedulingModel extends EventModel {

    private DateModel selectedDate;

    private ISchedulingStrategy schedulingStrategy;

    public EventSchedulingModel(Long id, String name, UserModel creator, EventTypesEnum type, List<EventParticipantModel> participants,
                                List<DateModel> dates, ISchedulingStrategy schedulingStrategy) {
        super(id, name, creator, type, participants, dates);
        this.schedulingStrategy = schedulingStrategy;
        this.updateSelectedDate();
    }

    public DateModel getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(DateModel selectedDate) {
        this.selectedDate = selectedDate;
    }

    public DateModel updateSelectedDate() {
        return this.selectedDate = schedulingStrategy.selectDate(super.getDates(), super.getParticipants());
    }

    public DateModel updateSelectedDate(DateModel dateProposed) {
        if (dateProposed == null) {
            return this.updateSelectedDate();
        }
        final boolean canChangeDate = schedulingStrategy.selectDate(super.getDates(), super.getParticipants()) != null;
        return this.selectedDate = canChangeDate ? dateProposed : null;
    }

    @Override
    public List<DateModel> getDates() {
        if (this.selectedDate == null) {
            return super.getDates();
        }
        return new ArrayList<>(Collections.singletonList(selectedDate));
    }
}
