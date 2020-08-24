package tp.das.DTOs.Event;

import tp.das.Model.Event.PeriodicityDaysTypeEnum;

import java.util.List;

public class PeriodicityRuleResponseDTO {
    private List<Integer> days;
    private List<Integer> daysNot;
    private PeriodicityDaysTypeEnum daysType;
    private Integer step;

    public PeriodicityRuleResponseDTO(List<Integer> days, List<Integer> daysNot, PeriodicityDaysTypeEnum daysType, Integer step) {
        this.days = days;
        this.daysNot = daysNot;
        this.daysType = daysType;
        this.step = step;
    }

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
