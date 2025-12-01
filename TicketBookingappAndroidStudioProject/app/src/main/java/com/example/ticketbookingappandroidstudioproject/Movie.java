package com.example.ticketbookingappandroidstudioproject;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private int durationMin;    // Thời lượng phim (phút) - khớp với database
    private String genre;       // Thể loại
    private String poster;      // URL poster từ API - khớp với database
    private String ratingCode;  // Mã phân loại (T13, T16, T18) - khớp với database
    private boolean isActive;   // Trạng thái active - khớp với database

    public Movie(String title, int durationMin, String genre, String poster, String ratingCode, boolean isActive) {
        this.title = title;
        this.durationMin = durationMin;
        this.genre = genre;
        this.poster = poster;
        this.ratingCode = ratingCode;
        this.isActive = isActive;
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

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRatingCode() {
        return ratingCode;
    }

    public void setRatingCode(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
