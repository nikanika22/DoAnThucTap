package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;

/**
 * Model class cho bảng product (sản phẩm - đồ ăn, nước uống)
 * Khớp với database schema
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private int price;
    private boolean isActive;

    public Product() {
        this.isActive = true;
    }

    public Product(int id, String name, int price, boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

