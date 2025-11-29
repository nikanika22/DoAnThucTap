package com.example.ticketbookingappandroidstudioproject.admin.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String duration_min;
    private String genre;
    private String poster;
    private String rating_code;

    private boolean is_active;

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

    public String getDuration_min() {
        return duration_min;
    }

    public void setDuration_min(String duration_min) {
        this.duration_min = duration_min;
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

    public String getRating_code() {
        return rating_code;
    }

    public void setRating_code(String rating_code) {
        this.rating_code = rating_code;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Movie(String title, String duration_min, String genre, String poster, String rating_code, Boolean is_active) {
        this.title = title;
        this.duration_min = duration_min;
        this.genre = genre;
        this.poster = poster;
        this.rating_code = rating_code;
        this.is_active = is_active;
    }
}
