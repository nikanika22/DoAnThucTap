package com.example.ticketbookingappandroidstudioproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    private static final String API_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
    private static final String API_FORMAT_SHORT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String API_FORMAT_NO_MILLIS = "yyyy-MM-dd HH:mm:ss";

    public static String formatDateTime(Date date) {
        if (date == null) return "";
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
        return outputFormat.format(date);
    }

    public static String formatTimeOnly(Date date) {
        if (date == null) return "";
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return outputFormat.format(date);
    }

    public static String formatDateOnly(Date date) {
        if (date == null) return "";
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return outputFormat.format(date);
    }

    public static String formatShowtimeInfo(Date startAt, Date endAt, String screenName) {
        String startTime = formatTimeOnly(startAt);
        String endTime = formatTimeOnly(endAt);
        String date = formatDateOnly(startAt);

        if (screenName == null || screenName.isEmpty()) {
            return startTime + " - " + endTime + " " + date;
        }
        return startTime + " - " + endTime + " " + date + " - " + screenName;
    }
}
