package tp.das.Service;

import tp.das.DTOs.User.UserListResponseDTO;
import tp.das.DTOs.User.UserResponseDTO;
import tp.das.DTOs.User.UserSchedulingPreferenceDTO;
import tp.das.DTOs.User.UserSchedulingPreferencesDTO;
import tp.das.Model.Database.DataMappers.UsersDataMapper;
import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Utilizador.UserModel;
import tp.das.Model.Utilizador.UserPreferenceModel;
import tp.das.Model.Utilizador.UserPreferencesModel;

import java.util.List;
import java.util.stream.Collectors;

public class UsersService {
    public static UserModel findById(Long id) {
        return UsersDataMapper.getInstance().find(id);
    }
    public static UserResponseDTO findByAccountId(Long id) {
        final List<UserModel> userModels = UsersDataMapper.getInstance().findAll();
        for (UserModel userModel : userModels) {
            final Long userId = userModel.getId();
            if (userId.equals(id)) {
                return new UserResponseDTO(userId, userModel.getName(), userModel.getAccount().getUsername());
            }
        }
        return null;
    }

    public static UserListResponseDTO findByAll() {
        return new UserListResponseDTO(UsersDataMapper.getInstance().findAll().stream()
                .map((UserModel userModel) -> new UserResponseDTO(userModel.getId(), userModel.getName(),
                        userModel.getAccount().getUsername())).collect(Collectors.toList()));
    }

    public static void changeUserPreferences(UserSchedulingPreferencesDTO preferences) {
        final UserModel userModel = findById(preferences.getUserId());
        if (userModel == null) {
            throw new RuntimeException("Couldn't find any user with the given event id");
        }
        final List<UserPreferenceModel> preferredList = preferences.getPreferred().stream()
                .map((UserSchedulingPreferenceDTO pref) ->
                        new UserPreferenceModel(pref.getFromTimestamp(), pref.getToTimestamp(), pref.getType()))
                .collect(Collectors.toList());
        final List<UserPreferenceModel> acceptableList = preferences.getAcceptable().stream()
                .map((UserSchedulingPreferenceDTO pref) ->
                        new UserPreferenceModel(pref.getFromTimestamp(), pref.getToTimestamp(), pref.getType()))
                .collect(Collectors.toList());
        final UserPreferencesModel userPreferences = new UserPreferencesModel(preferredList, acceptableList);
        userModel.setPreferences(userPreferences);
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerDirty(userModel);
        unitOfWork.commit();
    }
}
