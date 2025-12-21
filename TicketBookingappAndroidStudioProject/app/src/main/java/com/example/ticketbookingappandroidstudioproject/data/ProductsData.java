package com.example.ticketbookingappandroidstudioproject.data;

import com.example.ticketbookingappandroidstudioproject.model.Movie;
import com.example.ticketbookingappandroidstudioproject.model.Product;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class ProductsData {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Collection<? extends Product> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Collection<? extends Product> getData() {
        return data;
    }
}
