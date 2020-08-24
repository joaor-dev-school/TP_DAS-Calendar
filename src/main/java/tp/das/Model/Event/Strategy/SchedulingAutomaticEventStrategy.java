package tp.das.Model.Event.Strategy;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.EventParticipantModel;
import tp.das.Model.Shared.Pair;
import tp.das.Model.Utilizador.UserModel;
import tp.das.Model.Utilizador.UserPreferenceModel;

import java.util.*;

public class SchedulingAutomaticEventStrategy implements ISchedulingStrategy {
    private static final int NO = 0;
    private static final int ACCEPT = 1;
    private static final int PREFER = 2;

    @Override
    public DateModel selectDate(List<DateModel> dates, List<EventParticipantModel> participants) {
        final Map<Long, List<Pair<Long, Integer>>> datesStateMap = new HashMap<>();
        for (DateModel date : dates) {
            final List<Pair<Long, Integer>> dateStates = new ArrayList<>();
            for (EventParticipantModel participant : participants) {
                final UserModel userModel = participant.getUserModel();
                dateStates.add(new Pair<>(userModel.getId(), this.resolveDateState(date, userModel)));
            }
            datesStateMap.put(date.getTimestamp(), dateStates);
        }
        DateModel acceptableResult = null;
        for (DateModel date : dates) {
            int state = NO;
            for (Pair<Long, Integer> dateState : datesStateMap.get(date.getTimestamp())) {
                if (dateState.b == NO) {
                    state = NO;
                    break;
                }
                if (dateState.b == ACCEPT || state == ACCEPT) {
                    state = ACCEPT;
                    continue;
                }
                state = PREFER;
            }
            if (state == PREFER) {
                return date;
            }
            if (state == ACCEPT) {
                acceptableResult = date;
            }
        }
        if (acceptableResult == null) {
            throw new RuntimeException("Could not select a date from the given dates/participants");
        }
        return null;
    }

    private Map<Long, Integer> createDatesStateMap(List<DateModel> dates) {
        final Map<Long, Integer> datesStateMap = new HashMap<>();
        for (DateModel date : dates) {
            datesStateMap.put(date.getTimestamp(), NO);
        }
        return datesStateMap;
    }

    private int resolveDateState(DateModel date, UserModel user) {
        if (this.resolveDateState(date, user.getPreferences().getPreferred(), PREFER) == PREFER) {
            return PREFER;
        }
        if (this.resolveDateState(date, user.getPreferences().getAcceptable(), ACCEPT) == ACCEPT) {
            return ACCEPT;
        }
        return NO;
    }

    private int resolveDateState(DateModel date, List<UserPreferenceModel> list, int successResult) {
        final long timestamp = date.getTimestamp();
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        for (UserPreferenceModel pref : list) {
            final Calendar cFrom = Calendar.getInstance();
            cFrom.setTimeInMillis(pref.getFromTimestamp());
            final Calendar cTo = Calendar.getInstance();
            cTo.setTimeInMillis(pref.getToTimestamp());

            switch (pref.getType()) {
                case DAY: {
                    final int[] comparisons = new int[]{Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND};
                    int lastCompareValue = -1;
                    for (int comparisonField : comparisons) {
                        final int compare = lastCompareValue = this.compareDateValue(comparisonField, c, cFrom, cTo);
                        if (compare == 0) {
                            return successResult;
                        }
                        if (compare == -1) {
                            break;
                        }
                    }
                    if (lastCompareValue == -1) {
                        break;
                    }
                    return successResult;
                }
                case WEEK:
                    if (this.compareDateValue(Calendar.DAY_OF_WEEK, c, cFrom, cTo) == -1) {
                        break;
                    }
                    return successResult;
                case MONTH:
                    if (this.compareDateValue(Calendar.WEEK_OF_MONTH, c, cFrom, cTo) == -1) {
                        break;
                    }
                    return successResult;
                case YEAR:
                    if (this.compareDateValue(Calendar.MONTH, c, cFrom, cTo) == -1) {
                        break;
                    }
                    return successResult;
                default:
                    break;
            }
        }
        return NO;
    }

    private int compareDateValue(int field, Calendar c, Calendar cMin, Calendar cMax) {
        final int value = c.get(field);
        final int valueMin = cMin.get(field);
        final int valueMax = cMax.get(field);
        if (value >= valueMin && value <= valueMax) {
            return valueMin == valueMax ? 1 : 0;
        }
        return -1;
    }
}
