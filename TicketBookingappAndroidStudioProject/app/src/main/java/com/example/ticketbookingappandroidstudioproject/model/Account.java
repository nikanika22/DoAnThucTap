package com.example.ticketbookingappandroidstudioproject.model;

public class Account {
    private int id;
    private String email;
    private String phone;
    private String full_name;
    private String role;
    private boolean is_active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Account(int id, String email, String phone, String full_name, String role, boolean is_active) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.full_name = full_name;
        this.role = role;
        this.is_active = is_active;
    }
}
