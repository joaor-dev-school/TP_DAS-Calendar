package tp.das.Service;


import tp.das.DataMappers.UtilizadorDataMapper;
import tp.das.Model.Utilizador.Utilizador;

public class UtilizadoresService {
    public static Utilizador findById(int id) {
        return UtilizadorDataMapper.getInstance().find(id);
    }
}
