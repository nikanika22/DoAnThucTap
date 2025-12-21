package com.example.ticketbookingappandroidstudioproject.model;

public class ShowTimeItems {
    // Type constants
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_SHOWTIME = 1;

    private int type;              // TYPE_HEADER hoặc TYPE_SHOWTIME
    private String headerText;     // Nếu là header: "chủ nhật, 21/12/2025"
    private ShowTime showtime;     // Nếu là showtime: object ShowTime

    // Constructor cho HEADER
    public static ShowTimeItems createHeader(String headerText) {
        ShowTimeItems item = new ShowTimeItems();
        item.type = TYPE_HEADER;
        item.headerText = headerText;
        return item;
    }

    // Constructor cho SHOWTIME
    public static ShowTimeItems createShowtime(ShowTime showtime) {
        ShowTimeItems item = new ShowTimeItems();
        item.type = TYPE_SHOWTIME;
        item.showtime = showtime;
        return item;
    }

    // Getters
    public int getType() { return type; }
    public String getHeaderText() { return headerText; }
    public ShowTime getShowtime() { return showtime; }
}
