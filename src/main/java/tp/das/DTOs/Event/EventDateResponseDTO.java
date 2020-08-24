package tp.das.DTOs.Event;

public class EventDateResponseDTO {
    private Long timestamp;
    private Long duration;

    public EventDateResponseDTO(Long timestamp, Long duration) {
        this.timestamp = timestamp;
        this.duration = duration;
    }

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
