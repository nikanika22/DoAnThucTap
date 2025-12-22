package com.example.ticketbookingappandroidstudioproject.model;

public class LoginRequest {
    private String email;
    private String password_hash;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password_hash = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword_hash() {
        return password_hash;
    }
}