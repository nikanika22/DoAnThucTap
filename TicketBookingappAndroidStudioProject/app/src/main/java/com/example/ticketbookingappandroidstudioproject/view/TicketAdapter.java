package com.example.ticketbookingappandroidstudioproject.view;

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
import com.example.ticketbookingappandroidstudioproject.model.MyTicket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends ArrayAdapter<MyTicket> {
    Activity context;
    int resource;
    List<MyTicket> ticketList;

    public TicketAdapter(Activity context, int resource, List<MyTicket> ticketList) {
        super(context, resource, ticketList);
        this.context = context;
        this.resource = resource;
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        // Lấy các view
        ImageView imgPoster = item.findViewById(R.id.imgPoster);
        TextView tvMovieTitle = item.findViewById(R.id.tvMovieTitle);
        TextView tvRoomName = item.findViewById(R.id.tvRoomName);
        TextView tvTime = item.findViewById(R.id.tvTime);
        TextView tvSeatNumber = item.findViewById(R.id.tvSeatNumber);

        // Lấy ticket
        MyTicket ticket = this.ticketList.get(position);

        // Set data
        tvMovieTitle.setText(ticket.getShowtime().getMovie().getTitle());
        tvRoomName.setText(ticket.getShowtime().getScreen().getName());
        tvTime.setText(formatDateTime(ticket.getShowtime().getStartAt()));
        tvSeatNumber.setText(ticket.getSeatsString());

        // Load poster
        Glide.with(context)
                .load(ticket.getShowtime().getMovie().getPoster())
                .placeholder(R.drawable.atlas)
                .error(R.drawable.atlas)
                .into(imgPoster);

        return item;
    }

    // Helper: Format ngày giờ từ "2026-01-05 19:00:00" thành "19:00 - 05/01/2026"
    private String formatDateTime(String dateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (Exception e) {
            return dateTime;
        }
    }
}
