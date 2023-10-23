package nl.han.oose.dea.Entity;

import java.util.ArrayList;
import java.util.List;

public class TracksEntity {
    private List<TrackEntity> tracks = new ArrayList<>();

    public List<TrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackEntity> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(TrackEntity trackEntity) {
        tracks.add(trackEntity);
    }
}
