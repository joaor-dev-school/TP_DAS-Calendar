package tp.das.Service;

import tp.das.Model.Database.UnitOfWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionService {
    private static Connection connection;
    private static UnitOfWork unitOfWork;

    public static Connection getConnection() {
        try {
            if (connection != null) {
                return connection;
            }
            Class.forName("org.h2.Driver");
            connection = DriverManager.
                    getConnection("jdbc:h2:mem:tpdas", "tpdas", "tpdas1920");
            SessionService.generateDatabase(connection);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return connection;
    }

    public static UnitOfWork getUnitOfWork() {
        if (unitOfWork == null) {
            unitOfWork = new UnitOfWork();
        }
        return unitOfWork;
    }

    private static void generateDatabase(Connection c) throws SQLException {
        Statement statement = c.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate("drop table if exists accounts");
        statement.executeUpdate("drop sequence IF EXISTS accounts_seq;");
        statement.executeUpdate("create sequence accounts_seq;");
        statement.executeUpdate
                ("create table accounts (" +
                        "id BIGINT DEFAULT accounts_seq.nextval primary key," +
                        "username VARCHAR(256) NOT NULL," +
                        "password VARCHAR(256) NOT NULL," +
                        "deleted INT DEFAULT 0 NOT NULL" + ")"
                );
    }
}
