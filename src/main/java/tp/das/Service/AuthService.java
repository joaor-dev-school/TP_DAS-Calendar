package tp.das.Service;

import tp.das.DTOs.Auth.LoginDTO;
import tp.das.DTOs.Auth.RegisterDTO;
import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Database.DataMappers.AuthDataMapper;
import tp.das.Model.Database.UnitOfWork;
import tp.das.Model.Utilizador.UserModel;

import java.util.List;

public class AuthService {
    public static AuthModel findById(Long id) throws Exception {
        return AuthDataMapper.getInstance().find(id);
    }

    public static List<AuthModel> findByAll() throws Exception {
        return AuthDataMapper.getInstance().findAll();
    }

    public static AuthModel findByUsername(String username) throws Exception {
        final List<AuthModel> authModels = AuthDataMapper.getInstance().findAll();
        for (AuthModel authModel : authModels) {
            if (authModel.getUsername().equals(username)) {
                return authModel;
            }
        }
        return null;
    }

    public static void createAccount(RegisterDTO registerDTO) {
        final AuthModel account = new AuthModel(registerDTO.getUsername(), registerDTO.getPassword());
        final UserModel user = new UserModel(account, registerDTO.getName());
        final UnitOfWork unitOfWork = SessionService.getUnitOfWork();
        unitOfWork.registerNew(account);
        unitOfWork.registerNew(user);
        unitOfWork.commit();
    }

    public static Long checkLogin(LoginDTO loginDTO) {
        try {
            final AuthModel authModel = findByUsername(loginDTO.getUsername());
            System.out.println(authModel);
            System.out.println(AuthDataMapper.getInstance().findAll());
            return authModel != null && authModel.getPassword().equals(loginDTO.getPassword())
                    ? authModel.getId() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
