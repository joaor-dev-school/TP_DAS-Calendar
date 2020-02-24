package tp.das.Model.Decorador;

import tp.das.Model.Data.Data;
import tp.das.Model.Sistema.Utilizador;

public class DateDisponivel {

    Data data;
    Utilizador u

    public DateDisponivel(Data d) {
        data = d;
    }

    @Override
    public Data cost(Data d, Utilizador u) {
    // vericar utilizador e data
        return new DateAceitavel(new DatePreferencial(d)).cost(d);
    }
}
