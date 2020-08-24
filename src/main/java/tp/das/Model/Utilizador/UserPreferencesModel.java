package tp.das.Model.Utilizador;

import java.util.ArrayList;
import java.util.List;

public class UserPreferencesModel {
    private List<UserPreferenceModel> preferred;
    private List<UserPreferenceModel> acceptable;

    public UserPreferencesModel() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public UserPreferencesModel(List<UserPreferenceModel> preferred, List<UserPreferenceModel> acceptable) {
        this.preferred = preferred;
        this.acceptable = acceptable;
    }

    public List<UserPreferenceModel> getPreferred() {
        return preferred;
    }

    public void setPreferred(List<UserPreferenceModel> preferred) {
        this.preferred = preferred;
    }

    public List<UserPreferenceModel> getAcceptable() {
        return acceptable;
    }

    public void setAcceptable(List<UserPreferenceModel> acceptable) {
        this.acceptable = acceptable;
    }
}
