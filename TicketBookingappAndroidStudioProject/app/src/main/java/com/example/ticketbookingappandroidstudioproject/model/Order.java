package com.example.ticketbookingappandroidstudioproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("channel")
    private String channel;         // WEB, POS
    @SerializedName("account_id")
    private int accountId;          // ID khách hàng (nullable)
    @SerializedName("cashier_id")
    private int cashierId;          // ID nhân viên thu ngân (nullable)
    @SerializedName("showtime_id")
    private int showtimeId;         // ID suất chiếu
    @SerializedName("status")
    private String status;          // INIT, PAID, CANCELLED
    @SerializedName("payment_method")
    private String paymentMethod;   // CASH, CARD, EWALLET (nullable)
    @SerializedName("total_amount")
    private int totalAmount;        // Tổng tiền

    private Account account;
    private ShowTime showtime;

    private List<OrderLine> orderLines;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ShowTime getShowtime() {
        return showtime;
    }

    public void setShowtime(ShowTime showtime) {
        this.showtime = showtime;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }
    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}

