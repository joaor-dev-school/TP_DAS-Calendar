package tp.das.Model.Event;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.Strategy.ISchedulingStrategy;
import tp.das.Model.Utilizador.UserModel;

import java.util.ArrayList;
import java.util.List;

public class EventSchedulingModel extends EventModel {

    private DateModel selectedDate;

    private ISchedulingStrategy schedulingStrategy;

    public EventSchedulingModel(String name, UserModel creator, EventTypesEnum type, List<EventParticipantModel> participants,
                                List<DateModel> dates, ISchedulingStrategy schedulingStrategy) {
        super(name, creator, type, participants, dates);
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
        List<DateModel> res = new ArrayList<>();
        if (this.selectedDate != null) {
            res.add(selectedDate);
        }
        return res;
    }
}
