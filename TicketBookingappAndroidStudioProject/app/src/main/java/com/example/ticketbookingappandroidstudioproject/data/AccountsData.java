package com.example.ticketbookingappandroidstudioproject.data;

import com.example.ticketbookingappandroidstudioproject.model.Account;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class AccountsData {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Collection<? extends Account> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Collection<? extends Account> getData() {
        return data;
    }
}
