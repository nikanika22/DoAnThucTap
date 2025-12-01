package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;

public class Seat implements Serializable {
    private int id;
    private int screenId;           // screen_id - khớp với database
    private String rowLabel;        // row_label (A, B, C...) - khớp với database
    private int seatNumber;         // seat_number (1, 2, 3...) - khớp với database
    private String seatType;        // STANDARD, VIP, COUPLE, ACCESSIBLE - khớp với database
    private boolean isAisle;        // is_aisle - khớp với database
    private boolean isBlocked;      // is_blocked - khớp với database

    // Fields cho trạng thái booking (không lưu trong DB seat table)
    private String status;          // available, selected, sold (dùng cho UI)
    private double price;           // Giá vé (tính từ base_price + seat_type)

    public Seat() {
        this.status = "available";
        this.seatType = "STANDARD";
        this.isAisle = false;
        this.isBlocked = false;
    }

    // Constructor tương thích với code cũ
    public Seat(int id, String rowLabel, int seatNumber, String seatType, String status, double price) {
        this.id = id;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.status = status;
        this.price = price;
        this.isAisle = false;
        this.isBlocked = false;
    }

    // Constructor đầy đủ từ database
    public Seat(int id, int screenId, String rowLabel, int seatNumber, String seatType, boolean isAisle, boolean isBlocked) {
        this.id = id;
        this.screenId = screenId;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.isAisle = isAisle;
        this.isBlocked = isBlocked;
        this.status = isBlocked ? "blocked" : "available";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public boolean isAisle() {
        return isAisle;
    }

    public void setAisle(boolean aisle) {
        isAisle = aisle;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
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

    // Methods tương thích với code cũ
    public String getRow() {
        return rowLabel;
    }

    public void setRow(String row) {
        this.rowLabel = row;
    }

    public int getNumber() {
        return seatNumber;
    }

    public void setNumber(int number) {
        this.seatNumber = number;
    }

    public String getType() {
        return seatType;
    }

    public void setType(String type) {
        this.seatType = type;
    }

    public int getRoomId() {
        return screenId;
    }

    public void setRoomId(int roomId) {
        this.screenId = roomId;
    }

    public String getSeatLabel() {
        return rowLabel + seatNumber;
    }
}

