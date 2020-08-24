package tp.das.DTOs.Event;

import tp.das.Model.Event.EventTypesEnum;

import java.util.List;

public class EventInviteDetailsResponseDTO extends EventDetailsResponseDTO {
    private PeriodicityResponseDTO periodicity;

    public EventInviteDetailsResponseDTO() {
        super();
    }

    public EventInviteDetailsResponseDTO(Long eventId, String name, EventTypesEnum type, List<EventDateResponseDTO> dates, EventParticipantResponseDTO creator, List<EventParticipantResponseDTO> participants, PeriodicityResponseDTO periodicity) {
        super(eventId, name, type, dates, creator, participants);
        this.periodicity = periodicity;
    }

    public EventInviteDetailsResponseDTO(EventDetailsResponseDTO event, PeriodicityResponseDTO periodicity) {
        super(event.getEventId(), event.getName(), event.getType(), event.getDates(), event.getCreator(), event.getParticipants());
        this.periodicity = periodicity;
    }

    public PeriodicityResponseDTO getPeriodicity() {
        return periodicity;
    }

    public EventInviteDetailsResponseDTO setPeriodicity(PeriodicityResponseDTO periodicity) {
        this.periodicity = periodicity;
        return this;
    }
}
