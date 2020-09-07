package tp.das.Model.Event.Command;

import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Event.EventModel;
import tp.das.Service.NotificationsService;
import tp.das.Service.SessionService;

public class CreateEventCommand implements IEventCommand {

    private EventModel eventModel;

    public CreateEventCommand(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public void doCommand() {
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        final Long previousId = this.eventModel.getId();
        unitOfWork.registerNew(this.eventModel);
        this.eventModel.setId(previousId);
        unitOfWork.commit();
        if (previousId != null) {
            unitOfWork.registerDirty(this.eventModel);
            unitOfWork.commit();
        }
        NotificationsService.getInstance().createNotification(this.eventModel);
    }

    public void undoCommand() {
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerDeleted(this.eventModel);
        unitOfWork.commit();
        NotificationsService.getInstance().removeNotification(this.eventModel);
    }
}
