package nl.han.oose.dea.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginEntityTest {

    @Test
    void loginEntityTest(){
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUser("Rody");
        loginEntity.setPassword("StrongPa$$w0rd#");

        Assertions.assertEquals("Rody", loginEntity.getUser());
        Assertions.assertEquals("StrongPa$$w0rd#", loginEntity.getPassword());
    }

    @Test
    void loginEntityNullTest(){
        LoginEntity loginEntity = new LoginEntity();

        Assertions.assertNull(loginEntity.getUser());
        Assertions.assertNull(loginEntity.getPassword());
    }
}
