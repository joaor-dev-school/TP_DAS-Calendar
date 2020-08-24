package tp.das.Model.Database.DataMappers;

import tp.das.Model.Auth.AuthModel;
import tp.das.Model.Database.DataMapper;
import tp.das.Service.SessionService;
import tp.das.Service.UsersService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthDataMapper implements DataMapper<AuthModel> {
    private static AuthDataMapper dataMapperInstance;

    private AuthDataMapper() {

    }

    public static AuthDataMapper getInstance() {
        if (dataMapperInstance == null) {
            dataMapperInstance = new AuthDataMapper();
        }
        return dataMapperInstance;
    }

    @Override
    public void create(AuthModel e) throws Exception {
        final Connection c = SessionService.getConnection();
        final PreparedStatement ps = c.prepareStatement("INSERT INTO accounts VALUES(DEFAULT, '" + e.getUsername() + "', '" + e.getPassword() + "', 0)",
                Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        final ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        e.setId(rs.getLong(1));
    }

    @Override
    public void update(Object id, AuthModel e) throws Exception {
        Connection c = SessionService.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE accounts SET username='" + e.getUsername() + "', password='" + e.getPassword() + "' WHERE id=" + id + ")");
        ps.executeUpdate();
    }

    @Override
    public void delete(Object id) throws Exception {
        final Connection c = SessionService.getConnection();
        final PreparedStatement ps = c.prepareStatement("UPDATE accounts SET deleted=1 WHERE id=" + id + ")");
        ps.executeUpdate();
    }

    @Override
    public List<AuthModel> findAll() throws Exception {
        final Connection c = SessionService.getConnection();
        final PreparedStatement ps = c.prepareStatement("SELECT * FROM accounts WHERE deleted=0");
        final ResultSet rs = ps.executeQuery();
        final List<AuthModel> authModels = new ArrayList<>();
        while (rs.next()) {
            authModels.add(new AuthModel(rs.getLong(1), rs.getString(2), rs.getString(3)));
        }
        return authModels;
    }

    @Override
    public AuthModel find(Object id) throws Exception {
        final Connection c = SessionService.getConnection();
        final PreparedStatement ps = c.prepareStatement("SELECT * FROM accounts WHERE id=" + id);
        final ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new AuthModel(rs.getLong(1), rs.getString(2), rs.getString(3));
        }
        return null;
    }
}
