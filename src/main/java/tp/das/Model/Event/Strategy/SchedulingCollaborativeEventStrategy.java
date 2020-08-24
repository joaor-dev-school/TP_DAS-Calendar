package tp.das.Model.Event.Strategy;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.EventParticipantModel;
import tp.das.Model.Event.EventParticipantStatesEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulingCollaborativeEventStrategy implements ISchedulingStrategy {
    @Override
    public DateModel selectDate(List<DateModel> dates, List<EventParticipantModel> participants) {
        if (!participants.stream().allMatch((EventParticipantModel participant) ->
                participant.getState() == EventParticipantStatesEnum.ACCEPTED)) {
            return null;
        }
        if (Math.random() < 0.5) {
            return this.selectDateFirstCriteria(dates, participants,
                    EventParticipantModel::getPreferredTimestamps);
        }

        return this.selectDateFirstCriteria(dates, participants,
                (EventParticipantModel participant) -> {
                    List<Long> list = participant.getAcceptableTimestamps();
                    list.addAll(participant.getPreferredTimestamps());
                    return list;
                });
    }

    private DateModel selectDateFirstCriteria(List<DateModel> dates, List<EventParticipantModel> participants,
                                              ParticipantsResolver participantsResolver) {
        final Map<Long, DateModel> timestampDateMap = new HashMap<>();
        final Map<Long, Integer> datesCount = new HashMap<>();
        DateModel maxCountDate = null;
        Integer maxCount = -1;

        for (DateModel dateModel : dates) {
            final Long timestamp = dateModel.getTimestamp();
            timestampDateMap.put(timestamp, dateModel);
            datesCount.put(timestamp, 0);
        }

        for (EventParticipantModel participant : participants) {
            for (Long timestamp : participantsResolver.resolve(participant)) {
                Integer count = datesCount.get(timestamp);
                if (count == null) {
                    continue;
                }
                ++count;
                datesCount.put(timestamp, count);
                if (count > maxCount) {
                    maxCount = count;
                    maxCountDate = timestampDateMap.get(timestamp);
                }
            }
        }

        return maxCountDate;
    }

    interface ParticipantsResolver {
        List<Long> resolve(EventParticipantModel participant);
    }
}
