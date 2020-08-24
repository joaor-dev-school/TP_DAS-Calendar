package tp.das.Model.Event.Decorators;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Event.EventPeriodicityRule;
import tp.das.Model.Event.PeriodicityDaysTypeEnum;

import java.util.*;

public class PeriodicEventDecorator extends EventModel {
    private static final long DAY_MS = 1000 * 60 * 60 * 24L;

    private List<EventPeriodicityRule> rules;

    private Long rangeTimestamp;

    private List<DateModel> datesCache;

    public PeriodicEventDecorator(EventModel eventModel, List<EventPeriodicityRule> rules,
                                  Long rangeTimestamp) {
        super(eventModel);
        this.rules = rules;
        this.rangeTimestamp = rangeTimestamp;
    }

    public List<EventPeriodicityRule> getRules() {
        return rules;
    }

    public void setRules(List<EventPeriodicityRule> rules) {
        this.rules = rules;
    }

    public Long getRangeTimestamp() {
        return rangeTimestamp;
    }

    public void setRangeTimestamp(Long rangeTimestamp) {
        this.rangeTimestamp = rangeTimestamp;
    }

    public List<DateModel> getDates() {
        final List<DateModel> initialDatesModels = super.getDates();
        if (initialDatesModels.size() != 1) {
            return initialDatesModels;
        }
        if (datesCache != null) {
            return datesCache;
        }

        final DateModel firstDateModel = initialDatesModels.get(0);
        final long duration = firstDateModel.getDuration();
        final Map<Long, DateModel> datesModelsMap = new HashMap<>();

        for (EventPeriodicityRule rule : rules) {
            final List<Integer> dayValues = rule.getDays();
            final List<Integer> dayNotValues = rule.getDaysNot();
            final Set<Integer> dayValuesSet = dayValues != null ? new HashSet<>(dayValues) : null;
            final Set<Integer> dayNotValuesSet = dayNotValues != null ? new HashSet<>(dayNotValues) : null;

            long timestamp = firstDateModel.getTimestamp();
            while (timestamp < rangeTimestamp) {
                final long dayTimePrecision0 = this.resolveDayTimePrecision0(timestamp);
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(dayTimePrecision0);

                final Integer dayValue = this.resolveDayValue(rule.getDaysType(), calendar);
                if (dayValues != null && !dayValuesSet.contains(dayValue)
                        || dayNotValues != null && dayNotValuesSet.contains(dayValue)) {
                    datesModelsMap.remove(calendar.getTimeInMillis());
                } else {
                    datesModelsMap.put(calendar.getTimeInMillis(), new DateModel(timestamp, duration));
                }
                timestamp += DAY_MS * rule.getStep();
            }
        }
        return this.datesCache = new ArrayList<>(datesModelsMap.values());
    }

    private Integer resolveDayValue(PeriodicityDaysTypeEnum daysType, Calendar calendar) {
        if (daysType.equals(PeriodicityDaysTypeEnum.DAY)) {
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private long resolveDayTimePrecision0(Long timestamp) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

}
