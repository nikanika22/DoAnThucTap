package com.example.ticketbookingappandroidstudioproject;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private int posterId;
    private String genre;       // Thể loại
    private String duration;    // Thời lượng (ví dụ: "120 phút")
    private String rated;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public Movie(String title, int posterId, String genre, String duration, String rated) {
        this.title = title;
        this.posterId = posterId;
        this.genre = genre;
        this.duration = duration;
        this.rated = rated;
    }
}
