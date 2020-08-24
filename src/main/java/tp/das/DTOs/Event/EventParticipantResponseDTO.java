package tp.das.DTOs.Event;

import tp.das.Model.Event.EventParticipantStatesEnum;

public class EventParticipantResponseDTO {
    private Long participantId;
    private String name;
    private EventParticipantStatesEnum state;

    public EventParticipantResponseDTO(Long participantId, String name, EventParticipantStatesEnum state) {
        this.participantId = participantId;
        this.name = name;
        this.state = state;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventParticipantStatesEnum getState() {
        return state;
    }

    public void setState(EventParticipantStatesEnum state) {
        this.state = state;
    }
}
