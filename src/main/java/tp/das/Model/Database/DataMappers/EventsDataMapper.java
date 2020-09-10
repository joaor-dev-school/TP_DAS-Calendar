package tp.das.Model.Database.DataMappers;

import tp.das.Model.Database.DataMapper;
import tp.das.Model.Event.EventModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsDataMapper implements DataMapper<EventModel> {
    private static Long identifiersCounter = 1L;
    private static EventsDataMapper dataMapperInstance;

    private final Map<Long, EventModel> events;

    private EventsDataMapper() {
        events = new HashMap<>();
    }

    public static EventsDataMapper getInstance() {
        if (dataMapperInstance == null) {
            dataMapperInstance = new EventsDataMapper();
        }
        return dataMapperInstance;
    }

    @Override
    public void create(EventModel eventModel) {
        Long identifier = eventModel.getId() != null ? eventModel.getId() : identifiersCounter;
        eventModel.setId(identifier);
        events.put(identifier, eventModel);
        identifiersCounter++;
    }

    @Override
    public void update(Object id, EventModel eventModel) {
        Long identifier = (Long) id;
        EventModel e = events.get(identifier);
        if (e == null) {
            throw new RuntimeException("Erro no update Evento: não existe evento com o id " + identifier);
        }
        events.put(identifier, eventModel);
    }

    @Override
    public void delete(Object id) {
        Long identifier = (Long) id;
        EventModel e = events.get(identifier);
        if (e == null) {
            throw new RuntimeException("Erro no delete Evento: não existe evento com o id " + identifier);
        }
        events.remove(identifier);
    }

    @Override
    public List<EventModel> findAll() {
        return new ArrayList<>(events.values());
    }

    @Override
    public EventModel find(Object id) {
        Long identifier = (Long) id;
        return events.get(identifier);
    }
}
