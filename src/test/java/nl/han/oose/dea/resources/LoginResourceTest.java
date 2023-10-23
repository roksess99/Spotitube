package nl.han.oose.dea.resources;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.Entity.LoginEntity;
import nl.han.oose.dea.Entity.UserEntity;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.services.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class LoginResourceTest {

    @Mock
    private LoginService mockedLoginService;
    private LoginResource sut;

    @BeforeEach
    void setUp() {
        this.mockedLoginService = Mockito.mock(LoginService.class);
        this.sut = new LoginResource();
        this.sut.setLoginService(mockedLoginService);
    }

    @Test
    void shouldValidateUserWhenValidateUser() {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUser("Rody");
        loginEntity.setPassword("WachtwoordVANrody!@#");
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Rody");
        userEntity.setPassword("WachtwoordVANrody!@#");
        Mockito.when(mockedLoginService.validateUser(loginEntity)).thenReturn(userEntity);

        //Act
        Response response = this.sut.Login(loginEntity);

        //Assert
        Assertions.assertEquals(response.getEntity(), userEntity);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(userEntity.getName(), loginEntity.getUser());
        Assertions.assertEquals(userEntity.getPassword(), loginEntity.getPassword());
    }

    @Test
    void shouldReturn200WhenUserFound() {
        //Arrange
        UserEntity userEntity = new UserEntity();
        LoginEntity loginEntity = new LoginEntity();
        Mockito.when(mockedLoginService.validateUser(loginEntity)).thenReturn(userEntity);

        //Act
        Response response = sut.Login(loginEntity);

        //Assert
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(userEntity, response.getEntity());
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        // Arrange
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUser("noUser");
        loginEntity.setPassword("noPassword");
        Mockito.when(mockedLoginService.validateUser(loginEntity)).thenThrow(new ServiceException("User not found"));

        // Act
        Response response = sut.Login(loginEntity);

        // Assert
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
