package nl.han.oose.dea.dao;

import nl.han.oose.dea.Entity.LoginEntity;
import nl.han.oose.dea.Entity.UserEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class UserDAOTest {
    private UserDAO sut;
    private PreparedStatement mockedPreparedStatement;
    private Connection mockedConnection;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp() {
        this.sut = Mockito.spy(new UserDAO());
        this.mockedPreparedStatement = Mockito.mock(PreparedStatement.class);
        this.mockedConnection = Mockito.mock(Connection.class);
        this.mockedResultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    void shouldValidateUserWhenValidateUser() throws SQLException {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();
        String name = "Rody";
        String user = "rody026";
        String password = "SuperPassword!_#";
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(query);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockedResultSet.getString("name")).thenReturn(name);
        Mockito.when(mockedResultSet.getString("username")).thenReturn(user);
        Mockito.when(mockedResultSet.getString("password")).thenReturn(password);

        //Act
        UserEntity response = this.sut.validateUser(loginEntity);

        //Assert
        Assertions.assertEquals(response.getUser(), user);
        Assertions.assertEquals(response.getPassword(), password);
        Assertions.assertEquals(response.getName(), name);
    }

    @Test
    void shouldNotValidateUserWhenObjectIdInvalid() throws SQLException {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();
        UserEntity userEntity;
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.getString("name")).thenReturn("name");
        Mockito.when(mockedResultSet.getString("username")).thenReturn("name");
        Mockito.when(mockedResultSet.getString("password")).thenReturn("name");

        //Act
        userEntity = this.sut.validateUser(loginEntity);

        //Assert
        Assertions.assertNull(userEntity.getUser());
        Assertions.assertNull(userEntity.getPassword());
        Assertions.assertNull(userEntity.getName());
        Assertions.assertNull(userEntity.getToken());
    }

    @Test
    void shouldThrowExceptionWhenValidateUser() throws SQLException {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        Mockito.when(sut.startQuery(sql)).thenThrow(new DatabaseException("Inloggen mislukt"));

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            this.sut.validateUser(loginEntity);
        });

        //Assert
        Assertions.assertEquals(exception.getMessage(), "Inloggen mislukt");
    }

    @Test
    void shouldSetTokenWhenSetToken() throws SQLException {
        //Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUser("Rody");
        String query = "UPDATE user SET token = ?, token_CreationTime = ? WHERE username = ?";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(query);
        Mockito.when(mockedPreparedStatement.executeUpdate()).thenReturn(1);

        //Act
        UserEntity response = this.sut.setUserToken(userEntity);

        //Assert
        Mockito.verify(mockedPreparedStatement).execute();
        Assertions.assertEquals(response.getUser(), userEntity.getUser());
        Assertions.assertNotNull(response.getToken());
    }

    @Test
    void shouldNotSetTokenWhenUserNotFound() throws SQLException {
        //Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUser("Rody");
        String query = "UPDATE user SET token = ?, token_CreationTime = ? WHERE username = ?";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(query);
        Mockito.when(mockedPreparedStatement.executeUpdate()).thenReturn(0);

        //Act
        UserEntity response = this.sut.setUserToken(userEntity);

        //Assert
        Assertions.assertNull(response.getToken());
    }

    @Test
    void shouldThrowExceptionWhenSetToken() throws SQLException {
        //Arrange
        UserEntity userEntity = new UserEntity();
        String sql = "UPDATE user SET token = ?, token_CreationTime = ? WHERE username = ?";
        Mockito.when(sut.startQuery(sql)).thenThrow(new DatabaseException("Het maken van een token voor gebruiker: " + userEntity.getUser() + " is mislukt"));

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.setUserToken(userEntity);
        });

        //Assert
        Assertions.assertEquals(exception.getMessage(), "Het maken van een token voor gebruiker: " + userEntity.getUser() + " is mislukt");
    }

    @Test
    void shouldGetUserWhenGetUser() throws SQLException {
        //Arrange
        String token = String.valueOf(UUID.randomUUID());
        String sql = "SELECT username FROM user WHERE token = ?";
        String user = "rody";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockedResultSet.getString("username")).thenReturn(user);

        //Act
        String response = this.sut.getUser(token);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, user);
    }

    @Test
    void shouldNotReturnUserWhenUserIsNotFound() throws SQLException {
        //Arrange
        String token = String.valueOf(UUID.randomUUID());
        String sql = "SELECT username FROM user WHERE token = ?";
        String user = "rody";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.getString("username")).thenReturn(null);

        //Act
        String response = this.sut.getUser(token);

        //Assert
        Assertions.assertEquals(response, "");
    }

    @Test
    void shouldThrowExceptionWhenGetUser() throws SQLException {
        //Arrange
        String token = String.valueOf(UUID.randomUUID());
        String sql = "SELECT username FROM user WHERE token = ?";
        Mockito.when(sut.startQuery(sql)).thenThrow(new DatabaseException("Het ophalen van gebruiker met token: " + token + " is mislukt"));

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.getUser(token);
        });

        //Assert
        Assertions.assertEquals(exception.getMessage(), "Het ophalen van gebruiker met token: " + token + " is mislukt");
    }
}
