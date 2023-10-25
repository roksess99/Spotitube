package nl.han.oose.dea.services;

import nl.han.oose.dea.Entity.PlaylistEntity;
import nl.han.oose.dea.Entity.PlaylistsEntity;
import nl.han.oose.dea.Entity.TrackEntity;
import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.dao.PlaylistsDAO;
import nl.han.oose.dea.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class PlaylistServiceTest {
    @Mock
    private PlaylistsService sut;
    private PlaylistsDAO mockedPlaylistDao;
    private UserDAO mockedUserDao;

    @BeforeEach
    void stUp() {
        this.mockedPlaylistDao = Mockito.mock(PlaylistsDAO.class);
        this.mockedUserDao = Mockito.mock(UserDAO.class);
        this.sut = new PlaylistsService();
        this.sut.setPlaylistsDAO(mockedPlaylistDao);
        this.sut.setUserDAO(mockedUserDao);
    }

    @Test
    void shouldGetPlaylistsWhenGetPlaylists() {
        //Arrange
        String token = "1234-1234-1234";
        String user = "mo";
        PlaylistsEntity playlists = new PlaylistsEntity();
        Mockito.when(mockedUserDao.getUser(token)).thenReturn(user);
        Mockito.when(mockedPlaylistDao.getPlaylists(user)).thenReturn(playlists);

        //Act
        PlaylistsEntity response = this.sut.getPlaylists(token);

        //Assert
        Assertions.assertEquals(mockedPlaylistDao.getPlaylists(user), playlists);
        Assertions.assertEquals(response, playlists);
    }

    @Test
    void shouldThrowExceptionWhenGetPlaylists() {
        //Arrange
        String token = "InvalidToken";
        String user = "mo";
        PlaylistsEntity playlists = new PlaylistsEntity();
        Mockito.when(mockedUserDao.getUser(token)).thenReturn(user);
        Mockito.when(sut.getPlaylists(token)).thenThrow(new ServiceException("Het ophalen van playlists is mislukt"));

        //Act
        ServiceException exception = Assertions.assertThrows(ServiceException.class, () -> {
            sut.getPlaylists(token);
        });

        //Assert
        Assertions.assertEquals(exception.getMessage(), "Het ophalen van playlists is mislukt");
    }

    @Test
    void shouldAddPlaylistWhenAddPlaylist() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setName("test");
        PlaylistsEntity playlists = new PlaylistsEntity();
        String token = "1234-1234-1234";
        String user = "mo";
        Mockito.when(mockedUserDao.getUser(token)).thenReturn(user);
        Mockito.when(mockedPlaylistDao.getPlaylists(user)).thenReturn(playlists);

        //Act
        PlaylistsEntity response = this.sut.addPlaylist(token, playlist);

        //Assert
        Assertions.assertEquals(response, playlists);
        Assertions.assertEquals(mockedPlaylistDao.getPlaylists(user), playlists);
        Mockito.verify(mockedPlaylistDao, Mockito.times(1)).addPlaylist(playlist.getName(), user);
        Mockito.verify(mockedPlaylistDao, Mockito.times(2)).getPlaylists(user);
    }

    @Test
    void shouldThrowExceptionWhenAddPlaylist() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setName("test");
        String token = "InvalidToken";
        String user = "mo";
        PlaylistsEntity playlists = new PlaylistsEntity();
        Mockito.when(mockedUserDao.getUser(token)).thenReturn(user);
        Mockito.when(sut.getPlaylists(token)).thenThrow(new ServiceException("Playlist: " + playlist.getName() + " toevoegen is niet mogelijk"));

        //Act
        ServiceException exception = Assertions.assertThrows(ServiceException.class, () -> {
            sut.addPlaylist(token, playlist);
        });

        //Assert
        Assertions.assertEquals(exception.getMessage(), "Playlist: " + playlist.getName() + " toevoegen is niet mogelijk");
    }

    @Test
    void shouldDeletePlaylistWhenDeletePlaylist() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        PlaylistsEntity playlists = new PlaylistsEntity();
        String token = "1234-1234-1234";
        String user = "mo";
        Mockito.when(mockedUserDao.getUser(token)).thenReturn(user);
        Mockito.when(mockedPlaylistDao.getPlaylists(user)).thenReturn(playlists);
        Mockito.when(sut.deletePlaylist(token, playlist.getId())).thenReturn(playlists);

        //Act
        PlaylistsEntity response = this.sut.deletePlaylist(token, playlist.getId());

        //Assert
        Mockito.verify(mockedPlaylistDao, Mockito.times(2)).deletePlaylist(playlist.getId());
        Mockito.verify(mockedUserDao, Mockito.times(2)).getUser(token);
        Assertions.assertEquals(response, playlists);
    }

    @Test
    void shouldThrowExceptionWHenDeletePlaylist() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        PlaylistsEntity playlists = new PlaylistsEntity();
        String token = "1234-1234-1234";
        String user = "mo";
        Mockito.when(mockedUserDao.getUser(token)).thenReturn(user);
        Mockito.when(mockedPlaylistDao.getPlaylists(user)).thenReturn(playlists);
        Mockito.when(sut.deletePlaylist(token, playlist.getId())).thenThrow(new ServiceException("Het verwijderen van playlist met id: " + playlist.getId() + "is niet mogelijk"));

        //Act
        ServiceException exception = Assertions.assertThrows(ServiceException.class, () -> {
            sut.deletePlaylist(token, playlist.getId());
        });

        //Assert
        Assertions.assertEquals(exception.getMessage(), "Het verwijderen van playlist met id: " + playlist.getId() + "is niet mogelijk");
    }

    @Test
    void shouldEditPlaylistWhenEditPlaylist() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        PlaylistsEntity playlists = new PlaylistsEntity();
        String token = "1234-1234-1234";
        String user = "mo";
        Mockito.when(mockedUserDao.getUser(token)).thenReturn(user);
        Mockito.when(mockedPlaylistDao.getPlaylists(user)).thenReturn(playlists);
        Mockito.when(sut.editPlaylist(token, playlist)).thenReturn(playlists);

        //Act
        PlaylistsEntity response = this.sut.editPlaylist(token, playlist);

        //Assert
        Assertions.assertEquals(response, playlists);
    }

    @Test
    void shouldThrowExceptionWhenEditPlaylist() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        PlaylistsEntity playlists = new PlaylistsEntity();
        String token = "1234-1234-1234";
        String user = "mo";
        Mockito.when(mockedUserDao.getUser(token)).thenReturn(user);
        Mockito.when(mockedPlaylistDao.getPlaylists(user)).thenReturn(playlists);
        Mockito.when(sut.editPlaylist(token, playlist)).thenThrow(new ServiceException("Het bijwerken van playlist met id: " + playlist.getId() + " is niet mogelijk"));

        //Act
        ServiceException exception = Assertions.assertThrows(ServiceException.class, () -> {
            sut.editPlaylist(token, playlist);
        });

        //Assert
        Assertions.assertEquals(exception.getMessage(), "Het bijwerken van playlist met id: " + playlist.getId() + " is niet mogelijk");
    }

    @Test
    void shouldGetPlaylistTracksWhenGetPlaylistTracks() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        TracksEntity tracks = new TracksEntity();
        Mockito.when(mockedPlaylistDao.getPlaylistTracks(playlist.getId())).thenReturn(tracks);

        //Act
        TracksEntity response = this.sut.getPlaylistTracks(playlist.getId());

        //Assert
        Assertions.assertEquals(response, tracks);
    }

    @Test
    void shouldDeleteTrackFromPlaylistWhenDeleteTrackFromPlaylist() {
        //Arrange
        TracksEntity tracks = new TracksEntity();
        TrackEntity track = new TrackEntity();
        track.setId(1);
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        Mockito.when(sut.getPlaylistTracks(playlist.getId())).thenReturn(tracks);

        //Act
        TracksEntity response = this.sut.deleteTrackFromPlaylist(playlist.getId(), track.getId());

        //Assert
        Assertions.assertEquals(response, tracks);
    }

    @Test
    void shouldAddTrackWhenAddTrack() {
        //Arrange
        PlaylistsEntity playlists = new PlaylistsEntity();
        TrackEntity track = new TrackEntity();
        PlaylistEntity playlist = new PlaylistEntity();
        track.setId(1);
        playlist.setId(1);
        String token = "1234-1234-1234";
        Mockito.when(sut.getPlaylists(token)).thenReturn(playlists);

        //Act
        PlaylistsEntity response = this.sut.addTrack(playlist.getId(), token, track);

        //Assert
        Assertions.assertEquals(response, playlists);
    }
}
