package com.example.ticketbookingappandroidstudioproject.data;

import com.example.ticketbookingappandroidstudioproject.model.MyTicket;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyTicketsResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<MyTicket> data;

    public boolean isSuccess() { return success; }
    public List<MyTicket> getData() { return data; }
}