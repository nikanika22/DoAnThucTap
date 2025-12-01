package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;

/**
 * Model class cho bảng orders (đơn hàng)
 * Khớp với database schema
 */
public class Order implements Serializable {
    private int id;
    private String channel;         // WEB, POS
    private int accountId;          // ID khách hàng (nullable)
    private int cashierId;          // ID nhân viên thu ngân (nullable)
    private int showtimeId;         // ID suất chiếu
    private String status;          // INIT, PAID, CANCELLED
    private String paymentMethod;   // CASH, CARD, EWALLET (nullable)
    private int totalAmount;        // Tổng tiền

    public Order() {
        this.channel = "WEB";
        this.status = "INIT";
        this.totalAmount = 0;
    }

    public Order(int id, String channel, int accountId, int showtimeId, String status, String paymentMethod, int totalAmount) {
        this.id = id;
        this.channel = channel;
        this.accountId = accountId;
        this.showtimeId = showtimeId;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}

