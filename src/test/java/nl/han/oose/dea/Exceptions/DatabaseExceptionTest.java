package nl.han.oose.dea.Exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseExceptionTest {
    private DatabaseException sut;

    @Test
    void shouldThrowExceptionWithMessage() {
        //Arrange
        String msg = "This is an error message";

        //Act
        this.sut = new DatabaseException(msg);

        //Assert
        Assertions.assertEquals(msg, sut.getMessage());
    }

    @Test
    void shouldThrowExceptionWithNullMessage() {
        //Act
        this.sut = new DatabaseException(null);

        //Assert
        Assertions.assertNull(sut.getMessage());
    }
}
