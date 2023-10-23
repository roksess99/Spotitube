package nl.han.oose.dea.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PlaylistEntityTest {

    @Test
    void playlistEntityTest(){
        PlaylistEntity playlist = new PlaylistEntity();
        TrackEntity track = new TrackEntity();
        List<TrackEntity> tracks = new ArrayList<>();
        tracks.add(track);
        playlist.setId(1);
        playlist.setName("Workout music");
        playlist.setLength(555);
        playlist.setOwner(true);
        playlist.setTracks(tracks);

        Assertions.assertEquals(1, playlist.getId());
        Assertions.assertEquals("Workout music", playlist.getName());
        Assertions.assertEquals(555, playlist.getLength());
        Assertions.assertTrue(playlist.isOwner());
        Assertions.assertNotNull(playlist.getTracks());
    }

    @Test
    void playlistEntityNullTest(){
        PlaylistEntity playlist = new PlaylistEntity();

        Assertions.assertNull(playlist.getName());
        Assertions.assertEquals(0, playlist.getId());
        Assertions.assertEquals(0, playlist.getLength());
        Assertions.assertFalse(playlist.isOwner());
        Assertions.assertNull(playlist.getTracks());
    }
}
