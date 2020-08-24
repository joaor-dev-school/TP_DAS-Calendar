package tp.das.Model.Event.Strategy;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.EventParticipantModel;

import java.util.List;

public interface ISchedulingStrategy {
    DateModel selectDate(List<DateModel> dates, List<EventParticipantModel> participants);
}
