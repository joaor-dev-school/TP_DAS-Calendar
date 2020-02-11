package tp.das.Model.Evento;

import tp.das.Model.Utilizador.Utilizador;

import java.util.List;

public class EventoColaborativo extends Evento {

    public EventoColaborativo(Utilizador organizador, List<Utilizador> convidados, IEstrategiaEventoColaborativo estrategiaData) {
        super(organizador, convidados, estrategiaData.selectData(convidados));
    }
}
