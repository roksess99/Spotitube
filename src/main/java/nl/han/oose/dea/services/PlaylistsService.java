package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.Entity.PlaylistEntity;
import nl.han.oose.dea.Entity.PlaylistsEntity;
import nl.han.oose.dea.Entity.TrackEntity;
import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.dao.PlaylistsDAO;
import nl.han.oose.dea.dao.UserDAO;

import java.util.Objects;

public class PlaylistsService {
    private PlaylistsDAO playlistsDAO;
    private PlaylistsEntity playlistsEntity;
    private UserDAO userDAO;
    private String user;
    private TracksEntity tracks;

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setPlaylistsDAO(PlaylistsDAO playlistsDAO) {
        this.playlistsDAO = playlistsDAO;
    }

    public PlaylistsEntity getPlaylists(String token) {
        try {
            if (token != null && !token.equals("")) {
                String user = userDAO.getUser(token);
                playlistsEntity = playlistsDAO.getPlaylists(user);
            }
        } catch (DatabaseException e) {
            throw new ServiceException("Het ophalen van playlists is mislukt");
        }
        return playlistsEntity;
    }

    public PlaylistsEntity addPlaylist(String token, PlaylistEntity playlistEntity) {
        try {
            if (token != null && !token.equals("") && playlistEntity != null) {
                playlistsDAO.addPlaylist(playlistEntity.getName(), userDAO.getUser(token));
                playlistsEntity = getPlaylists(token);
            }
        } catch (DatabaseException e) {
            throw new ServiceException("Playlist: " + playlistEntity.getName() + " toevoegen is niet mogelijk");
        }
        return playlistsEntity;
    }

    public PlaylistsEntity deletePlaylist(String token, int id) {
        try {
            if (!Objects.equals(token, "") && token != null && id != 0 && !(id < 0)) {
                user = userDAO.getUser(token);
                playlistsDAO.deletePlaylist(id);
                playlistsEntity = getPlaylists(user);
            }
        } catch (DatabaseException e) {
            throw new ServiceException("Het verwijderen van playlist met id: " + id + "is niet mogelijk");
        }
        return playlistsEntity;
    }

    public PlaylistsEntity editPlaylist(String token, PlaylistEntity playlistEntity) {
        try {
            if (token != null && !token.equals("") && playlistEntity != null) {
                user = userDAO.getUser(token);
                playlistsDAO.editPlaylist(playlistEntity);
                playlistsEntity = getPlaylists(user);
            }
        } catch (DatabaseException e) {
            throw new ServiceException("Het bijwerken van playlist met id: " + playlistEntity.getId() + " is niet mogelijk");
        }
        return playlistsEntity;
    }

    public TracksEntity getPlaylistTracks(int id) {
        try {
            if (id != 0 && !(id < 0)) {
                tracks = playlistsDAO.getPlaylistTracks(id);
            }
        } catch (DatabaseException e) {
            throw new ServiceException("Tracks ophalen van playlist met id: " + id + " is niet mogelijk");
        }
        return tracks;
    }

    public TracksEntity deleteTrackFromPlaylist(int playlistId, int trackId) {
        try {
            if (playlistId != 0 && !(playlistId < 0) && trackId != 0 && !(trackId < 0))
                playlistsDAO.deleteTrackFromPlaylist(playlistId, trackId);
            tracks = getPlaylistTracks(playlistId);
        } catch (DatabaseException e) {
            throw new ServiceException("Track met id:" + trackId + "uit playlist met id: " + playlistId + " verwijderen is niet mogelijk");
        }
        return tracks;
    }

    public PlaylistsEntity addTrack(int id, String token, TrackEntity track) {
        try {
            if (!Objects.equals(token, "") && token != null && id != 0 && !(id < 0) && track != null) {
                playlistsDAO.addTrack(id, track.getId());
                playlistsEntity = getPlaylists(token);
            }
        } catch (DatabaseException e) {
            throw new ServiceException("Het toevoegen van track met id " + id + " is niet mogelijk");
        }
        return playlistsEntity;
    }
}
