package tp.das.Model.Event.Command;

import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Event.EventModel;
import tp.das.Model.Event.EventParticipantModel;
import tp.das.Model.Event.EventParticipantStatesEnum;
import tp.das.Service.EventsService;
import tp.das.Service.SessionService;

public class UpdateEventInviteStatusCommand implements IEventCommand {

    private Long eventId;
    private Long participantId;

    private EventParticipantStatesEnum newStatus;
    private EventParticipantStatesEnum oldStatus;

    public UpdateEventInviteStatusCommand(Long eventId, Long participantId, EventParticipantStatesEnum newStatus) {
        final EventsService eventsService = EventsService.getInstance();
        final EventModel eventModel = eventsService.findEventById(eventId);
        final EventParticipantModel participantModel = eventsService.findParticipantById(eventModel, participantId);
        this.eventId = eventId;
        this.participantId = participantId;
        this.newStatus = newStatus;
        this.oldStatus = participantModel.getState();
    }

    @Override
    public void doCommand() {
        final EventsService eventsService = EventsService.getInstance();
        final EventModel eventModel = eventsService.findEventById(eventId);
        final EventParticipantModel participantModel = eventsService.findParticipantById(eventModel, participantId);
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        participantModel.setState(newStatus);
        unitOfWork.registerDirty(eventModel);
        unitOfWork.commit();
    }

    @Override
    public void undoCommand() {
        final EventsService eventsService = EventsService.getInstance();
        final EventModel eventModel = eventsService.findEventById(eventId);
        final EventParticipantModel participantModel = eventsService.findParticipantById(eventModel, participantId);
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        participantModel.setState(oldStatus);
        unitOfWork.registerDirty(eventModel);
        unitOfWork.commit();
    }
}
