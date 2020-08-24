package tp.das.Model.Utilizador;

import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Database.EntityModel;

public class UserModel implements EntityModel {
    private Long id;
    private String name;
    private UserPreferencesModel preferences;
    private AuthModel account;

    public UserModel(String name) {
        this(null, null, name, new UserPreferencesModel());
    }

    public UserModel(AuthModel account, String name) {
        this(null, account, name, new UserPreferencesModel());
    }

    public UserModel(AuthModel account, String name, UserPreferencesModel preferences) {
        this(null, account, name, preferences);
    }

    public UserModel(Long id, AuthModel account, String name, UserPreferencesModel preferences) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.preferences = preferences;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthModel getAccount() {
        return account;
    }

    public void setAccount(AuthModel account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserPreferencesModel getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferencesModel preferences) {
        this.preferences = preferences;
    }
}
