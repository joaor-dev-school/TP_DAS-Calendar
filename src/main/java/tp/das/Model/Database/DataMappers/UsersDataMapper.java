package tp.das.Model.Database.DataMappers;

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
    }

    public static UsersDataMapper getInstance() {
        if (dataMapperInstance == null) {
            dataMapperInstance = new UsersDataMapper();
        }
        return dataMapperInstance;
    }

    @Override
    public void create(UserModel userModel) {
        Long identifier = userModel.getId() != null ? userModel.getId() : identifiersCounter;
        userModel.setId(identifier);
        users.put(identifier, userModel);
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
