package nl.han.oose.dea.dao;

import nl.han.oose.dea.Entity.LoginEntity;
import nl.han.oose.dea.Entity.UserEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserDAO extends DAO {

    public UserEntity validateUser(LoginEntity loginEntity) throws DatabaseException {
        UserEntity userEntity = new UserEntity();
        try {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement statement = startQuery(query);
            statement.setString(1, loginEntity.getUser());
            statement.setString(2, loginEntity.getPassword());
            ResultSet set = statement.executeQuery();
            setToEntity(userEntity, set);
        } catch (SQLException e) {
            throw new DatabaseException("Inloggen mislukt");
        }
        return userEntity;
    }

    private void setToEntity(UserEntity userEntity, ResultSet set) throws SQLException {
        while (set.next()) {
            userEntity.setName(set.getString("name"));
            userEntity.setUser(set.getString("username"));
            userEntity.setPassword(set.getString("password"));
            hasAToken(userEntity);
        }
    }

    private boolean hasAToken(UserEntity userEntity) {
        try {
            String query = "SELECT token FROM user WHERE username = ? AND password = ?";
            PreparedStatement statement = startQuery(query);
            statement.setString(1, userEntity.getUser());
            statement.setString(2, userEntity.getPassword());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                userEntity.setToken(set.getString("token"));
            }
            if (userEntity.getToken() != null) {
                return true;
            } else {
                setUserToken(userEntity);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Token ophalen van gebruiker: " + userEntity.getUser() + " is mislukt");
        }
        return false;
    }

    public String getUser(String token) {
        String user = "";
        try {
            String sql = "SELECT username FROM user WHERE token = ?";
            PreparedStatement statement = startQuery(sql);
            statement.setString(1, token);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                user = set.getString("username");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Het ophalen van gebruiker met token: " + token + " is mislukt");
        }
        return user;
    }

    public UserEntity setUserToken(UserEntity userEntity) {
        String token = String.valueOf(UUID.randomUUID());
        try {
            String sql = "UPDATE user SET token = ?, token_CreationTime = ? WHERE username = ?";
            PreparedStatement statement = startQuery(sql);
            statement.setString(1, token);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(3, userEntity.getUser());
            statement.execute();
            updateRow(userEntity, token, statement);
        } catch (SQLException e) {
            userEntity.setToken(null);
            throw new DatabaseException("Het maken van een token voor gebruiker: " + userEntity.getUser() + " is mislukt");
        }
        return userEntity;
    }

    private void updateRow(UserEntity userEntity, String token, PreparedStatement statement) throws SQLException {
        int rowUpdate = statement.executeUpdate();
        if (rowUpdate > 0) {
            userEntity.setToken(token);
        } else {
            userEntity.setToken(null);
        }
    }
}
