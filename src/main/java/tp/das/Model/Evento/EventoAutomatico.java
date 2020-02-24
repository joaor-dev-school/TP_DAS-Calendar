package tp.das.Model.Evento;

import tp.das.Model.Sistema.Utilizador;

import java.util.List;

public class EventoAutomatico  extends Evento {
        public EventoAutomatico(Utilizador organizador, List<Utilizador> convidados, IEstrategiaEventoColaborativo estrategiaData) {
            super(organizador, convidados, estrategiaData.selectData(convidados));
        }

    }




