package tp.das.Model.Data;

public class DataEvitarIntervalo extends Data {
    private Data data;

    public DataEvitarIntervalo(Data data) {
        super(data);
        this.data = data;
    }

    @Override
    public boolean matchData(Data otherData) {
        return !data.matchData(otherData);
    }
}
