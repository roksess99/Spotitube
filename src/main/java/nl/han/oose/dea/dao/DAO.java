package nl.han.oose.dea.dao;

import nl.han.oose.dea.dao.util.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO {
    protected PreparedStatement startQuery(String sql) throws SQLException {
        DatabaseProperties databaseProperties = new DatabaseProperties();
        Connection connection = DriverManager.getConnection(databaseProperties.getDatabaseUrl(), databaseProperties.getDatabaseUsername(), databaseProperties.getDatabasePassword());
        return connection.prepareStatement(sql);
    }
}
