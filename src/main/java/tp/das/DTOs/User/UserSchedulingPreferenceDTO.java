package tp.das.DTOs.User;

import tp.das.Model.Utilizador.UserSchedulingPreferenceStateEnum;
import tp.das.Model.Utilizador.UserSchedulingPreferenceTypeEnum;
import tp.das.Validators.Users.UserSchedulingPreference;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@UserSchedulingPreference
public class UserSchedulingPreferenceDTO {

    @Min(0)
    private Long fromTimestamp;

    @Min(0)
    private Long toTimestamp;

    @NotNull
    private UserSchedulingPreferenceTypeEnum type;

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
