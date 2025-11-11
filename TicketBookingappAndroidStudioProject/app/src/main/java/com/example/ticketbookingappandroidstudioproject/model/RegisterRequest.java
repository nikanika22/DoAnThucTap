package com.example.ticketbookingappandroidstudioproject.model;

public class RegisterRequest {
    private String email;
    private String phone;
    private String full_name;
    private String password_hash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public RegisterRequest(String email, String phone, String full_name, String password_hash) {
        this.email = email;
        this.phone = phone;
        this.full_name = full_name;
        this.password_hash = password_hash;
    }
}
