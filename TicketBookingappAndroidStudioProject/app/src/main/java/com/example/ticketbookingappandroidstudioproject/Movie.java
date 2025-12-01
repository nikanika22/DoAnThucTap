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

    // Cho local drawable (tạm thời dùng khi chưa có API)
    private int posterDrawableId;

    public Movie() {
        this.isActive = true;
    }

    // Constructor cho local data (dùng drawable resource)
    public Movie(String title, int posterDrawableId, String genre, String duration, String ratingCode) {
        this.id = 0;
        this.title = title;
        this.posterDrawableId = posterDrawableId;
        this.genre = genre;
        this.ratingCode = ratingCode;
        this.isActive = true;
        // Convert "120 phút" -> 120
        this.durationMin = parseDuration(duration);
    }

    // Constructor đầy đủ cho API data
    public Movie(int id, String title, int durationMin, String genre, String poster, String ratingCode, boolean isActive) {
        this.id = id;
        this.title = title;
        this.durationMin = durationMin;
        this.genre = genre;
        this.poster = poster;
        this.ratingCode = ratingCode;
        this.isActive = isActive;
    }

    // Helper method để convert string sang int
    private int parseDuration(String duration) {
        try {
            return Integer.parseInt(duration.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    // Getters and Setters
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

    // Method tương thích với code cũ
    public String getDuration() {
        return durationMin + " phút";
    }

    public void setDuration(String duration) {
        this.durationMin = parseDuration(duration);
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

    // Renamed từ getRated() để khớp với database
    public String getRated() {
        return ratingCode;
    }

    public void setRated(String ratingCode) {
        this.ratingCode = ratingCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Getter cho drawable ID (local only)
    public int getPosterId() {
        return posterDrawableId;
    }

    public void setPosterId(int posterDrawableId) {
        this.posterDrawableId = posterDrawableId;
    }
}
