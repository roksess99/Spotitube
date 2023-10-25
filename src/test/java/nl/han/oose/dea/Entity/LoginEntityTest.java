package nl.han.oose.dea.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginEntityTest {

    @Test
    void loginEntityTest() {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUser("Rody");
        loginEntity.setPassword("StrongPa$$w0rd#");

        //Assert
        Assertions.assertEquals("Rody", loginEntity.getUser());
        Assertions.assertEquals("StrongPa$$w0rd#", loginEntity.getPassword());
    }

    @Test
    void loginEntityNullTest() {
        //Arrange
        LoginEntity loginEntity = new LoginEntity();

        //Assert
        Assertions.assertNull(loginEntity.getUser());
        Assertions.assertNull(loginEntity.getPassword());
    }
}
