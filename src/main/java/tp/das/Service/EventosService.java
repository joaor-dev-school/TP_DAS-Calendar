package tp.das.Service;

import tp.das.DataMappers.EventoDataMapper;
import tp.das.Model.Evento.Evento;

public class EventosService {
    public static Evento findById(int id) {
        return EventoDataMapper.getInstance().find(id);
    }
}
