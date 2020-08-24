package tp.das.Model.Event.Command;

import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Event.EventModel;
import tp.das.Service.NotificationsService;
import tp.das.Service.SessionService;

public class UpdateEventCommand implements IEventCommand {

    private EventModel newEventModel;

    private EventModel oldEventModel;

    public UpdateEventCommand(EventModel newEventModel, EventModel oldEventModel) {
        this.newEventModel = newEventModel;
        this.oldEventModel = oldEventModel;
    }

    public void doCommand() {
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerDirty(this.newEventModel);
        unitOfWork.commit();
        NotificationsService.getInstance().removeNotification(this.oldEventModel);
        NotificationsService.getInstance().createNotification(this.newEventModel);
    }

    public void undoCommand() {
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerDirty(this.oldEventModel);
        unitOfWork.commit();
        NotificationsService.getInstance().removeNotification(this.newEventModel);
        NotificationsService.getInstance().createNotification(this.oldEventModel);
    }
}
