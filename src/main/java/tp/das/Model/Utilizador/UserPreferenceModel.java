package tp.das.Model.Utilizador;

public class UserPreferenceModel {
    private Long fromTimestamp;
    private Long toTimestamp;
    private UserSchedulingPreferenceTypeEnum type;

    public UserPreferenceModel(Long fromTimestamp, Long toTimestamp, UserSchedulingPreferenceTypeEnum type) {
        this.fromTimestamp = fromTimestamp;
        this.toTimestamp = toTimestamp;
        this.type = type;
    }

    public Long getFromTimestamp() {
        return fromTimestamp;
    }

    public void setFromTimestamp(Long fromTimestamp) {
        this.fromTimestamp = fromTimestamp;
    }

    public Long getToTimestamp() {
        return toTimestamp;
    }

    public void setToTimestamp(Long toTimestamp) {
        this.toTimestamp = toTimestamp;
    }

    public UserSchedulingPreferenceTypeEnum getType() {
        return type;
    }

    public void setType(UserSchedulingPreferenceTypeEnum type) {
        this.type = type;
    }
}
