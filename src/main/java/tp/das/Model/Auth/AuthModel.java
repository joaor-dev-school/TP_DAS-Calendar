package tp.das.Model.Auth;

import tp.das.Model.Database.EntityModel;

public class AuthModel implements EntityModel {
    private Long id;
    private String username;
    private String password;
    private Boolean deleted;

    public AuthModel(String username, String password) {
        this(null, username, password);
    }

    public AuthModel(Long id, String username, String password) {
        this(id, username, password, false);
    }

    public AuthModel(Long id, String username, String password, Boolean deleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deleted = deleted;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
