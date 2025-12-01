package com.example.ticketbookingappandroidstudioproject.helper;

import android.content.Context;
import android.content.Intent;

import com.example.ticketbookingappandroidstudioproject.SeatSelectionActivity;

public class NavigationHelper {

    /**
     * Mở màn hình chọn ghế
     * @param context Context hiện tại
     * @param movieId ID của phim
     * @param movieTitle Tên phim
     * @param showTime Suất chiếu
     * @param roomId ID phòng chiếu
     * @param roomName Tên phòng chiếu
     */
    public static void openSeatSelection(Context context, int movieId, String movieTitle,
                                         String showTime, int roomId, String roomName) {
        Intent intent = new Intent(context, SeatSelectionActivity.class);
        intent.putExtra("MOVIE_ID", movieId);
        intent.putExtra("MOVIE_TITLE", movieTitle);
        intent.putExtra("SHOW_TIME", showTime);
        intent.putExtra("ROOM_ID", roomId);
        intent.putExtra("ROOM_NAME", roomName);
        context.startActivity(intent);
    }
}

