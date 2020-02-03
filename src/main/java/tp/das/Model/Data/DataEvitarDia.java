package tp.das.Model.Data;

import sun.util.calendar.ZoneInfo;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataEvitarDia extends Data {
    private Data data;

    public DataEvitarDia(Data data) {
        super(data);
        this.data = data;
    }

    @Override
    public boolean matchData(Data otherData) {
        Calendar c1 = new GregorianCalendar(ZoneInfo.getDefault());
        Calendar c2 = new GregorianCalendar(ZoneInfo.getDefault());

        c1.setTime(data.getDate());
        c2.setTime(otherData.getDate());

        return c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH);
    }
}
