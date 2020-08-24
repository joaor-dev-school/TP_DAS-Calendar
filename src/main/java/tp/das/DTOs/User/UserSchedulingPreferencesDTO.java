package tp.das.DTOs.User;

import tp.das.Validators.Users.UserId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserSchedulingPreferencesDTO {
    @NotNull
    @UserId
    private Long userId;

    @NotNull
    @Size(max = UserDTOConstants.MAX_PREFERENCES)
    private List<UserSchedulingPreferenceDTO> preferred;

    @NotNull
    @Size(max = UserDTOConstants.MAX_PREFERENCES)
    private List<UserSchedulingPreferenceDTO> acceptable;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<UserSchedulingPreferenceDTO> getPreferred() {
        return preferred;
    }

    public void setPreferred(List<UserSchedulingPreferenceDTO> preferred) {
        this.preferred = preferred;
    }

    public List<UserSchedulingPreferenceDTO> getAcceptable() {
        return acceptable;
    }

    public void setAcceptable(List<UserSchedulingPreferenceDTO> acceptable) {
        this.acceptable = acceptable;
    }
}
