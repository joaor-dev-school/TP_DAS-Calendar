package tp.das.Model.Data;

import java.util.Date;

public class Data {
    private long timestamp;
    private long duracao;

    public Data(Data data) {
        this.timestamp = data.timestamp;
        this.duracao = data.duracao;
    }

    public Data(long timestamp, long duracao) {
        this.timestamp = timestamp;
        this.duracao = duracao;
    }

    public Date getDate() {
        return new Date(timestamp);
    }

    public void setDate(Date date) {
        this.timestamp = date.getTime();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDuracao() {
        return duracao;
    }

    public void setDuracao(long duracao) {
        this.duracao = duracao;
    }

    public boolean matchData(Data otherData) {
        final long otherDataTimestamp = otherData.getTimestamp();
        return otherDataTimestamp >= timestamp && otherDataTimestamp < timestamp + duracao;
    }
}
