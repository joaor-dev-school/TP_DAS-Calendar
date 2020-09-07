package tp.das.Model.Event.Decorators;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Event.EventPeriodicityRule;
import tp.das.Model.Event.PeriodicityDaysTypeEnum;

import java.util.*;

public class PeriodicEventDecorator extends EventModel implements IPeriodicEventDecorator {
    private static final long DAY_MS = 1000 * 60 * 60 * 24L;

    private List<EventPeriodicityRule> rules;

    private Long rangeTimestamp;

    private List<DateModel> dates;

    public PeriodicEventDecorator(EventModel eventModel, List<EventPeriodicityRule> rules,
                                  Long rangeTimestamp) {
        super(eventModel);
        this.rules = rules;
        this.rangeTimestamp = rangeTimestamp;
        this.dates = this.getEventDecorated().getDates();
        if (this.dates.size() == 0) {
            throw new RuntimeException("No valid dates for the event");
        }
    }

    private IPeriodicEventDecorator getEventDecorated() {
        IPeriodicEventDecorator eventDecorated = this;
        for (EventPeriodicityRule rule : this.rules) {
            if (rule.getDaysType() == PeriodicityDaysTypeEnum.DAY) {
                eventDecorated = new DayPeriodicEventDecorator(eventDecorated, rule);
                continue;
            }
            eventDecorated = new WeekPeriodicEventDecorator(eventDecorated, rule);
        }
        return eventDecorated;
    }

    public List<EventPeriodicityRule> getRules() {
        return rules;
    }

    public Long getRangeTimestamp() {
        return rangeTimestamp;
    }

    @Override
    public List<DateModel> getDates() {
        if (dates != null) {
            return dates;
        }

        final List<DateModel> initialDatesModels = super.getDates();
        if (initialDatesModels.size() != 1 || this.rules.size() < 1) {
            return initialDatesModels;
        }

        final DateModel firstDateModel = initialDatesModels.get(0);
        final long firstDateTimestamp = firstDateModel.getTimestamp();
        final long duration = Math.min(firstDateModel.getDuration(),
                Math.abs(this.resolveDayTimePrecision0(firstDateTimestamp + DAY_MS) - firstDateTimestamp));
        final List<DateModel> datesRes = new ArrayList<>();

        for (long timestamp = firstDateModel.getTimestamp(); timestamp < rangeTimestamp; timestamp += DAY_MS) {
            datesRes.add(new DateModel(timestamp, duration));
        }

        return datesRes;
    }

    @Override
    public DateModel getReferenceDate() {
        final List<DateModel> dates = super.getDates();
        if (dates.size() < 1) {
            return null;
        }
        return dates.get(0);
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
