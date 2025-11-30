package com.example.ticketbookingappandroidstudioproject.admin.model;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private String email;
    private String phone;
    private String password_hash;
    private String fullname;
    private String role;
    private boolean is_active;

    public Account(int id, String email, String phone, String password_hash, String fullname, String role, boolean is_active) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password_hash = password_hash;
        this.fullname = fullname;
        this.role = role;
        this.is_active = is_active;
    }

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

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
}
