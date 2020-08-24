package tp.das.Model.Event.Factory;

import tp.das.DTOs.Event.*;
import tp.das.Model.Event.*;
import tp.das.Model.Event.Decorators.PeriodicEventDecorator;
import tp.das.Model.Utilizador.UserModel;

import java.util.List;
import java.util.stream.Collectors;

public class InviteEventsFactory extends EventsFactory {
    @Override
    public EventModel getEvent(CreateEventDTO createEventDTO) {
        return this.getEvent(createEventDTO, null);
    }

    @Override
    public EventModel getEvent(CreateEventDTO createEventDTO, Long id) {
        final CreateInviteEventDTO createInviteDTO = (CreateInviteEventDTO) createEventDTO;
        final UserModel creator = super.resolveCreator(createEventDTO.getCreatorId());
        final List<EventParticipantModel> participants = super.resolveParticipants(createEventDTO, creator);


        final PeriodicityDTO periodicityDTO = createInviteDTO.getPeriodicity();
        final CreateEventDateDTO dateDTO = createInviteDTO.getDate();
        final DateModel date = new DateModel(dateDTO.getTimestamp(), dateDTO.getDuration());
        final String name = createEventDTO.getEventName();
        final EventModel eventModel = new EventModel(id, name, creator, EventTypesEnum.INVITE, participants, date);

        if (periodicityDTO == null) {
            return eventModel;
        }

        return new PeriodicEventDecorator(eventModel,
                periodicityDTO.getRules().stream()
                        .map((PeriodicityRuleDTO rule) -> new EventPeriodicityRule(rule.getDays(), rule.getDaysNot(),
                                rule.getDaysType(), rule.getStep()))
                        .collect(Collectors.toList()),
                periodicityDTO.getRangeTimestamp());
    }
}
