package nl.han.oose.dea.dao;

import nl.han.oose.dea.Entity.TrackEntity;
import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TracksDAO extends DAO {
    public TracksEntity getAllAvailableTracks(int id) {
        TracksEntity tracks = new TracksEntity();
        try {
            String sql = "SELECT * FROM track WHERE id NOT IN (SELECT track_id FROM playlist_track where playlist_id = ? )";
            PreparedStatement statement = startQuery(sql);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            tracks = convertToTracksEntity(tracks, set);

        } catch (SQLException e) {
            throw new DatabaseException("Tracks ophalen is mislukt");
        }
        return tracks;
    }

    private TracksEntity convertToTracksEntity(TracksEntity tracks, ResultSet set) {
        try {
            while (set.next()) {
                int track_id = set.getInt("id");
                String title = set.getString("title");
                String singer = set.getString("singer");
                int duration = set.getInt("duration");
                String album = set.getString("album");
                int playCount = set.getInt("play_count");
                Date date = set.getDate("date");
                String description = set.getString("description");
                boolean isOffline = set.getBoolean("isOffline");
                TrackEntity track = new TrackEntity(track_id, title, singer, duration, album, playCount, date, description, isOffline);
                tracks.addTrack(track);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
