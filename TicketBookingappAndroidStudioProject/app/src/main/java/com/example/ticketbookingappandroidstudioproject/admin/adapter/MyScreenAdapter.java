package com.example.ticketbookingappandroidstudioproject.admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.admin.activity.UpdateMovieActivity;
import com.example.ticketbookingappandroidstudioproject.admin.model.Movie;
import com.example.ticketbookingappandroidstudioproject.admin.model.Screen;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyScreenAdapter extends ArrayAdapter<Screen> {
    Activity context;
    int resource;
    List<Screen> DSScreen;

    public MyScreenAdapter(Activity context, int resource, List<Screen> DSScreen) {
        super(context, resource, DSScreen);
        this.context = context;
        this.resource = resource;
        this.DSScreen = DSScreen;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();

        View item = inflater.inflate(this.resource, null);

        TextView txtName = item.findViewById(R.id.txtScreenName);
        TextView txtFormat = item.findViewById(R.id.txtScreenFormat);
        TextView txtCode = item.findViewById(R.id.txtScreenCode);
        TextView txtIsActive = item.findViewById(R.id.txtScreenStatus);

        ImageView btnDelete = item.findViewById(R.id.btnXoa);
        ImageView btnUpdate = item.findViewById(R.id.btnSua);

        Screen screen = this.DSScreen.get(position);

        txtName.setText(screen.getName());
        txtFormat.setText(screen.getFormat() + " : " + screen.getRow_count() + " x " + screen.getCol_count());
        txtCode.setText(screen.getCode());
        if (screen.isIs_active()) {
            txtIsActive.setText("Đang hoạt động");
            txtIsActive.setTextColor(Color.GREEN);
        } else {
            txtIsActive.setText("Ngưng hoạt động");
            txtIsActive.setTextColor(Color.RED);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteScreen(screen);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateMovieActivity.class);
                intent.putExtra("screen", screen);
                intent.putExtra("id", screen.getId());
                context.startActivity(intent);
            }
        });

        return item;
    }

    private void deleteScreen(Screen screen) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.deleteScreen(bearerToken, screen.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Screen deleted successfully.", Toast.LENGTH_SHORT).show();
                        DSScreen.remove(screen);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Failed to screen movies: " + response.message(), Toast.LENGTH_SHORT).show();
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
