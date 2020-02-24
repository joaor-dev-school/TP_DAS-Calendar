package tp.das.Model.Evento;
import java.util.List;
import tp.das.Model.Sistema.Utilizador;



public class EventoConvite  extends Evento {
        public EventoConvite(Utilizador organizador, List<Utilizador> convidados, IEstrategiaEventoColaborativo estrategiaData) {
            super(organizador, convidados, estrategiaData.selectData(convidados));
        }

}
