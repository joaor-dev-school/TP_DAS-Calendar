package tp.das.DTOs.Auth;

import org.hibernate.validator.constraints.Length;
import tp.das.Validators.Users.UserId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ChangePasswordDTO {
    @NotNull
    @Min(0)
    @UserId
    private Long userId;

    @NotNull
    @Length(min = 5)
    private String password;

    public ChangePasswordDTO(@NotNull @Min(0) Long userId, @NotNull @Min(5) String password) {
        this.userId = userId;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
