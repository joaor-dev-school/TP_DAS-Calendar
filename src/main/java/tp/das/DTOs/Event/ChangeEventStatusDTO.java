package tp.das.DTOs.Event;

import tp.das.Model.Event.EventParticipantStatesEnum;
import tp.das.Model.Event.EventTypesEnum;
import tp.das.Validators.Users.UserId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ChangeEventStatusDTO {

    @NotNull
    @Min(0)
    @UserId
    private Long userId;

    @NotNull
    private EventParticipantStatesEnum status;

    public EventParticipantStatesEnum getStatus() {
        return status;
    }

    public void setStatus(EventParticipantStatesEnum status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
