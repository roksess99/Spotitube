package nl.han.oose.dea.resources;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.Entity.PlaylistEntity;
import nl.han.oose.dea.Entity.PlaylistsEntity;
import nl.han.oose.dea.Entity.TrackEntity;
import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.services.PlaylistsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class PlaylistResourceTest {
    @Mock
    private PlaylistsService mockedPlaylistsService;
    private PlaylistResource sut;

    @BeforeEach
    void setUp() {
        this.mockedPlaylistsService = Mockito.mock(PlaylistsService.class);
        this.sut = new PlaylistResource();
        this.sut.setPlaylistsService(mockedPlaylistsService);
    }

    @Test
    void shouldReturnPlaylistWhenGetPlaylist() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        Mockito.when(mockedPlaylistsService.getPlaylists(token)).thenReturn(playlistsEntity);

        //Act
        Response response = this.sut.getPlaylists(token);

        //Assert
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(playlistsEntity, response.getEntity());
    }

    @Test
    void shouldReturn404WhenPlaylistIsNotFound() {
        //Arrange
        String token = "1234-1234-1234";
        Mockito.when(mockedPlaylistsService.getPlaylists(token)).thenThrow(new ServiceException("Het ophalen van playlists is mislukt"));

        //Act
        Response response = this.sut.getPlaylists(token);

        //Assert
        Assertions.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
        Assertions.assertNull(response.getEntity());
    }

    @Test
    void shouldAddPlaylistWhenAddPlaylist() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity playlistEntity = new PlaylistEntity();
        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        Mockito.when(mockedPlaylistsService.addPlaylist(token, playlistEntity)).thenReturn(playlistsEntity);

        //Act
        Response response = this.sut.addPlaylists(token, playlistEntity);

        //Assert
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(playlistsEntity, response.getEntity());
    }

    @Test
    void shouldReturn400WhenPlaylistObjectIsInvalid() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity invalidObject = new PlaylistEntity();
        Mockito.when(mockedPlaylistsService.addPlaylist(token, invalidObject)).thenThrow(new ServiceException("Playlist toevoegen is niet mogelijk"));

        //Act
        Response response = this.sut.addPlaylists(token, invalidObject);

        //Assert
        Assertions.assertNull(response.getEntity());
        Assertions.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    void shouldDeletePlaylistWhenDeletePlaylist() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity playlistEntity = new PlaylistEntity();
        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        Mockito.when(mockedPlaylistsService.deletePlaylist(token, playlistEntity.getId())).thenReturn(playlistsEntity);

        //Act
        Response response = this.sut.deletePlaylist(token, playlistEntity.getId());

        //Assert
        Assertions.assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
        Assertions.assertEquals(playlistsEntity, response.getEntity());
    }

    @Test
    void shouldReturn404WhenPlaylistIdIsNotFound() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity playlistEntity = new PlaylistEntity();
        Mockito.when(mockedPlaylistsService.deletePlaylist(token, playlistEntity.getId())).thenThrow(new ServiceException("Het verwijderen van playlist met id: " + playlistEntity.getId() + "is niet mogelijk"));

        //Act
        Response response = this.sut.deletePlaylist(token, playlistEntity.getId());

        //Assert
        Assertions.assertNull(response.getEntity());
        Assertions.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void shouldEditPlaylistWhenEditPlaylist() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity playlistEntity = new PlaylistEntity();
        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        Mockito.when(mockedPlaylistsService.editPlaylist(token, playlistEntity)).thenReturn(playlistsEntity);

        //Act
        Response response = this.sut.editPlaylist(token, playlistEntity);

        //Assert
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(playlistsEntity, response.getEntity());
    }

    @Test
    void shouldReturn400WhenEditPlaylistObjectIsInvalid() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity playlistEntity = new PlaylistEntity();
        Mockito.when(mockedPlaylistsService.editPlaylist(token, playlistEntity)).thenThrow(new ServiceException("Het bijwerken van playlist met id: " + playlistEntity.getId() + " is niet mogelijk"));

        //Act
        Response response = this.sut.editPlaylist(token, playlistEntity);

        //Assert
        Assertions.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
        Assertions.assertNull(response.getEntity());
    }

    @Test
    void shouldGetPlaylistTracksWhenGetPlaylistTracks() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity playlistEntity = new PlaylistEntity();
        TracksEntity tracks = new TracksEntity();
        Mockito.when(mockedPlaylistsService.getPlaylistTracks(playlistEntity.getId())).thenReturn(tracks);

        //Act
        Response response = this.sut.getPlaylistTracks(token, playlistEntity.getId());

        //Assert
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(tracks, response.getEntity());
    }

    @Test
    void shouldReturn404WhenPlaylistTracksNotFound() {
        String token = "1234-1234-1234";
        PlaylistEntity playlistEntity = new PlaylistEntity();
        Mockito.when(mockedPlaylistsService.getPlaylistTracks(playlistEntity.getId())).thenThrow(new ServiceException("Tracks ophalen van playlist met id: " + playlistEntity.getId() + " is niet mogelijk"));

        //Act
        Response response = this.sut.getPlaylistTracks(token, playlistEntity.getId());

        //Assert
        Assertions.assertNull(response.getEntity());
        Assertions.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void shouldDeleteTrackFromPlaylistWhenDeleteTrackFromPlaylist() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity playlist = new PlaylistEntity();
        TrackEntity track = new TrackEntity();
        TracksEntity tracks = new TracksEntity();
        Mockito.when(mockedPlaylistsService.deleteTrackFromPlaylist(playlist.getId(), track.getId())).thenReturn(tracks);

        //Act
        Response response = this.sut.deleteTrackFromPlaylist(token, playlist.getId(), track.getId());

        //Assert
        Assertions.assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
        Assertions.assertEquals(tracks, response.getEntity());
    }

    @Test
    void shouldReturn404WhenDeleteTrackFromPlaylist() {
        //Arrange
        String token = "1234-1234-1234";
        PlaylistEntity playlist = new PlaylistEntity();
        TrackEntity track = new TrackEntity();
        Mockito.when(mockedPlaylistsService.deleteTrackFromPlaylist(playlist.getId(), track.getId())).thenThrow(new ServiceException("Track met id:" + track.getId() + "uit playlist met id: " + playlist.getId() + " verwijderen is niet mogelijk"));

        //Act
        Response response = this.sut.deleteTrackFromPlaylist(token, playlist.getId(), track.getId());

        //Assert
        Assertions.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
        Assertions.assertNull(response.getEntity());
    }

    @Test
    void shouldAddTrackToPlaylistWhenAddTrackToPlaylist() {
        //Arrange
        String token = "1234-1234-1234";
        TrackEntity track = new TrackEntity();
        PlaylistsEntity playlists = new PlaylistsEntity();
        Mockito.when(mockedPlaylistsService.addTrack(track.getId(), token, track)).thenReturn(playlists);

        //Act
        Response response = this.sut.addTrackToPlaylist(track.getId(), token, track);

        //Assert
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(playlists, response.getEntity());
    }

    @Test
    void shouldReturn400WhenAddTrackToPlaylistObjectIsInvalid() {
        //Arrange
        String token = "1234-1234-1234";
        TrackEntity track = new TrackEntity();
        Mockito.when(mockedPlaylistsService.addTrack(track.getId(), token, track)).thenThrow(new ServiceException("Het toevoegen van track met id " + track.getId() + " is niet mogelijk"));

        //Act
        Response response = this.sut.addTrackToPlaylist(track.getId(), token, track);

        //Assert
        Assertions.assertNull(response.getEntity());
        Assertions.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }
}
