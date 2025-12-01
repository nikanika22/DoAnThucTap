package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;

/**
 * Model class cho bảng screen (phòng chiếu)
 * Khớp với database schema
 */
public class Screen implements Serializable {
    private int id;
    private String code;            // Mã phòng (unique)
    private String name;            // Tên phòng
    private String format;          // 2D, 3D, IMAX, ScreenX
    private int rowCount;           // Số hàng ghế
    private int colCount;           // Số cột ghế
    private boolean isActive;       // Trạng thái hoạt động

    public Screen() {
        this.format = "2D";
        this.isActive = true;
    }

    public Screen(int id, String code, String name, String format, int rowCount, int colCount, boolean isActive) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.format = format;
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

