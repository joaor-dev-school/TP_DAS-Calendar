package tp.das.Model.Command;

import tp.das.Model.Event.EventModel;
import tp.das.Model.Utilizador.UserModel;

public class AddEventCommand implements IEventRecorderCommand{
    private EventModel ev;
    public AddEventCommand(EventModel ev) {
        this.ev = ev;
    }

    @Override
    public void execute(UserModel client) {
//        client.addEvent(ev);
    }

    @Override
    public void undo(UserModel client) {
//        client.removeEvent(ev);
    }
}
