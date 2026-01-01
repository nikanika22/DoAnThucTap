package com.example.ticketbookingappandroidstudioproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Seat implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("price")
    private double price;
    @SerializedName("row")
    private String row;
    @SerializedName("number")
    private int number;
    @SerializedName("type")
    private String type;
    @SerializedName("status")
    private String status;
    public Seat()
    {
        this.status="available";
        this.type="STANDARD";
    }


    public String getSeatLabel() {
        return row+number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Seat(double price, String status, String type, int number, String row, int id) {
        this.price = price;
        this.status = status;
        this.type = type;
        this.number = number;
        this.row = row;
        this.id = id;
    }
}

