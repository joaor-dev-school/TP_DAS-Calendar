package tp.das.DTOs.Event;

import tp.das.Model.Event.EventTypesEnum;

import java.util.List;

public class EventListItemResponseDTO {
    private Long eventId;
    private Long creator;
    private String name;
    private EventTypesEnum type;
    private List<EventDateResponseDTO> dates;

    public EventListItemResponseDTO(Long eventId, Long creator, String name, EventTypesEnum type, List<EventDateResponseDTO> dates) {
        this.eventId = eventId;
        this.creator = creator;
        this.name = name;
        this.type = type;
        this.dates = dates;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventTypesEnum getType() {
        return type;
    }

    public void setType(EventTypesEnum type) {
        this.type = type;
    }

    public List<EventDateResponseDTO> getDates() {
        return dates;
    }

    public void setDates(List<EventDateResponseDTO> dates) {
        this.dates = dates;
    }
}
