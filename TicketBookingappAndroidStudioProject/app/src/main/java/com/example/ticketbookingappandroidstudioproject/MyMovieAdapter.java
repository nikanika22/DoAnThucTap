package com.example.ticketbookingappandroidstudioproject;

import android.app.Activity;
import android.app.Notification;
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

import org.w3c.dom.Text;

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
        TextView txtRated=item.findViewById(R.id.txtRated);
        Button btnDatVe=item.findViewById(R.id.btnDatVe);

        Movie movie = this.DSMovie.get(position);
        imageView.setImageResource(movie.getPosterId());
        txtTitle.setText(movie.getTitle());
        txtGenre.setText(movie.getGenre());
        txtDuration.setText(movie.getDuration());
        txtRated.setText(movie.getRated());

        // Xử lý sự kiện click nút Đặt vé
        btnDatVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình chọn ghế
                Intent intent = new Intent(context, SeatSelectionActivity.class);
                intent.putExtra("MOVIE_ID", movie.getId());
                intent.putExtra("MOVIE_TITLE", movie.getTitle());
                intent.putExtra("SHOW_TIME", "19:30 - 30/11/2025"); // Có thể lấy từ movie nếu có
                intent.putExtra("ROOM_ID", 1); // Có thể dynamic
                intent.putExtra("ROOM_NAME", "A1"); // Có thể dynamic
                context.startActivity(intent);
            }
        });

        return item;
    }
}
