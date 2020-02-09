package tp.das.Model.Command;
import tp.das.Model.Evento.Evento;
import tp.das.Model.Evento.IEstrategiaEventoColaborativo;
import tp.das.Model.Utilizador;

public class ApplyComand implements IEventRecorderCommand {

    private IEstrategiaEventoColaborativo evento;
    @Override
    public void execute(Utilizador client) {

        Evento ev = client.getEvent();

        if (ev != null) {
            evento.selectData(ev.getConvidados());
            ev.setOrganizador(client);
        }

    }

    @Override
    public void undo(Utilizador client) {

        Evento ev = client.getEvent();

        if (ev != null && evento != null) {
            evento.selectData(ev.getConvidados());
        }

    }
}
