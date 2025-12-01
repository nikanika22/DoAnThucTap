package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;
import java.util.Date;

public class ShowTime implements Serializable {
    private int id;
    private int movieId;            // movie_id - khớp với database
    private int screenId;           // screen_id - khớp với database
    private Date startAt;           // start_at (datetime) - khớp với database
    private Date endAt;             // end_at (datetime) - khớp với database
    private int basePrice;          // base_price (giá vé cơ bản) - khớp với database
    private String status;          // SCHEDULED, OPEN, CLOSED, CANCELLED - khớp với database

    // Fields bổ sung cho UI
    private String time;            // Format: "19:30" (từ startAt)
    private String date;            // Format: "30/11/2025" (từ startAt)
    private String format;          // 2D, 3D, IMAX (lấy từ screen.format)

    public ShowTime() {
        this.status = "OPEN";
    }

    // Constructor tương thích với code cũ
    public ShowTime(int id, String time, String date, String format) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.format = format;
        this.status = "OPEN";
    }

    // Constructor đầy đủ từ database
    public ShowTime(int id, int movieId, int screenId, Date startAt, Date endAt, int basePrice, String status) {
        this.id = id;
        this.movieId = movieId;
        this.screenId = screenId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.basePrice = basePrice;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    // Methods tương thích với code cũ
    public int getCinemaId() {
        return screenId; // Tạm map screen_id thành cinema_id
    }

    public void setCinemaId(int cinemaId) {
        this.screenId = cinemaId;
    }

    public int getRoomId() {
        return screenId;
    }

    public void setRoomId(int roomId) {
        this.screenId = roomId;
    }

    public boolean isAvailable() {
        return "OPEN".equals(status);
    }

    public void setAvailable(boolean available) {
        this.status = available ? "OPEN" : "CLOSED";
    }
}

