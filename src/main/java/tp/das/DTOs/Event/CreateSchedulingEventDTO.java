package tp.das.DTOs.Event;

import tp.das.Model.Event.EventTypesEnum;
import tp.das.Validators.Events.SchedulingType;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateSchedulingEventDTO extends CreateEventDTO {
    @NotNull
    private List<CreateEventDateDTO> dates;

    @NotNull
    @SchedulingType
    private EventTypesEnum type;

    public List<CreateEventDateDTO> getDates() {
        return dates;
    }

    public void setDates(List<CreateEventDateDTO> dates) {
        this.dates = dates;
    }

    public EventTypesEnum getType() {
        return type;
    }

    public void setType(EventTypesEnum type) {
        this.type = type;
    }
}
