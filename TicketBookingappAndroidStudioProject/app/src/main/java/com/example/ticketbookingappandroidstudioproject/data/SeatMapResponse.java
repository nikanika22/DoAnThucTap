package com.example.ticketbookingappandroidstudioproject.data;

import com.example.ticketbookingappandroidstudioproject.model.Seat;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeatMapResponse {
    @SerializedName("success")
    private boolean success;
    private List<Seat> data;

    public boolean isSuccess() { return success; }
    public List<Seat> getData() { return data; }
}
