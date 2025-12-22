package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;

public class Seat implements Serializable {
    private int id;
    private String row;
    private int number;
    private String type;
    private String status;
    private double price;
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

