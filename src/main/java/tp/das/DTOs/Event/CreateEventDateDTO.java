package tp.das.DTOs.Event;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateEventDateDTO {
    @NotNull
    @Min(0)
    private Long timestamp;

    @NotNull
    @Min(EventDTOConstants.MIN_DURATION)
    @Max(EventDTOConstants.MAX_DURATION)
    private Long duration;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
