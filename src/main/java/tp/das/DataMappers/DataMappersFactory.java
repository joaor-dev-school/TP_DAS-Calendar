package tp.das.DataMappers;

import tp.das.Model.Database.DataMapper;
import tp.das.Model.Utilizador.Utilizador;

public class DataMappersFactory {
    public DataMapper getMapper(Object o) {
        if(o.getClass() == Utilizador.class) {
            return UtilizadorDataMapper.getInstance();
        }

        return EventoDataMapper.getInstance();
    }
}
