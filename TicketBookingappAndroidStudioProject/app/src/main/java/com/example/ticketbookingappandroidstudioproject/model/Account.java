package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;

/**
 * Model class cho bảng account (tài khoản)
 * Khớp với database schema
 */
public class Account implements Serializable {
    private int id;
    private String email;
    private String phone;
    private String passwordHash;
    private String fullName;
    private String role;            // CUSTOMER, STAFF, ADMIN
    private boolean isActive;

    public Account() {
        this.role = "CUSTOMER";
        this.isActive = true;
    }

    public Account(int id, String email, String phone, String fullName, String role, boolean isActive) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
        this.isActive = isActive;
    }

    // Getters and Setters
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

