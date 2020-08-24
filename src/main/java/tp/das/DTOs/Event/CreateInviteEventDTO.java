package tp.das.DTOs.Event;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CreateInviteEventDTO extends CreateEventDTO {
    @NotNull
    @Valid
    private CreateEventDateDTO date;

    @Valid
    private PeriodicityDTO periodicity;

    public CreateEventDateDTO getDate() {
        return date;
    }

    public void setDate(CreateEventDateDTO date) {
        this.date = date;
    }

    public PeriodicityDTO getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(PeriodicityDTO periodicity) {
        this.periodicity = periodicity;
    }
}
