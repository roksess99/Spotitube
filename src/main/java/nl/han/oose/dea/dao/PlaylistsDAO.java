package nl.han.oose.dea.dao;

import nl.han.oose.dea.Entity.PlaylistEntity;
import nl.han.oose.dea.Entity.PlaylistsEntity;
import nl.han.oose.dea.Entity.TrackEntity;
import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistsDAO extends DAO {
    private PlaylistEntity playlistEntity;

    public void addPlaylist(String name, String user) {
        try {
            String sql = "INSERT INTO playlist (name, owner) VALUES (?, ?)";
            PreparedStatement statement = startQuery(sql);
            statement.setString(1, name);
            statement.setString(2, user);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseException("Playlist: " + name + " toevoegen is mislukt");
        }
    }

    public PlaylistsEntity getPlaylists(String user) {
        PlaylistsEntity playlistsEntity = new PlaylistsEntity();
        try {
            String query = "SELECT * FROM playlist ";
            PreparedStatement statement = startQuery(query);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String owner = set.getString("owner");
                isOwner(user, playlistsEntity, id, name, owner);
                PlaylistEntity playlistEntity = new PlaylistEntity();
                playlistsEntity.setLength(getPlaylistLength(playlistEntity));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Playlists ophalen mislukt");
        }
        return playlistsEntity;
    }

    public int getPlaylistLength(PlaylistEntity playlistEntity) {
        try {
            String query = "SELECT SUM(t.duration) as total_duration FROM playlist p " + "JOIN playlist_track pt ON p.id = pt.playlist_id " + "JOIN track t ON pt.track_id = t.id " + "WHERE p.id = ?";
            PreparedStatement statement = startQuery(query);
            statement.setInt(1, playlistEntity.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                playlistEntity.setLength(set.getInt("total_duration"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("playlist lengte ophalen mislukt");
        }
        return playlistEntity.getLength();
    }

    public void isOwner(String user, PlaylistsEntity playlistsEntity, int id, String name, String owner) {
        if (owner.equals(user)) {
            playlistEntity = new PlaylistEntity(id, name, true);
        } else {
            playlistEntity = new PlaylistEntity(id, name, false);
        }
        playlistsEntity.addPlaylist(playlistEntity);
    }

    public void deletePlaylist(int id) {
        try {
            String sql = "DELETE FROM playlist WHERE id = ?";
            PreparedStatement statement = startQuery(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Playlist met id: " + id + " bestaat niet");
        }
    }

    public void editPlaylist(PlaylistEntity playlistEntity) {
        try {
            String sql = "UPDATE playlist SET name = ? WHERE id = ?";
            PreparedStatement statement = startQuery(sql);
            statement.setString(1, playlistEntity.getName());
            statement.setInt(2, playlistEntity.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Het updaten van playlist: " + playlistEntity.getName() + " is mislukt");
        }
    }

    public TracksEntity getPlaylistTracks(int id) {
        TracksEntity tracks = new TracksEntity();
        try {
            String sql = "SELECT playlist_id, track_id FROM playlist_track WHERE playlist_id = ?";
            PreparedStatement statement = startQuery(sql);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            getTrack(tracks, set);
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseException("Het ophalen van playlist met id: " + id + " is mislukt");
        }
        return tracks;
    }

    public void getTrack(TracksEntity tracks, ResultSet set) {
        int trackId = 0;
        try {
            while (set.next()) {
                trackId = set.getInt("track_id");
                String sqlTrack = "SELECT id, title, singer, duration, album,play_count,date,description,isOffline FROM track WHERE id = ? ";
                PreparedStatement trackStatement = startQuery(sqlTrack);
                trackStatement.setInt(1, trackId);
                ResultSet trackSet = trackStatement.executeQuery();

                if (trackSet.next()) {
                    convertToTracksEntity(tracks, trackSet);
                }
                trackStatement.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Het ophalen van track met id: " + trackId + " is mislukt");
        }
    }

    public void convertToTracksEntity(TracksEntity tracks, ResultSet trackSet) {
        try {
            int tId = trackSet.getInt("id");
            String title = trackSet.getString("title");
            String singer = trackSet.getString("singer");
            int duration = trackSet.getInt("duration");
            String album = trackSet.getString("album");
            int playCount = trackSet.getInt("play_count");
            Date date = trackSet.getDate("date");
            String description = trackSet.getString("description");
            boolean isOffline = trackSet.getBoolean("isOffline");
            TrackEntity track = new TrackEntity(tId, title, singer, duration, album, playCount, date, description, isOffline);
            tracks.addTrack(track);
        } catch (SQLException e) {
            throw new DatabaseException("Het maken van een track is mislukt");
        }
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        try {
            String sql = "DELETE FROM playlist_track WHERE playlist_id = ? AND track_id = ?";
            PreparedStatement statement = startQuery(sql);
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("het playlist/track id bestaat niet");
        }
    }

    public void addTrack(int playlistId, int trackId) {
        try {
            String sql = "INSERT INTO playlist_track VALUES (?,?)";
            PreparedStatement statement = startQuery(sql);
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Het playlist/track bestaat niet");
        }
    }
}
