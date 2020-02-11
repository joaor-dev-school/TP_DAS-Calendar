package tp.das.Model.Command;

import tp.das.Model.Evento.Evento;
import tp.das.Model.Utilizador.Utilizador;

public class AddEventCommand implements IEventRecorderCommand{
    private Evento ev;
    public AddEventCommand(Evento ev) {
        this.ev = ev;
    }

    @Override
    public void execute(Utilizador client) {
        client.addEvent(ev);
    }

    @Override
    public void undo(Utilizador client) {
        client.removeEvent(ev);
    }
}
