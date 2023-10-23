package nl.han.oose.dea.Entity;

import java.util.Date;

public class TrackEntity {
    private int id;
    private String title;
    private String singer;
    private int duration;
    private String album;
    private int playCount;
    private Date date;
    private String description;
    private boolean isOffline;

    public TrackEntity() {
    }

    public TrackEntity(int id, String title, String singer, int duration, String album, int playCount, Date date, String description, boolean isOffline) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.duration = duration;
        this.album = album;
        this.playCount = playCount;
        this.date = date;
        this.description = description;
        this.isOffline = isOffline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }
}
