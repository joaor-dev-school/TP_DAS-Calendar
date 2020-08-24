package tp.das.Model.Event;

import java.util.Date;

public class DateModel {
    private long timestamp;
    private long duration;

    public DateModel(DateModel date) {
        this.timestamp = date.timestamp;
        this.duration = date.duration;
    }

    public DateModel(long timestamp, long duration) {
        this.timestamp = timestamp;
        this.duration = duration;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
