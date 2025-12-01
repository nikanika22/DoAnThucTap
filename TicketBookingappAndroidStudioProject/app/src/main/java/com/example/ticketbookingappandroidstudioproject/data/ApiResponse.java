package com.example.ticketbookingappandroidstudioproject.data;


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
