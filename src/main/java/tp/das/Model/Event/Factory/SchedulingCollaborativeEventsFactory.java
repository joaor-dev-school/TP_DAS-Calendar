package tp.das.Model.Event.Factory;

import tp.das.DTOs.Event.CreateEventDTO;
import tp.das.DTOs.Event.CreateSchedulingEventDTO;
import tp.das.Model.Event.*;
import tp.das.Model.Event.Strategy.SchedulingCollaborativeEventStrategy;
import tp.das.Model.Utilizador.UserModel;

import java.util.List;

public class SchedulingCollaborativeEventsFactory extends EventsFactory {
    @Override
    public EventModel getEvent(CreateEventDTO createEventDTO) {
        return this.getEvent(createEventDTO, null);
    }

    @Override
    public EventModel getEvent(CreateEventDTO createEventDTO, Long id) {
        final UserModel creator = super.resolveCreator(createEventDTO.getCreatorId());
        final List<EventParticipantModel> participants = super.resolveParticipants(createEventDTO, creator);
        final List<DateModel> date = super.resolveDates(((CreateSchedulingEventDTO) createEventDTO).getDates());
        final String name = createEventDTO.getEventName();
        final EventTypesEnum type = ((CreateSchedulingEventDTO) createEventDTO).getType();

        return new EventSchedulingModel(id, name, creator, type, participants, date, new SchedulingCollaborativeEventStrategy());
    }
}
