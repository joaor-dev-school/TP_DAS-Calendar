package tp.das.DTOs.User;

import org.hibernate.validator.constraints.Length;
import tp.das.Validators.Users.UserId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ChangeUserNameDTO {

    @NotNull
    @Min(0)
    @UserId
    private Long userId;

    @NotNull
    @Length(min = 5)
    private String name;

    public ChangeUserNameDTO(@NotNull @Min(0) Long userId, @NotNull @Min(5) String name) {
        this.userId = userId;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
