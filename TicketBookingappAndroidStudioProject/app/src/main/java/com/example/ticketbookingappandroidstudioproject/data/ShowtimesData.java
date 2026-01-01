package com.example.ticketbookingappandroidstudioproject.data;

import com.example.ticketbookingappandroidstudioproject.model.ShowTime;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class ShowtimesData {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Collection<? extends ShowTime> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Collection<? extends ShowTime> getData() {
        return data;
    }
}
