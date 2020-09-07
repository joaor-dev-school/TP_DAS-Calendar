package tp.das.Model.Event.Command;

import tp.das.Model.Event.DateModel;
import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Event.EventParticipantModel;
import tp.das.Model.Event.EventParticipantStatesEnum;
import tp.das.Model.Event.EventSchedulingModel;
import tp.das.Service.EventsService;
import tp.das.Service.SessionService;

import java.util.ArrayList;
import java.util.List;

public class UpdateEventCollaborativeStatusCommand implements IEventCommand {

    private boolean isFirstTime;

    private Long eventId;
    private Long participantId;

    private List<Long> newPreferredTimestamps;
    private List<Long> oldPreferredTimestamps;

    private List<Long> newAcceptableTimestamps;
    private List<Long> oldAcceptableTimestamps;


    private EventParticipantStatesEnum oldStatus;
    private EventParticipantStatesEnum newStatus;
    private DateModel oldDate;
    private DateModel newDate;

    public UpdateEventCollaborativeStatusCommand(Long eventId, Long eventParticipantId, EventParticipantStatesEnum status) {
        this(eventId, eventParticipantId, new ArrayList<>(), new ArrayList<>(), status);
    }

    public UpdateEventCollaborativeStatusCommand(Long eventId, Long eventParticipantId,
                                                 List<Long> newPreferredTimestamps, List<Long> newAcceptableTimestamps) {
        this(eventId, eventParticipantId, newPreferredTimestamps, newAcceptableTimestamps,
                EventParticipantStatesEnum.ACCEPTED);
    }

    private UpdateEventCollaborativeStatusCommand(Long eventId, Long participantId,
                                                  List<Long> newPreferredTimestamps, List<Long> newAcceptableTimestamps,
                                                  EventParticipantStatesEnum newStatus) {
        final EventsService eventsService = EventsService.getInstance();
        final EventSchedulingModel eventModel = eventsService.findSchedulingBydId(eventId);
        final EventParticipantModel participantModel = eventsService.findParticipantById(eventModel, participantId);
        this.eventId = eventId;
        this.participantId = participantId;
        this.newPreferredTimestamps = newPreferredTimestamps;
        this.newAcceptableTimestamps = newAcceptableTimestamps;
        this.oldAcceptableTimestamps = new ArrayList<>(participantModel.getAcceptableTimestamps());
        this.oldPreferredTimestamps = new ArrayList<>(participantModel.getPreferredTimestamps());
        this.oldStatus = participantModel.getState();
        this.newStatus = newStatus;
        this.oldDate = eventModel.getSelectedDate();
        this.isFirstTime = true;
    }

    @Override
    public void doCommand() {
        final EventsService eventsService = EventsService.getInstance();
        final EventSchedulingModel eventModel = eventsService.findSchedulingBydId(this.eventId);
        final EventParticipantModel participantModel = eventsService.findParticipantById(eventModel, participantId);
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        participantModel.setAcceptableTimestamps(this.newAcceptableTimestamps);
        participantModel.setPreferredTimestamps(this.newPreferredTimestamps);
        participantModel.setState(this.newStatus);
        this.newDate = eventModel.updateSelectedDate(this.newDate);
        unitOfWork.registerDirty(eventModel);
        unitOfWork.commit();
    }

    @Override
    public void undoCommand() {
        final EventsService eventsService = EventsService.getInstance();
        final EventSchedulingModel eventModel = eventsService.findSchedulingBydId(this.eventId);
        final EventParticipantModel participantModel = eventsService.findParticipantById(eventModel, participantId);
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        participantModel.setAcceptableTimestamps(this.oldAcceptableTimestamps);
        participantModel.setPreferredTimestamps(this.oldPreferredTimestamps);
        participantModel.setState(this.oldStatus);
        this.newDate = eventModel.getSelectedDate(); // Can be updated by another participant.
        eventModel.setSelectedDate(this.oldDate);
        unitOfWork.registerDirty(eventModel);
        unitOfWork.commit();
    }
}
