package tp.das.Model.Command;
import tp.das.Model.Event.Strategy.ISchedulingStrategy;
import tp.das.Model.Utilizador.UserModel;

public class ApplyComand implements IEventRecorderCommand {

    private ISchedulingStrategy evento;
    @Override
    public void execute(UserModel client) {

        /*EventModel ev = client.getEventModel();

        if (ev != null) {
            evento.selectDate(ev.getParticipants());
            ev.setCreator(client);
        }*/

    }

    @Override
    public void undo(UserModel client) {

        /*EventModel ev = client.getEventModel();

        if (ev != null && evento != null) {
            evento.selectDate(ev.getParticipants());
        }*/

    }
}
