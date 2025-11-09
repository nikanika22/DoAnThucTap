package com.example.ticketbookingappandroidstudioproject;

import android.app.Activity;
import android.app.Notification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        ImageView imageView=item.findViewById(R.id.imgPoster);
        TextView txtTitle=item.findViewById(R.id.txtTitle);
        TextView txtGenre=item.findViewById(R.id.txtGenre);
        TextView txtDuration=item.findViewById(R.id.txtDuration);
        Movie movie=this.DSMovie.get(position);
        imageView.setImageResource(movie.getPosterId());
        txtTitle.setText(movie.getTitle());
        txtGenre.setText(movie.getGenre());
        txtDuration.setText(movie.getDuration());
        return item;
    }
}
