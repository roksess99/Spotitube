package nl.han.oose.dea.Exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceExceptionTest {
    private ServiceException sut;

    @Test
    void shouldThrowExceptionWithMessage() {
        //Arrange
        String msg = "This is an error message";

        //Act
        this.sut = new ServiceException(msg);

        //Assert
        Assertions.assertEquals(msg, sut.getMessage());
    }

    @Test
    void shouldThrowExceptionWithNullMessage() {
        //Act
        this.sut = new ServiceException(null);

        //Assert
        Assertions.assertNull(sut.getMessage());
    }
}
