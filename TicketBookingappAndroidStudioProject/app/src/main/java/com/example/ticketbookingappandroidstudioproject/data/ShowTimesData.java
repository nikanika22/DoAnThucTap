package com.example.ticketbookingappandroidstudioproject.data;

import com.example.ticketbookingappandroidstudioproject.model.ShowTime;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowTimesData {
    @SerializedName("success")
    private boolean success;

    @SerializedName("showtimes")
    private List<ShowTime> showtimes;

    // Getters & Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public List<ShowTime> getShowtimes() { return showtimes; }
    public void setShowtimes(List<ShowTime> showtimes) { this.showtimes = showtimes; }

    public ShowTimesData() {
    }

    public ShowTimesData(boolean success, List<ShowTime> showtimes) {
        this.success = success;
        this.showtimes = showtimes;
    }
}
