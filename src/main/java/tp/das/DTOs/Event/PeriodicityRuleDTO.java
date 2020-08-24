package tp.das.DTOs.Event;

import tp.das.Model.Event.PeriodicityDaysTypeEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class PeriodicityRuleDTO {
    @Max(EventDTOConstants.MAX_DAY)
    @Size(max = EventDTOConstants.MAX_DAYS_LENGTH)
    private List<Integer> days;

    @Max(EventDTOConstants.MAX_DAY)
    @Size(max = EventDTOConstants.MAX_DAYS_LENGTH)
    private List<Integer> daysNot;

    @NotNull
    private PeriodicityDaysTypeEnum daysType;

    @NotNull
    @Max(EventDTOConstants.MAX_DAY)
    private Integer step;

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public List<Integer> getDaysNot() {
        return daysNot;
    }

    public void setDaysNot(List<Integer> daysNot) {
        this.daysNot = daysNot;
    }

    public PeriodicityDaysTypeEnum getDaysType() {
        return daysType;
    }

    public void setDaysType(PeriodicityDaysTypeEnum daysType) {
        this.daysType = daysType;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
