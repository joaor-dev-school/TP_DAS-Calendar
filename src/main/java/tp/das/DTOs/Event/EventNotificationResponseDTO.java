package tp.das.DTOs.Event;

public class EventNotificationResponseDTO {
    private Long id;
    private Long eventId;
    private String name;
    private String creatorName;
    private Long nextDateTimestamp;
    private Integer totalParticipants;
    private Boolean read;

    public EventNotificationResponseDTO(Long id) {
        this.id = id;
    }

    public EventNotificationResponseDTO(Long id, Long eventId, String name, String creatorName, Long nextDateTimestamp, Integer totalParticipants, Boolean read) {
        this.id = id;
        this.eventId = eventId;
        this.name = name;
        this.creatorName = creatorName;
        this.nextDateTimestamp = nextDateTimestamp;
        this.totalParticipants = totalParticipants;
        this.read = read;
    }

    public EventNotificationResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

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

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public Long getNextDateTimestamp() {
        return nextDateTimestamp;
    }

    public Integer getTotalParticipants() {
        return totalParticipants;
    }

    public Boolean getRead() {
        return read;
    }
}
