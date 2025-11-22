package com.example.ticketbookingappandroidstudioproject.admin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.admin.model.Movie;

import java.util.List;

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

        Movie movie = this.DSMovie.get(position);

        Glide.with(context)
                .load(movie.getPoster())
                .into(imageView);

        txtTitle.setText(movie.getTitle());
        txtGenre.setText(movie.getGenre());
        txtDuration.setText(movie.getDuration_min());
        txtRated.setText(movie.getRating_code());

        return item;
    }
}
