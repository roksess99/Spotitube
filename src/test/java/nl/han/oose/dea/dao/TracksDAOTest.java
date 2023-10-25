package nl.han.oose.dea.dao;

import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TracksDAOTest {
    private TracksDAO sut;
    private PreparedStatement mockedPreparedStatement;
    private Connection mockedConnection;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp() {
        this.sut = Mockito.spy(TracksDAO.class);
        this.mockedConnection = Mockito.mock(Connection.class);
        this.mockedPreparedStatement = Mockito.mock(PreparedStatement.class);
        this.mockedResultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    void shouldGetAllAvailableTracksWhenGetAllAvailableTracks() throws SQLException {
        //Arrange
        int playlistId = 1;
        TracksEntity tracks;
        String sql = "SELECT * FROM track WHERE id NOT IN (SELECT track_id FROM playlist_track where playlist_id = ? )";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.next()).thenReturn(true, false);
        Mockito.when(mockedResultSet.getInt("id")).thenReturn(playlistId);


        //Act
        tracks = this.sut.getAllAvailableTracks(playlistId);

        //Assert
        Assertions.assertNotNull(tracks);
        Assertions.assertEquals(tracks.getTracks().size(), 1);
    }

    @Test
    void shouldThrowExceptionWhenGetAllAvailableTracks() throws SQLException {
        //Arrange
        int playlistId = 1;
        String sql = "SELECT * FROM track WHERE id NOT IN (SELECT track_id FROM playlist_track where playlist_id = ? )";
        Mockito.when(sut.startQuery(sql)).thenThrow(new DatabaseException("Tracks ophalen is mislukt"));

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.getAllAvailableTracks(playlistId);
        });

        //Assert
        Assertions.assertEquals(exception.getMessage(), "Tracks ophalen is mislukt");
    }
}
