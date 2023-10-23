package nl.han.oose.dea.services;

import nl.han.oose.dea.Entity.PlaylistEntity;
import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;
import nl.han.oose.dea.dao.TracksDAO;
import nl.han.oose.dea.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class TracksServiceTest {
    @Mock
    private TracksDAO mockedTracksDAO;
    private UserDAO mockedUserDAO;
    private TracksService sut;

    @BeforeEach
    void setUp() {
        this.mockedTracksDAO = Mockito.mock(TracksDAO.class);
        this.mockedUserDAO = Mockito.mock(UserDAO.class);
        this.sut = new TracksService();
        this.sut.setTracksDAO(mockedTracksDAO);
        this.sut.setUserDAO(mockedUserDAO);
    }

    @Test
    void shouldGetAllAvailableTracksWhenGetAllAvailableTracks() {
        //Arrange
        TracksEntity tracks = new TracksEntity();
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        Mockito.when(mockedTracksDAO.getAllAvailableTracks(playlist.getId())).thenReturn(tracks);

        //Act
        TracksEntity response = this.sut.getAllAvailableTracks(playlist.getId());

        //Assert
        Assertions.assertEquals(response, tracks);
    }

    @Test
    void shouldReturnExceptionWhenIdIsInvalid() {
        //Arrange
        TracksEntity tracks = new TracksEntity();
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(-2);
        Mockito.when(mockedTracksDAO.getAllAvailableTracks(playlist.getId())).thenThrow(new DatabaseException("Tracks ophalen van playlist met id: " + playlist.getId() + " mislukt"));

        //Act
        TracksEntity response = this.sut.getAllAvailableTracks(playlist.getId());

        //Assert
        Assertions.assertNull(response);
        Assertions.assertNotEquals(response, tracks);
        Assertions.assertThrows(DatabaseException.class, () -> {
            mockedTracksDAO.getAllAvailableTracks(playlist.getId());
        });
    }
}
