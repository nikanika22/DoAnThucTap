package com.example.ticketbookingappandroidstudioproject.model;

import java.io.Serializable;
import java.util.List;

public class Cinema implements Serializable {
    private int id;
    private String name;
    private String address;
    private List<ShowTime> showTimes;

    public Cinema() {
    }

    public Cinema(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }
}


