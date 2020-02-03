package tp.das.Model.Data;

public class DataRecorrente extends Data {
    private Data data;
    private long periodicidade;

    public DataRecorrente(Data data, long periodicidade) {
        super(data);
        this.data = data;
        this.periodicidade = periodicidade;
    }

    @Override
    public boolean matchData(Data otherData) {
        final long modifiedTimestamp = data.getTimestamp() + Math.max(data.getTimestamp(), otherData.getTimestamp()) % periodicidade;
        final Data modifiedData = new Data(modifiedTimestamp, data.getDuracao());
        return data.matchData(modifiedData);
    }
}
