package com.example.ticketbookingappandroidstudioproject.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.model.ShowTime;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyShowtimeAdapterAdmin extends ArrayAdapter<ShowTime> {
    Activity context;
    int resource;
    List<ShowTime> showtimeList;

    public MyShowtimeAdapterAdmin(Activity context, int resource, List<ShowTime> showtimeList) {
        super(context, resource, showtimeList);
        this.context = context;
        this.resource = resource;
        this.showtimeList = showtimeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();

        View item = inflater.inflate(this.resource, null);

        ShowTime showtime = showtimeList.get(position);

        TextView txtMovieName = item.findViewById(R.id.txtMovieName);
        TextView txtShowtime = item.findViewById(R.id.txtShowtime);
        TextView txtDate = item.findViewById(R.id.txtDate);
        TextView txtScreen = item.findViewById(R.id.txtScreen);
        TextView txtPrice = item.findViewById(R.id.txtPrice);
        TextView txtStatus = item.findViewById(R.id.txtStatus);
        ImageView btnEdit = item.findViewById(R.id.btnEditShowtime);
        ImageView btnDelete = item.findViewById(R.id.btnDeleteShowtime);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        if (showtime.getMovie() != null && showtime.getMovie().getTitle() != null) {
            txtMovieName.setText(showtime.getMovie().getTitle());
        } else {
            txtMovieName.setText("N/A");
        }

        String startTime = showtime.getStartAt() != null ? timeFormat.format(showtime.getStartAt()) : "--:--";
        String endTime = showtime.getEndAt() != null ? timeFormat.format(showtime.getEndAt()) : "--:--";
        txtShowtime.setText(startTime + " - " + endTime);

        if (showtime.getStartAt() != null) {
            txtDate.setText(dateFormat.format(showtime.getStartAt()));
        } else {
            txtDate.setText("--/--/----");
        }

        if (showtime.getScreen() != null) {
            String screenInfo = showtime.getScreen().getName();
            if (showtime.getScreen().getFormat() != null) {
                screenInfo += " · " + showtime.getScreen().getFormat();
            }
            txtScreen.setText(screenInfo);
        } else {
            txtScreen.setText("N/A");
        }

        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        txtPrice.setText(currencyFormat.format(showtime.getBasePrice()) + "đ");

        String status = showtime.getStatus();
        txtStatus.setText(status);
        switch (status) {
            case "OPEN":
                txtStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
                break;
            case "SCHEDULED":
                txtStatus.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
                break;
            case "CLOSED":
                txtStatus.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
                break;
            case "CANCELLED":
                txtStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                break;
            default:
                txtStatus.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateShowtimeActivity.class);
            intent.putExtra("showtime", showtime);
            context.startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            deleteShowtime(showtime);
        });

        return item;
    }

    private void deleteShowtime(ShowTime showtime) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.deleteShowtime(bearerToken, showtime.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Showtime deleted successfully.", Toast.LENGTH_SHORT).show();
                        showtimeList.remove(showtime);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Failed to delete showtime: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}
