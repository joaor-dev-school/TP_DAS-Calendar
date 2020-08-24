package tp.das.DTOs.Event;

import tp.das.Validators.Events.SchedulingPreference;
import tp.das.Validators.Events.UniqueTimestamps;

import javax.validation.constraints.Size;
import java.util.List;

@SchedulingPreference
public class EventSchedulingStatusDTO extends ChangeEventStatusDTO {
    @Size(max = EventDTOConstants.MAX_DATES)
    @UniqueTimestamps
    private List<Long> preferredTimestamps;

    @Size(max = EventDTOConstants.MAX_DATES)
    @UniqueTimestamps
    private List<Long> acceptableTimestamps;

    public List<Long> getPreferredTimestamps() {
        return preferredTimestamps;
    }

    public void setPreferredTimestamps(List<Long> preferredTimestamps) {
        this.preferredTimestamps = preferredTimestamps;
    }

    public List<Long> getAcceptableTimestamps() {
        return acceptableTimestamps;
    }

    public void setAcceptableTimestamps(List<Long> acceptableTimestamps) {
        this.acceptableTimestamps = acceptableTimestamps;
    }
}
