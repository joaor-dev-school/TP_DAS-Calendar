package tp.das.Model.Event.Decorators;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Event.EventPeriodicityRule;

import java.util.*;

public class WeekPeriodicEventDecorator implements IPeriodicEventDecorator {
    private static final long STEP_MS = 1000 * 60 * 60 * 24 * 7L;

    private IPeriodicEventDecorator event;
    private EventPeriodicityRule rule;

    public WeekPeriodicEventDecorator(IPeriodicEventDecorator event, EventPeriodicityRule rule) {
        this.event = event;
        this.rule = rule;
    }

    @Override
    public List<DateModel> getDates() {
        final List<DateModel> possibleDates = event.getDates();
        if (possibleDates.isEmpty()) {
            return possibleDates;
        }
        final DateModel refDate = event.getReferenceDate();
        final long refDayOfWeek = this.resolveWeekPrecision0(refDate.getTimestamp());
        final long stepTime = STEP_MS * rule.getStep();
        final Set<Integer> dayValuesSet = rule.getDays() != null ? new HashSet<>(rule.getDays()) : null;
        final Set<Integer> dayNotValuesSet = rule.getDaysNot() != null ? new HashSet<>(rule.getDaysNot()) : null;

        final List<DateModel> datesRes = new ArrayList<>();
        for (DateModel possibleDate : possibleDates) {
            final long timestamp = possibleDate.getTimestamp();
            final long timeDiff = Math.abs(refDayOfWeek - this.resolveWeekPrecision0(timestamp));
            if (timeDiff % stepTime != 0 || this.shouldRemoveDate(possibleDate, dayValuesSet, dayNotValuesSet)) {
                continue;
            }
            datesRes.add(possibleDate);
        }
        return datesRes;
    }

    @Override
    public DateModel getReferenceDate() {
        return this.event.getReferenceDate();
    }

    private boolean shouldRemoveDate(DateModel possibleDate, Set<Integer> dayValuesSet, Set<Integer> dayNotValuesSet) {
        final Integer dayValue = this.getDayOfTheWeek(possibleDate.getTimestamp());
        return dayValuesSet != null && !dayValuesSet.contains(dayValue)
                || dayNotValuesSet != null && dayNotValuesSet.contains(dayValue);
    }

    private int getDayOfTheWeek(long time) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private long resolveWeekPrecision0(Long timestamp) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
