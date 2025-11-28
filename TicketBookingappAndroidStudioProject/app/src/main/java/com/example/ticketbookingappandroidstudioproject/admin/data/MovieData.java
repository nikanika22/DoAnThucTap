package com.example.ticketbookingappandroidstudioproject.admin.data;

import com.example.ticketbookingappandroidstudioproject.admin.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class MovieData {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Movie data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Movie getData() {
        return data;
    }
}
