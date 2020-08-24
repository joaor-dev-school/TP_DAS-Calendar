package tp.das.Model.Event.Command;

import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Event.EventModel;
import tp.das.Service.NotificationsService;
import tp.das.Service.SessionService;

public class DeleteEventCommand implements IEventCommand {
    private EventModel eventModel;

    public DeleteEventCommand(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public void doCommand() {
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerDeleted(this.eventModel);
        unitOfWork.commit();
        NotificationsService.getInstance().removeNotification(this.eventModel);
    }

    public void undoCommand() {
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerNew(this.eventModel);
        unitOfWork.commit();
        NotificationsService.getInstance().createNotification(this.eventModel);
    }
}
