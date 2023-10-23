package nl.han.oose.dea.resources;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.Entity.PlaylistEntity;
import nl.han.oose.dea.Entity.TrackEntity;
import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.services.TracksService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TracksResourceTest {
    private TracksService mockedTracksService;
    private TracksResource sut;

    @BeforeEach
    void setUp() {
        this.mockedTracksService = Mockito.mock(TracksService.class);
        this.sut = new TracksResource();
        this.sut.setTracksService(mockedTracksService);
    }

    @Test
    void shouldGetGetAvailableTracksWhenGetAvailableTracks() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        TracksEntity tracks = new TracksEntity();
        TrackEntity track = new TrackEntity();
        track.setId(1);
        track.setTitle("Track Title");
        track.setAlbum("New release");
        track.setSinger("Rody");
        tracks.addTrack(track);
        Mockito.when(mockedTracksService.getAllAvailableTracks(playlist.getId())).thenReturn(tracks);

        //Act
        Response response = this.sut.getAvailableTracks(playlist.getId());

        //Assert
        Assertions.assertNotNull(response.getEntity());
        Assertions.assertEquals(response.getEntity(), tracks);
    }

    @Test
    void shouldReturn200WhenGetAvailableTracks() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        TracksEntity tracks = new TracksEntity();
        Mockito.when(mockedTracksService.getAllAvailableTracks(playlist.getId())).thenReturn(tracks);

        //Act
        Response response = this.sut.getAvailableTracks(playlist.getId());

        //Assert
        Assertions.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        Assertions.assertEquals(response.getEntity(), tracks);
    }

    @Test
    void shouldReturn404WhenGetAvailableTracks() {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(0);
        Mockito.when(mockedTracksService.getAllAvailableTracks(playlist.getId())).thenThrow(new ServiceException("Tracks ophalen van playlist met id: " + playlist.getId() + " mislukt"));

        //Act
        Response response = this.sut.getAvailableTracks(playlist.getId());

        //Assert
        Assertions.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
        Assertions.assertNull(response.getEntity());
    }
}
