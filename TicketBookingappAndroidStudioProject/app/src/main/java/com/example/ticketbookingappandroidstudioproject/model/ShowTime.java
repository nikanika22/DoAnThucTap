package com.example.ticketbookingappandroidstudioproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class ShowTime implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("movie_id")
    private int movieId;

    @SerializedName("screen_id")
    private int screenId;

    @SerializedName("start_at")
    private String startAt;  // "2024-12-16T09:00:00.000000Z"

    @SerializedName("end_at")
    private String endAt;

    @SerializedName("base_price")
    private int basePrice;

    @SerializedName("status")
    private String status;

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShowTime() {
    }

    public ShowTime(String endAt, int id, int movieId, int screenId, String startAt, int basePrice, String status) {
        this.endAt = endAt;
        this.id = id;
        this.movieId = movieId;
        this.screenId = screenId;
        this.startAt = startAt;
        this.basePrice = basePrice;
        this.status = status;
    }
}


