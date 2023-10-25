package nl.han.oose.dea.dao;

import nl.han.oose.dea.Entity.PlaylistEntity;
import nl.han.oose.dea.Entity.PlaylistsEntity;
import nl.han.oose.dea.Entity.TrackEntity;
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

public class PlaylistsDAOTest {
    private PlaylistsDAO sut;
    private PreparedStatement mockedPreparedStatement;
    private Connection mockedConnection;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp() {
        this.sut = Mockito.spy(new PlaylistsDAO());
        this.mockedPreparedStatement = Mockito.mock(PreparedStatement.class);
        this.mockedConnection = Mockito.mock(Connection.class);
        this.mockedResultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    void shouldAddPlaylistWhenAddPlaylist() throws SQLException {
        //Arrange
        String playlistName = "Workout playlist";
        String user = "Rody";
        String sql = "INSERT INTO playlist (name, owner) VALUES (?, ?)";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedPreparedStatement.executeUpdate()).thenReturn(1);

        //Act
        sut.addPlaylist(playlistName, user);

        //Assert
        Mockito.verify(sut).startQuery(sql);
        Mockito.verify(mockedPreparedStatement).setString(1, playlistName);
        Mockito.verify(mockedPreparedStatement).setString(2, user);
        Mockito.verify(mockedPreparedStatement).execute();
        Mockito.verify(mockedPreparedStatement).close();
    }

    @Test
    void shouldThrowExceptionWhenAddPlaylist() throws SQLException {
        //Arrange
        String playlistName = "Workout playlist";
        String user = "Rody";
        String sql = "INSERT INTO playlist (name, owner) VALUES (?, ?)";
        Mockito.doThrow(new SQLException()).when(mockedPreparedStatement).execute();
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.addPlaylist(playlistName, user);
        });

        //Assert
        Assertions.assertEquals("Playlist: " + playlistName + " toevoegen is mislukt", exception.getMessage());
    }

    @Test
    void shouldGetPlaylistsWhenGetPlaylists() throws SQLException {
        //Arrange
        String user = "Rody";
        String sql = "SELECT * FROM playlist ";
        PlaylistsEntity playlists;
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.next()).thenReturn(true, false);
        Mockito.when(mockedResultSet.getInt("id")).thenReturn(1);
        Mockito.when(mockedResultSet.getString("name")).thenReturn("My Playlist");
        Mockito.when(mockedResultSet.getString("owner")).thenReturn(user);

        //Act
        playlists = this.sut.getPlaylists(user);

        //Assert
        Mockito.verify(sut).startQuery(sql);
        Mockito.verify(mockedPreparedStatement).executeQuery();
        Mockito.verify(mockedPreparedStatement).executeQuery();
    }

    @Test
    void shouldThrowExceptionWhenGetPlaylist() throws SQLException {
        //Arrange
        String user = "Rody";
        String query = "SELECT * FROM playlist ";
        Mockito.when(sut.startQuery(query)).thenReturn(mockedPreparedStatement);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenThrow(new SQLException());

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.getPlaylists(user);
        });

        //Assert
        Assertions.assertEquals("Playlists ophalen mislukt", exception.getMessage());
    }

    @Test
    void shouldGetPlaylistLength() throws SQLException {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        String query = "SELECT SUM(t.duration) as total_duration FROM playlist p " + "JOIN playlist_track pt ON p.id = pt.playlist_id " + "JOIN track t ON pt.track_id = t.id " + "WHERE p.id = ?";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(query);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockedResultSet.getInt("total_duration")).thenReturn(750);

        //Act
        int playlistLength = this.sut.getPlaylistLength(playlist);

        //Assert
        Mockito.verify(sut).startQuery(query);
        Mockito.verify(mockedPreparedStatement).setInt(1, playlist.getId());
        Mockito.verify(mockedPreparedStatement).executeQuery();
        Assertions.assertEquals(750, playlist.getLength());
    }

    @Test
    void shouldThrowExceptionWhenGetPlaylistLength() throws SQLException {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        String query = "SELECT SUM(t.duration) as total_duration FROM playlist p " + "JOIN playlist_track pt ON p.id = pt.playlist_id " + "JOIN track t ON pt.track_id = t.id " + "WHERE p.id = ?";
        Mockito.when(sut.startQuery(query)).thenReturn(mockedPreparedStatement);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenThrow(new SQLException());

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.getPlaylistLength(playlist);
        });

        //Assert
        Assertions.assertEquals("playlist lengte ophalen mislukt", exception.getMessage());
    }

    @Test
    void isOwnerTest() throws SQLException {
        //Arrange
        String user = "Rody";
        int playlistId = 1;
        String playlistName = "Workout playlist";
        PlaylistsEntity playlists;
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(Mockito.anyString());
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.next()).thenReturn(true, false); // Simulating one record in the result set
        Mockito.when(mockedResultSet.getInt("id")).thenReturn(playlistId);
        Mockito.when(mockedResultSet.getString("name")).thenReturn(playlistName);
        Mockito.when(mockedResultSet.getString("owner")).thenReturn(user);

        //Act
        playlists = this.sut.getPlaylists(user);

        //Assert
        Mockito.verify(sut).isOwner(user, playlists, playlistId, playlistName, user);
        Assertions.assertEquals(1, playlists.getPlaylists().size());
        PlaylistEntity playlist = playlists.getPlaylists().get(0);
        Assertions.assertEquals(1, playlist.getId());
        Assertions.assertEquals(playlistName, playlist.getName());
        Assertions.assertTrue(playlist.isOwner());
    }

    @Test
    void shouldDeletePlaylistWhenDeletePlaylist() throws SQLException {
        //Arrange
        int playlistId = 1;
        String sql = "DELETE FROM playlist WHERE id = ?";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);

        //Act
        this.sut.deletePlaylist(playlistId);

        //Assert
        Mockito.verify(sut).startQuery(sql);
        Mockito.verify(mockedPreparedStatement).setInt(1, playlistId);
        Mockito.verify(mockedPreparedStatement).execute();
    }

    @Test
    void shouldThrowExceptionWhenIdIsInvalid() throws SQLException {
        //Arrange
        int playlistId = 1;
        String sql = "DELETE FROM playlist WHERE id = ?";
        Mockito.doThrow(new SQLException()).when(mockedPreparedStatement).execute();
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.deletePlaylist(playlistId);
        });

        //Assert
        Assertions.assertEquals("Playlist met id: " + playlistId + " bestaat niet", exception.getMessage());
    }

    @Test
    void shouldEditPlaylistWhenEditPlaylist() throws SQLException {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        playlist.setName("New playlist name");
        String sql = "UPDATE playlist SET name = ? WHERE id = ?";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);

        //Act
        sut.editPlaylist(playlist);

        //Assert
        Mockito.verify(mockedPreparedStatement).setString(1, playlist.getName());
        Mockito.verify(mockedPreparedStatement).setInt(2, playlist.getId());
        Mockito.verify(mockedPreparedStatement).execute();
    }

    @Test
    void shouldThrowExceptionWhenEditPlaylist() throws SQLException {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setId(1);
        playlist.setName("New playlist name");
        String sql = "UPDATE playlist SET name = ? WHERE id = ?";
        Mockito.doThrow(new SQLException()).when(mockedPreparedStatement).execute();
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.editPlaylist(playlist);
        });

        //Assert
        Assertions.assertEquals("Het updaten van playlist: " + playlist.getName() + " is mislukt", exception.getMessage());
    }

    @Test
    void shouldGetPlaylistTrackWhenGetPlaylistTrack() throws SQLException {
        //Arrange
        PlaylistEntity playlist = new PlaylistEntity();
        String sql = "SELECT playlist_id, track_id FROM playlist_track WHERE playlist_id = ?";
        playlist.setId(1);
        TracksEntity tracks = new TracksEntity();
        tracks.addTrack(new TrackEntity(1, "Track 1", "Artist 1", 180, "Album 1", 100, null, null, false));
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockedResultSet.getInt("track_id")).thenReturn(1);
        Mockito.when(mockedResultSet.getString("title")).thenReturn("Track 1");
        Mockito.when(mockedResultSet.getString("singer")).thenReturn("Artist 1");
        Mockito.when(mockedResultSet.getInt("duration")).thenReturn(180);
        Mockito.when(mockedResultSet.getString("album")).thenReturn("Album 1");
        Mockito.when(mockedResultSet.getInt("play_count")).thenReturn(100);
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);

        //Act
        TracksEntity result = this.sut.getPlaylistTracks(playlist.getId());

        //Assert
        Mockito.verify(mockedPreparedStatement).setInt(1, playlist.getId());
        Mockito.verify(sut).startQuery(sql);
        Assertions.assertNotNull(result);
    }

    @Test
    void shouldGetTrackWhenGetTrack() throws SQLException {
        //Arrange
        TracksEntity tracks = new TracksEntity();
        int trackId = 0;
        String sql = "SELECT id, title, singer, duration, album, play_count, date, description, isOffline FROM track WHERE id = ? ";
        Mockito.when(sut.startQuery(sql)).thenReturn(mockedPreparedStatement);
        Mockito.when(mockedResultSet.next()).thenReturn(true, false);
        Mockito.when(mockedResultSet.getInt("track_id")).thenReturn(trackId);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);

        // Act
        sut.getTrack(tracks, mockedResultSet);

        //Assert
        Assertions.assertNotNull(tracks);
    }

    @Test
    void shouldConvertToTracksEntityWhenConvertToTracksEntity() throws SQLException {
        //Arrange
        TracksEntity tracks = new TracksEntity();
        Mockito.when(mockedResultSet.getInt("id")).thenReturn(1);
        Mockito.when(mockedResultSet.getString("title")).thenReturn("Death to all");

        //Act
        this.sut.convertToTracksEntity(tracks, mockedResultSet);

        //Assert
        TrackEntity track = tracks.getTracks().get(0);
        Assertions.assertEquals(1, tracks.getTracks().size());
        Assertions.assertEquals(1, track.getId());
        Assertions.assertEquals("Death to all", track.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenConvertToTracksEntity() throws SQLException {
        //Arrange
        TracksEntity tracks = new TracksEntity();
        Mockito.when(mockedResultSet.getInt("id")).thenThrow(new SQLException());

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.convertToTracksEntity(tracks, mockedResultSet);
        });

        //Assert
        Assertions.assertEquals("Het maken van een track is mislukt", exception.getMessage());
    }

    @Test
    void shouldDeleteTrackFromPlaylistWhenDeleteTrackFromPlaylist() throws SQLException {
        //Arrange
        int playlistId = 1;
        int trackId = 1;
        String sql = "DELETE FROM playlist_track WHERE playlist_id = ? AND track_id = ?";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.doThrow(new SQLException()).when(mockedPreparedStatement).execute();

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            sut.deleteTrackFromPlaylist(playlistId, trackId);
        });

        //Assert
        Assertions.assertEquals("het playlist/track id bestaat niet", exception.getMessage());
    }

    @Test
    void shouldAddTrackWhenAddTrack() throws SQLException {
        //Arrange
        int playlistId = 1;
        int trackId = 1;
        String sql = "INSERT INTO playlist_track VALUES (?,?)";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        Mockito.when(mockedPreparedStatement.executeUpdate()).thenReturn(1);

        //Act
        this.sut.addTrack(playlistId, trackId);

        //Assert
        Mockito.verify(sut).startQuery(sql);
        Mockito.verify(mockedPreparedStatement).setInt(1, playlistId);
        Mockito.verify(mockedPreparedStatement).setInt(2, trackId);
        Mockito.verify(mockedPreparedStatement).execute();
    }

    @Test
    void shouldThrowExceptionWhenAddTrack() throws SQLException {
        //Arrange
        int playlistId = 1;
        int trackId = 1;
        String sql = "INSERT INTO playlist_track VALUES (?,?)";
        Mockito.doReturn(mockedPreparedStatement).when(sut).startQuery(sql);
        Mockito.doThrow(new SQLException()).when(mockedPreparedStatement).execute();

        //Act
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            this.sut.addTrack(playlistId, trackId);
        });

        //Assert
        Assertions.assertEquals("Het playlist/track bestaat niet", exception.getMessage());
    }
}
