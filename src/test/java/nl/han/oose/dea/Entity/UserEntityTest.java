package nl.han.oose.dea.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserEntityTest {

    @Test
    void userEntityTest() {
        //Arrange
        UserEntity user = new UserEntity();
        user.setUser("Rody_026_");
        user.setPassword("suPerPa$$w0rd@#");
        user.setName("Rody");
        String token = String.valueOf(UUID.randomUUID());
        user.setToken(token);

        //Assert
        Assertions.assertEquals(user.getUser(), "Rody_026_");
        Assertions.assertEquals(user.getPassword(), "suPerPa$$w0rd@#");
        Assertions.assertEquals(user.getName(), "Rody");
        Assertions.assertEquals(user.getToken(), token);
    }

    @Test
    void userEntityNullTest() {
        //Arrange
        UserEntity user = new UserEntity();

        //Assert
        Assertions.assertNull(user.getUser());
        Assertions.assertNull(user.getPassword());
        Assertions.assertNull(user.getName());
        Assertions.assertNull(user.getToken());
    }
}
