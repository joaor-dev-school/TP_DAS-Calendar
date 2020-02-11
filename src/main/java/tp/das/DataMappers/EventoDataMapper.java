package tp.das.DataMappers;

import tp.das.Model.Database.DataMapper;
import tp.das.Model.Evento.Evento;

import java.util.HashMap;
import java.util.Map;

public class EventoDataMapper implements DataMapper<Evento> {
    private static int identifiersCounter = 1;
    private static EventoDataMapper dataMapperInstance;

    private final Map<Integer, Evento> eventos;

    private EventoDataMapper() {
        eventos = new HashMap<>();
    }

    public static EventoDataMapper getInstance() {
        if (dataMapperInstance == null) {
            dataMapperInstance = new EventoDataMapper();
        }
        return dataMapperInstance;
    }

    @Override
    public void create(Evento evento) throws Exception {
        eventos.put(identifiersCounter, evento);
        identifiersCounter++;
    }

    @Override
    public void update(Object id, Evento evento) throws Exception {
        Integer identifier = (Integer) id;
        Evento e = eventos.get(identifier);
        if (e == null) {
            throw new RuntimeException("Erro no update Evento: não existe evento com o id " + identifier);
        }
        eventos.put(identifier, evento);
    }

    @Override
    public void delete(Object id) throws Exception {
        Integer identifier = (Integer) id;
        Evento e = eventos.get(identifier);
        if (e == null) {
            throw new RuntimeException("Erro no delete Evento: não existe evento com o id " + identifier);
        }
        eventos.remove(identifier);
    }

    @Override
    public Evento find(Object id) {
        Integer identifier = (Integer) id;
        return eventos.get(identifier);
    }
}
