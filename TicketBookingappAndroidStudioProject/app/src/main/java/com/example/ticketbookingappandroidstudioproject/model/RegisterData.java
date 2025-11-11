package com.example.ticketbookingappandroidstudioproject.model;

import com.google.gson.annotations.SerializedName;
public class RegisterData {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataRegister data;

    // Inner class for nested "data" object
    public class DataRegister {
        private Account account;
        private String token;

        public Account getAccount() {
            return account;
        }

        public String getToken() {
            return token;
        }
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public DataRegister getData() {
        return data;
    }
}
