package tp.das.Model.Auth;

import tp.das.Model.Database.EntityModel;

public class AuthModel implements EntityModel {
    private Long id;
    private String username;
    private String password;

    public AuthModel(String username, String password) {
        this(null, username, password);
    }

    public AuthModel(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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
