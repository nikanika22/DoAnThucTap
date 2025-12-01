package com.example.ticketbookingappandroidstudioproject.admin.data;

import com.example.ticketbookingappandroidstudioproject.admin.model.Account;
import com.example.ticketbookingappandroidstudioproject.admin.model.Screen;
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
