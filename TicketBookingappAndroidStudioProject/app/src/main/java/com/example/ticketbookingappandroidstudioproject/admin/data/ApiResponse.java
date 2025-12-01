package com.example.ticketbookingappandroidstudioproject.admin.data;

import com.example.ticketbookingappandroidstudioproject.admin.model.Movie;
import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("success")
    boolean success;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    T data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
