package tp.das.Service;

import tp.das.DTOs.Event.*;
import tp.das.Model.Database.DataMappers.EventsDataMapper;
import tp.das.Model.Event.*;
import tp.das.Model.Event.Command.*;
import tp.das.Model.Event.Decorators.PeriodicEventDecorator;
import tp.das.Model.Event.Factory.EventsFactory;
import tp.das.Model.Utilizador.UserModel;

import java.util.*;
import java.util.stream.Collectors;

public class EventsService {

    private static EventsService eventsService;
    private EventCommandHandler eventCommandHandler;

    private EventsService() {
        this.eventCommandHandler = new EventCommandHandler();
    }

    public static EventsService getInstance() {
        return eventsService == null ? (eventsService = new EventsService()) : eventsService;
    }

    public EventListResponseDTO findAll() {
        final List<EventModel> events = EventsDataMapper.getInstance().findAll();
        return new EventListResponseDTO(events.stream().map((EventModel event) -> new EventListItemResponseDTO(event.getId(),
                event.getCreator().getId(), event.getName(), event.getType(),
                this.datesModelToResponse(event.getDates()))).collect(Collectors.toList()));
    }

    public EventDetailsResponseDTO findById(Long id) {
        final EventModel eventModel = EventsDataMapper.getInstance().find(id);
        if (eventModel == null) {
            return null;
        }
        return this.eventModelToDetails(eventModel);
    }

    public EventSchedulingModel findSchedulingBydId(Long id) {
        final EventModel event = EventsDataMapper.getInstance().find(id);
        if (event == null) {
            throw new RuntimeException("Couldn't find any event with the given event id");
        }
        if (!(event instanceof EventSchedulingModel)) {
            throw new RuntimeException("The given event is not a scheduling type event");
        }
        return (EventSchedulingModel) event;
    }

    public EventModel findEventById(Long id) {
        final EventModel event = EventsDataMapper.getInstance().find(id);
        if (event == null) {
            throw new RuntimeException("Couldn't find any event with the given event id");
        }
        return event;
    }

    public List<EventDetailsResponseDTO> findEventsByParticipantId(Long participantId) {
        final List<EventDetailsResponseDTO> res = new ArrayList<>();
        final List<EventModel> events = EventsDataMapper.getInstance().findAll();
        for (EventModel event : events) {
            if (!event.getParticipants().stream().anyMatch((EventParticipantModel participant) ->
                    participant.getUserModel().getId().equals(participantId))) {
                continue;
            }
            res.add(this.eventModelToDetails(event));
        }
        return res;
    }

    public EventParticipantModel findParticipantById(EventModel event, Long participantId) {
        for (EventParticipantModel participant : event.getParticipants()) {
            if (participant.getUserModel().getId().equals(participantId)) {
                return participant;
            }
        }
        throw new RuntimeException("No participant found on event with the given id");
    }

    public void createEvent(CreateEventDTO createEventDTO, EventTypesEnum eventType) {
        final EventModel event = EventsFactory.getFactory(eventType).getEvent(createEventDTO);
        this.eventCommandHandler
                .apply(createEventDTO.getCreatorId(), new CreateEventCommand(event));
    }

    public void editEvent(Long eventId, CreateEventDTO createEventDTO, EventTypesEnum eventType) {
        final EventModel oldEventModel = EventsDataMapper.getInstance().find(eventId);

        if (oldEventModel == null) {
            throw new RuntimeException("Couldn't find any event with the given event id");
        }

        final EventModel newEvent = EventsFactory.getFactory(eventType).getEvent(createEventDTO, eventId);
        this.eventCommandHandler
                .apply(createEventDTO.getCreatorId(), new UpdateEventCommand(newEvent, oldEventModel));
    }

    public void deleteEvent(Long eventId) {
        final EventModel eventModel = EventsDataMapper.getInstance().find(eventId);
        if (eventModel == null) {
            throw new RuntimeException("Couldn't find any event with the given event id");
        }

        this.eventCommandHandler.apply(eventModel.getCreator().getId(), new DeleteEventCommand(eventModel));
    }

    public void redoEventOperation(Long userId) {
        this.eventCommandHandler.redo(userId);
    }

    public void undoEventOperation(Long userId) {
        this.eventCommandHandler.undo(userId);
    }

    public void cleanUserRecord(Long userId) {
        this.eventCommandHandler.cleanUserRecord(userId);
    }

    public void updateEventSchedulingStatus(Long eventId, EventSchedulingStatusDTO preferencesDTO) {
        final Long userId = preferencesDTO.getUserId();
        this.eventCommandHandler.apply(userId, preferencesDTO.getAccept()
                ? new UpdateEventSchedulingStatusCommand(eventId, userId,
                preferencesDTO.getPreferredTimestamps(), preferencesDTO.getAcceptableTimestamps())
                : new UpdateEventSchedulingStatusCommand(eventId, userId));
    }

    public void updateEventInviteStatus(Long eventId, ChangeEventStatusDTO statusDTO) {
        final Long userId = statusDTO.getUserId();
        this.eventCommandHandler.apply(userId, new UpdateEventInviteStatusCommand(eventId, userId,
                statusDTO.getAccept() ? EventParticipantStatesEnum.ACCEPTED : EventParticipantStatesEnum.DECLINED));
    }

    private EventDetailsResponseDTO eventModelToDetails(EventModel event) {
        final Map<Long, EventParticipantResponseDTO> participantsMap = new HashMap<>();
        final List<EventParticipantResponseDTO> participantsRes = new ArrayList<>();

        for (EventParticipantModel participant : event.getParticipants()) {
            final UserModel userModel = participant.getUserModel();
            EventParticipantResponseDTO participantRes = participantsMap.get(userModel.getId());
            if (participantRes == null) {
                participantRes = new EventParticipantResponseDTO(userModel.getId(), userModel.getName(), participant.getState());
                participantsMap.put(userModel.getId(), participantRes);
            }
            participantsRes.add(participantRes);
        }

        final UserModel creator = event.getCreator();

        final EventDetailsResponseDTO eventRes = new EventDetailsResponseDTO()
                .setEventId(event.getId())
                .setName(event.getName())
                .setType(event.getType())
                .setCreator(new EventParticipantResponseDTO(creator.getId(), creator.getName(), null))
                .setParticipants(participantsRes)
                .setDates(event.getDates().stream().map((DateModel dateModel) ->
                        new EventDateResponseDTO(dateModel.getTimestamp(), dateModel.getDuration()))
                        .collect(Collectors.toList()));

        if (event instanceof PeriodicEventDecorator) {
            final PeriodicEventDecorator periodicEvent = (PeriodicEventDecorator) event;
            return new EventInviteDetailsResponseDTO(eventRes,
                    new PeriodicityResponseDTO(periodicEvent.getRules().stream()
                            .map((EventPeriodicityRule rule) ->
                                    new PeriodicityRuleResponseDTO(rule.getDays(), rule.getDaysNot(), rule.getDaysType(), rule.getStep()))
                            .collect(Collectors.toList()),
                            periodicEvent.getRangeTimestamp()
                    ));
        }
        return eventRes;
    }

    private List<EventDateResponseDTO> datesModelToResponse(List<DateModel> dates) {
        return dates.stream().map((DateModel date) -> new EventDateResponseDTO(date.getTimestamp(), date.getDuration()))
                .collect(Collectors.toList());
    }
}
