package com.example.ticketbookingappandroidstudioproject.data;
import com.example.ticketbookingappandroidstudioproject.model.Order;
import com.google.gson.annotations.SerializedName;
public class OrderResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Order data;
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Order getData() { return data; }
}