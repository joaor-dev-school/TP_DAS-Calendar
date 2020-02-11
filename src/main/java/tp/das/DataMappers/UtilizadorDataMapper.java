package tp.das.DataMappers;

import tp.das.Model.Database.DataMapper;
import tp.das.Model.Utilizador.Utilizador;

import java.util.HashMap;
import java.util.Map;

public class UtilizadorDataMapper implements DataMapper<Utilizador> {
    private static int identifiersCounter = 1;
    private static UtilizadorDataMapper dataMapperInstance;

    private final Map<Integer, Utilizador> utilizadores;

    private UtilizadorDataMapper() {
        utilizadores = new HashMap<>();
    }

    public static UtilizadorDataMapper getInstance() {
        if (dataMapperInstance == null) {
            dataMapperInstance = new UtilizadorDataMapper();
        }
        Utilizador u = new Utilizador();
        boolean ax = u.getClass() == Utilizador.class;
        DataMapper<Utilizador> a;
        return dataMapperInstance;
    }

    @Override
    public void create(Utilizador utilizador) throws Exception {
        utilizadores.put(identifiersCounter, utilizador);
        identifiersCounter++;

    }

    @Override
    public void update(Object id, Utilizador utilizador) throws Exception {
        Integer identifier = (Integer) id;
        Utilizador u = utilizadores.get(identifier);
        if (u == null) {
            throw new RuntimeException("Erro no update Utilizador: não existe utilizador com o id " + identifier);
        }
        utilizadores.put(identifier, utilizador);
    }

    @Override
    public void delete(Object id) throws Exception {
        Integer identifier = (Integer) id;
        Utilizador u = utilizadores.get(identifier);
        if (u == null) {
            throw new RuntimeException("Erro no delete Utilizador: não existe utilizador com o id " + identifier);
        }
        utilizadores.remove(identifier);
    }

    @Override
    public Utilizador find(Object id) {
        Integer identifier = (Integer) id;
        return utilizadores.get(identifier);
    }
}
