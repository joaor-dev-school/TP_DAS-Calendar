package tp.das.DTOs.Auth;

import tp.das.Validators.Auth.UniqueUsername;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {
    @NotNull
    @Size(min = AuthDTOConstants.MIN_LENGTH_USERNAME, max = AuthDTOConstants.MAX_LENGTH_USERNAME)
    private String username;

    @NotNull
    @Size(min = AuthDTOConstants.MIN_LENGTH_PASSWORD, max = AuthDTOConstants.MAX_LENGTH_PASSWORD)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
