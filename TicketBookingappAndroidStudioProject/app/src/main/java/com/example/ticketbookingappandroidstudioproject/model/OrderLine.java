package com.example.ticketbookingappandroidstudioproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderLine implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("order_id")
    private Integer orderId;
    @SerializedName("item_id")
    private Integer itemId;
    @SerializedName("item_type")
    private String itemType; // TICKET, PRODUCT
    @SerializedName("seat_id")
    private Integer seatId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("unit_price")
    private double unitPrice;
    @SerializedName("line_total")
    private Integer lineTotal;

    private Seat seat;
    private Product product;

    public OrderLine() {
    }

    public OrderLine(int id, int orderId, int itemId, String itemType, int quantity, double unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemType = itemType;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(int lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
