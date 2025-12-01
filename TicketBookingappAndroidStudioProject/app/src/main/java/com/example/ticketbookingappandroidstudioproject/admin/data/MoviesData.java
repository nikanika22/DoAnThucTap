package com.example.ticketbookingappandroidstudioproject.admin.data;

import com.example.ticketbookingappandroidstudioproject.admin.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class MoviesData {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Collection<? extends Movie> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Collection<? extends Movie> getData() {
        return data;
    }
}
