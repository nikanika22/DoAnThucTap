package com.example.ticketbookingappandroidstudioproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyTicket {
    @SerializedName("id")
    private int id;

    @SerializedName("status")
    private String status;

    @SerializedName("total_amount")
    private int totalAmount;

    @SerializedName("showtime")
    private ShowtimeInfo showtime;

    @SerializedName("order_lines")
    private List<TicketLine> orderLines;

    public void setOrderLines(List<TicketLine> orderLines) {
        this.orderLines = orderLines;
    }

    // Getters
    public int getId() { return id; }
    public String getStatus() { return status; }

    public void setShowtime(ShowtimeInfo showtime) {
        this.showtime = showtime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalAmount() { return totalAmount; }
    public ShowtimeInfo getShowtime() { return showtime; }
    public List<TicketLine> getOrderLines() { return orderLines; }

    // ===== Classes bên trong =====

    public static class ShowtimeInfo {
        @SerializedName("start_at")
        private String startAt;

        @SerializedName("movie")
        private MovieInfo movie;

        @SerializedName("screen")
        private ScreenInfo screen;

        public String getStartAt() { return startAt; }
        public MovieInfo getMovie() { return movie; }
        public ScreenInfo getScreen() { return screen; }
    }

    public static class MovieInfo {
        @SerializedName("title")
        private String title;

        @SerializedName("poster")
        private String poster;

        public String getTitle() { return title; }
        public String getPoster() { return poster; }
    }

    public static class ScreenInfo {
        @SerializedName("name")
        private String name;

        public String getName() { return name; }
    }

    public static class TicketLine {
        @SerializedName("item_type")
        private String itemType;

        @SerializedName("seat")
        private SeatInfo seat;

        public String getItemType() { return itemType; }
        public SeatInfo getSeat() { return seat; }
    }

    public static class SeatInfo {
        @SerializedName("row_label")
        private String rowLabel;

        @SerializedName("seat_number")
        private int seatNumber;

        public String getRowLabel() { return rowLabel; }
        public int getSeatNumber() { return seatNumber; }
    }

    // ===== Helper Method =====

    // Lấy chuỗi ghế: "C5, C6, C7"
    public String getSeatsString() {
        if (orderLines == null || orderLines.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < orderLines.size(); i++) {
            TicketLine line = orderLines.get(i);
            if (line.getItemType().equals("TICKET") && line.getSeat() != null) {
                SeatInfo seat = line.getSeat();
                result.append(seat.getRowLabel()).append(seat.getSeatNumber());

                // Thêm dấu phẩy nếu chưa phải ghế cuối
                if (i < orderLines.size() - 1) {
                    result.append(", ");
                }
            }
        }
        return result.toString();
    }
}
