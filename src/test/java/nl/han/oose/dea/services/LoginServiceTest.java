package nl.han.oose.dea.services;

import nl.han.oose.dea.Entity.LoginEntity;
import nl.han.oose.dea.Entity.UserEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;
import nl.han.oose.dea.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


public class LoginServiceTest {
    @Mock
    private LoginService sut;
    private UserDAO mockedUserDao;

    @BeforeEach
    void setUp() {
        this.mockedUserDao = Mockito.mock(UserDAO.class);
        this.sut = new LoginService();
        this.sut.setUserDAO(mockedUserDao);
    }

    @Test
    void shouldValidateUser() {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();
        UserEntity userEntity = new UserEntity();
        loginEntity.setUser("user");
        loginEntity.setPassword("password");
        Mockito.when(mockedUserDao.validateUser(loginEntity)).thenReturn(userEntity);

        //Act
        UserEntity response = this.sut.validateUser(loginEntity);

        //Assert
        Assertions.assertEquals(response, userEntity);
    }

    @Test
    void shouldNotValidateUserWhenObjectIsNotValid() {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();
        Mockito.when(mockedUserDao.validateUser(loginEntity)).thenThrow(new DatabaseException("User not Found"));
        //Act
        UserEntity response = sut.validateUser(loginEntity);

        //Assert
        Assertions.assertNull(response);
    }

    @Test
    void shouldNotValidateUserWhenObjectIsNotComplete() {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setPassword("password");
        Mockito.when(mockedUserDao.validateUser(loginEntity)).thenThrow(new DatabaseException("User not Found"));
        //Act
        UserEntity response = sut.validateUser(loginEntity);

        //Assert
        Assertions.assertNull(response);
    }
}
