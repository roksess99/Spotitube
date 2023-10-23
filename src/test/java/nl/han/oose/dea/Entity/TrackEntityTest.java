package nl.han.oose.dea.Entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;

public class TrackEntityTest {

    @Test
    void trackEntityTest(){
        Date date = new Date();
        TrackEntity track = new TrackEntity();
        track.setId(1);
        track.setTitle("Last Day on Earth");
        track.setSinger("Rody");
        track.setDuration(430);
        track.setAlbum("DAY / DAY");
        track.setPlayCount(4);
        track.setDate(date);
        track.setDescription("The best music ever");
        track.setOffline(true);

        Assertions.assertEquals(track.getId(), 1);
        Assertions.assertEquals(track.getTitle(),"Last Day on Earth" );
        Assertions.assertEquals(track.getSinger(), "Rody");
        Assertions.assertEquals(track.getDuration(), 430);
        Assertions.assertEquals(track.getAlbum(), "DAY / DAY");
        Assertions.assertEquals(track.getPlayCount(), 4);
        Assertions.assertEquals(track.getDate(), date);
        Assertions.assertEquals(track.getDescription(), "The best music ever");
        Assertions.assertTrue(track.isOffline());
    }

    @Test
    void trackEntityNullTest(){
        TrackEntity track = new TrackEntity();

        Assertions.assertEquals(track.getId(), 0);
        Assertions.assertNull(track.getTitle());
        Assertions.assertNull(track.getSinger());
        Assertions.assertEquals(track.getDuration(), 0);
        Assertions.assertNull(track.getAlbum());
        Assertions.assertEquals(track.getPlayCount(), 0);
        Assertions.assertNull(track.getDate());
        Assertions.assertNull(track.getDescription());
        Assertions.assertFalse(track.isOffline());
    }
}
