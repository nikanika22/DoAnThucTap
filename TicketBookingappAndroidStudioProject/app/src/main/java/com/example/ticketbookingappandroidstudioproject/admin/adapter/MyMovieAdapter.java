package com.example.ticketbookingappandroidstudioproject.admin.adapter;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.admin.activity.UpdateMovieActivity;
import com.example.ticketbookingappandroidstudioproject.admin.data.MovieData;
import com.example.ticketbookingappandroidstudioproject.admin.data.MoviesData;
import com.example.ticketbookingappandroidstudioproject.admin.model.Movie;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMovieAdapter extends ArrayAdapter<Movie> {
    Activity context;
    int resource;
    List<Movie> DSMovie;

    public MyMovieAdapter(Activity context, int resource, List<Movie> DSMovie) {
        super(context, resource, DSMovie);
        this.context = context;
        this.resource = resource;
        this.DSMovie = DSMovie;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();

        View item = inflater.inflate(this.resource, null);

        ImageView imageView = item.findViewById(R.id.imgPoster);

        TextView txtTitle = item.findViewById(R.id.txtTitle);
        TextView txtGenre = item.findViewById(R.id.txtGenre);
        TextView txtDuration = item.findViewById(R.id.txtDuration);
        TextView txtRated = item.findViewById(R.id.txtRated);

        ImageView btnDelete = item.findViewById(R.id.btnXoa);
        ImageView btnUpdate = item.findViewById(R.id.btnSua);


        Movie movie = this.DSMovie.get(position);

        Glide.with(context)
                .load(movie.getPoster())
                .into(imageView);

        txtTitle.setText(movie.getTitle());
        txtGenre.setText(movie.getGenre());
        txtDuration.setText(movie.getDuration_min());
        txtRated.setText(movie.getRating_code());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMovie(movie);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return item;
    }

    private void deleteMovie(Movie movie) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.deleteMovie(bearerToken, movie.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Movie deleted successfully.", Toast.LENGTH_SHORT).show();
                        DSMovie.remove(movie);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Failed to delete movies: " + response.message(), Toast.LENGTH_SHORT).show();
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
