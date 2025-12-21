package com.example.ticketbookingappandroidstudioproject.view;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.model.Movie;

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
        TextView txtRating = item.findViewById(R.id.txtRated);

        Button btnDatVe = item.findViewById(R.id.btnDatVe);

        Movie movie = this.DSMovie.get(position);

        Glide.with(context).load(movie.getPoster()).into(imageView);

        txtTitle.setText(movie.getTitle());
        txtGenre.setText(movie.getGenre());
        txtDuration.setText(movie.getDuration_min());
        txtRating.setText(movie.getRating_code());

        // Xử lý sự kiện click nút Đặt vé
        btnDatVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình xem suất chiếu
                Intent intent = new Intent(context, ShowTimesActivity.class);
                intent.putExtra("MOVIE_ID", movie.getId());
                intent.putExtra("MOVIE_TITLE", movie.getTitle());
                context.startActivity(intent);
            }
        });

        return item;
    }
}
