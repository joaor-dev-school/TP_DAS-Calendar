package tp.das.Model.Evento;

import tp.das.Model.Data.Data;
import tp.das.Model.Utilizador.Utilizador;

import java.util.List;

public interface IEstrategiaEventoColaborativo {
    Data selectData(List<Utilizador> convidados);
}
