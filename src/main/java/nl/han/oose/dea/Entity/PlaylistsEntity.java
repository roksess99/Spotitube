package nl.han.oose.dea.Entity;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsEntity {
    private List<PlaylistEntity> playlists = new ArrayList<>();
    private int length;

    public PlaylistsEntity() {
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<PlaylistEntity> getPlaylists() {
        return playlists;
    }

    public void addPlaylist(PlaylistEntity playlistEntity) {
        playlists.add(playlistEntity);
    }
}
