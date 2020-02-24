package tp.das.Model.Decorador;

import tp.das.Model.Data.Data;

public class DatePreferencial {
    Data data;
    public DatePreferencial(Data d) {
        data = d;
    }


    @Override
    public Data cost(Data d) {
        return new DateAceitavel(new DateDisponivel(d)).cost(d);
    }
}
