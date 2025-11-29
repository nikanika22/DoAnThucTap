package com.example.ticketbookingappandroidstudioproject.admin.model;

import java.io.Serializable;

public class Screen implements Serializable {
    private int id;

    private String code;
    private String name;
    private String format;
    private int row_count;
    private int col_count;
    private boolean is_active;

    public Screen(int id, String code, String name, String format, int row_count, int col_count, boolean is_active) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.format = format;
        this.row_count = row_count;
        this.col_count = col_count;
        this.is_active = is_active;
    }

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

    public int getRow_count() {
        return row_count;
    }

    public void setRow_count(int row_count) {
        this.row_count = row_count;
    }

    public int getCol_count() {
        return col_count;
    }

    public void setCol_count(int col_count) {
        this.col_count = col_count;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
