package tp.das.DTOs.Event;

import tp.das.Validators.Users.UserId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserOperationDTO {
    @NotNull
    @Min(0)
    @UserId
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}