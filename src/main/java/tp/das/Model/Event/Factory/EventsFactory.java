package tp.das.Model.Event.Factory;

import tp.das.DTOs.Event.CreateEventDTO;
import tp.das.DTOs.Event.CreateEventDateDTO;
import tp.das.Model.Event.DateModel;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Event.EventParticipantModel;
import tp.das.Model.Event.EventParticipantStatesEnum;
import tp.das.Model.Event.EventTypesEnum;
import tp.das.Model.Utilizador.UserModel;
import tp.das.Service.UsersService;

import java.util.ArrayList;
import java.util.List;

public abstract class EventsFactory {
    public static EventsFactory getFactory(EventTypesEnum eventType) {
        switch (eventType) {
            case SCHEDULING_AUTOMATIC:
                return new SchedulingAutomaticEventsFactory();
            case SCHEDULING_COLLABORATIVE:
                return new SchedulingCollaborativeEventsFactory();
            default:
                return new InviteEventsFactory();
        }
    }

    public abstract EventModel getEvent(CreateEventDTO createEventDTO);

    public abstract EventModel getEvent(CreateEventDTO createEventDTO, Long id);

    UserModel resolveCreator(Long creatorId) {
        final UserModel creator = UsersService.findById(creatorId);

        if (creator == null) {
            // TODO: Create an exception for bad request (400).
            throw new RuntimeException("Creator do not exist");
        }

        return creator;
    }

    List<EventParticipantModel> resolveParticipants(CreateEventDTO createEventDTO, UserModel creator) {
        final List<EventParticipantModel> participants = new ArrayList<>();
        participants.add(new EventParticipantModel(creator, EventParticipantStatesEnum.NEW));

        for (Long participantId : createEventDTO.getParticipantsIds()) {
            if (participantId.equals(creator.getId())) {
                continue;
            }

            final UserModel participantUser = UsersService.findById(participantId);
            if (participantUser != null) {
                participants.add(new EventParticipantModel(participantUser, EventParticipantStatesEnum.NEW));
            }
        }
        return participants;
    }

    List<DateModel> resolveDates(List<CreateEventDateDTO> datesDTOS) {
        final List<DateModel> dates = new ArrayList<>();
        for (CreateEventDateDTO dateDTO : datesDTOS) {
            dates.add(this.resolveDate(dateDTO));
        }
        return dates;
    }

    DateModel resolveDate(CreateEventDateDTO dateDTO) {
        return new DateModel(dateDTO.getTimestamp(), dateDTO.getDuration());
    }
}
