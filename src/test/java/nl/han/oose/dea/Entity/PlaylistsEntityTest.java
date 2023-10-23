package nl.han.oose.dea.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PlaylistsEntityTest {

    @Test
    void playlistsEntityTest() {
        PlaylistsEntity playlists = new PlaylistsEntity();
        PlaylistEntity playlist = new PlaylistEntity();
        playlists.addPlaylist(playlist);
        playlists.setLength(444);

        Assertions.assertEquals(444, playlists.getLength());
        Assertions.assertNotNull(playlists.getPlaylists());
    }

    @Test
    void playlistsEntityNullTest() {
        PlaylistsEntity playlists = new PlaylistsEntity();
        List<PlaylistEntity> playlistEntityList = playlists.getPlaylists();

        Assertions.assertEquals(0, playlistEntityList.size());
        Assertions.assertEquals(0, playlists.getLength());
    }
}
