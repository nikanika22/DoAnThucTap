package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;
import java.util.List;

public class Booking implements Serializable {
    private int id;
    private int userId;
    private int showTimeId;
    private List<Seat> seats;
    private double totalPrice;
    private String bookingDate;
    private String status; // pending, confirmed, cancelled

    public Booking() {
    }

    public Booking(int userId, int showTimeId, List<Seat> seats, double totalPrice) {
        this.userId = userId;
        this.showTimeId = showTimeId;
        this.seats = seats;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(int showTimeId) {
        this.showTimeId = showTimeId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

