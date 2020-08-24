package tp.das.DTOs.Event;

import tp.das.Model.Event.EventTypesEnum;

import java.util.List;

public class EventDetailsResponseDTO {
    private Long eventId;
    private String name;
    private EventTypesEnum type;
    private List<EventDateResponseDTO> dates;
    private EventParticipantResponseDTO creator;
    private List<EventParticipantResponseDTO> participants;

    public EventDetailsResponseDTO() {
    }

    public EventDetailsResponseDTO(Long eventId, String name, EventTypesEnum type, List<EventDateResponseDTO> dates, EventParticipantResponseDTO creator, List<EventParticipantResponseDTO> participants) {
        this.eventId = eventId;
        this.name = name;
        this.type = type;
        this.dates = dates;
        this.creator = creator;
        this.participants = participants;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public EventTypesEnum getType() {
        return type;
    }

    public List<EventDateResponseDTO> getDates() {
        return dates;
    }

    public EventParticipantResponseDTO getCreator() {
        return creator;
    }

    public List<EventParticipantResponseDTO> getParticipants() {
        return participants;
    }

    public EventDetailsResponseDTO setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public EventDetailsResponseDTO setName(String name) {
        this.name = name;
        return this;
    }

    public EventDetailsResponseDTO setType(EventTypesEnum type) {
        this.type = type;
        return this;
    }

    public EventDetailsResponseDTO setDates(List<EventDateResponseDTO> dates) {
        this.dates = dates;
        return this;
    }

    public EventDetailsResponseDTO setCreator(EventParticipantResponseDTO creator) {
        this.creator = creator;
        return this;
    }

    public EventDetailsResponseDTO setParticipants(List<EventParticipantResponseDTO> participants) {
        this.participants = participants;
        return this;
    }
}
