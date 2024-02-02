package com.bej.muzixservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public class Track {
@Id
private String trackId;
private String trackName;
private String trackComments;
private int trackRating;
private Artist artist;

    public Track(String trackId, String trackName, String trackComments, int trackRating, Artist artist) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackComments = trackComments;
        this.trackRating = trackRating;
        this.artist = artist;
    }

    public Track() {
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId='" + trackId + '\'' +
                ", trackName='" + trackName + '\'' +
                ", trackComments='" + trackComments + '\'' +
                ", rating=" + trackRating +
                ", artist=" + artist +
                '}';
    }

    public int getRating() {
        return trackRating;
    }

    public void setRating(int rating) {
        this.trackRating = rating;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackComments() {
        return trackComments;
    }

    public void setTrackComments(String trackComments) {
        this.trackComments = trackComments;
    }


    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }


}
