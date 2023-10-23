package nl.han.oose.dea.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TracksEntityTest {
    @Test
    void tracksEntityTest(){
        TracksEntity tracks = new TracksEntity();
        TrackEntity track = new TrackEntity();
        List<TrackEntity> tracksList = new ArrayList<>();
        tracks.setTracks(tracksList);
        tracks.addTrack(track);

        Assertions.assertNotNull(tracks.getTracks());
    }

    @Test
    void tracksEntityNullTest(){
        TracksEntity tracks = new TracksEntity();
        List<TrackEntity> trackEntityList = tracks.getTracks();

        Assertions.assertEquals(0, trackEntityList.size());
        Assertions.assertEquals(0, tracks.getTracks().size());
    }
}
