package tp.das.DTOs.Event;

public class EventNotificationResponseDTO {
    private Long eventId;
    private String name;
    private String creatorName;
    private Long nextDateTimestamp;
    private Integer totalParticipants;
    private Boolean read;

    public EventNotificationResponseDTO setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public EventNotificationResponseDTO setName(String name) {
        this.name = name;
        return this;
    }

    public EventNotificationResponseDTO setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public EventNotificationResponseDTO setNextDateTimestamp(Long nextDateTimestamp) {
        this.nextDateTimestamp = nextDateTimestamp;
        return this;
    }

    public EventNotificationResponseDTO setTotalParticipants(Integer totalParticipants) {
        this.totalParticipants = totalParticipants;
        return this;
    }

    public EventNotificationResponseDTO setRead(Boolean read) {
        this.read = read;
        return this;
    }
}
