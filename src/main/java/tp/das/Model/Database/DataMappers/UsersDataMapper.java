package tp.das.Model.Database.DataMappers;

import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Database.DataMapper;
import tp.das.Model.Utilizador.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersDataMapper implements DataMapper<UserModel> {
    private static Long identifiersCounter = 1L;
    private static UsersDataMapper dataMapperInstance;

    private final Map<Long, UserModel> users;

    private UsersDataMapper() {
        users = new HashMap<>();
        try {
            // TODO: remove mock users
            this.create(new UserModel(new AuthModel("joao", "pass"), "João Paulo"));
            this.create(new UserModel(new AuthModel("joana", "pass"), "Joana Lopes"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UsersDataMapper getInstance() {
        if (dataMapperInstance == null) {
            dataMapperInstance = new UsersDataMapper();
        }
        return dataMapperInstance;
    }

    @Override
    public void create(UserModel userModel) {
        userModel.setId(identifiersCounter);
        users.put(identifiersCounter, userModel);
        identifiersCounter++;
    }

    @Override
    public void update(Object id, UserModel userModel) {
        Long identifier = (Long) id;
        UserModel u = users.get(identifier);
        if (u == null) {
            throw new RuntimeException("Erro no update Utilizador: não existe utilizador com o id " + identifier);
        }
        users.put(identifier, userModel);
    }

    @Override
    public void delete(Object id) {
        Long identifier = (Long) id;
        UserModel u = users.get(identifier);
        if (u == null) {
            throw new RuntimeException("Erro no delete Utilizador: não existe utilizador com o id " + identifier);
        }
        users.remove(identifier);
    }

    @Override
    public List<UserModel> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public UserModel find(Object id) {
        Long identifier = (Long) id;
        return users.get(identifier);
    }
}
