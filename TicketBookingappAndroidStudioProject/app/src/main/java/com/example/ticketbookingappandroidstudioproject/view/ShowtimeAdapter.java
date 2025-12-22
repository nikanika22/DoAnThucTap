package com.example.ticketbookingappandroidstudioproject.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.ScreensData;
import com.example.ticketbookingappandroidstudioproject.model.Screen;
import com.example.ticketbookingappandroidstudioproject.model.ShowTime;
import com.example.ticketbookingappandroidstudioproject.model.ShowTimeItems;
import com.example.ticketbookingappandroidstudioproject.view.SeatSelectionActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Adapter xử lý 2 loại view: Header (ngày) và Showtime (card)
 */
public class ShowtimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShowTimeItems> items = new ArrayList<>();
    private Activity context;
    private String movieTiltle;

    public ShowtimeAdapter(Activity context, String movieTiltle)
    {
        this.context = context;
        this.movieTiltle=movieTiltle;
    }

    public void updateData(List<ShowTimeItems> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == ShowTimeItems.TYPE_HEADER) {
            // Inflate layout cho HEADER
            View view = inflater.inflate(R.layout.item_showtime_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            // Inflate layout cho SHOWTIME
            View view = inflater.inflate(R.layout.item_showtime_card, parent, false);
            return new ShowtimeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShowTimeItems item = items.get(position);

        if (holder instanceof HeaderViewHolder) {
            // Bind HEADER
            ((HeaderViewHolder) holder).bind(item.getHeaderText());
        } else if (holder instanceof ShowtimeViewHolder) {
            // Bind SHOWTIME
            ((ShowtimeViewHolder) holder).bind(item.getShowtime());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ===== ViewHolder cho HEADER =====
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader;

        HeaderViewHolder(View view) {
            super(view);
            tvHeader = view.findViewById(R.id.tvHeaderDate);
        }

        void bind(String headerText) {
            tvHeader.setText(headerText);
        }
    }

    // ===== ViewHolder cho SHOWTIME =====
    class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvScreen;
        TextView tvPrice;

        ShowtimeViewHolder(View view) {
            super(view);
            tvTime = view.findViewById(R.id.tvTime);
            tvScreen = view.findViewById(R.id.tvScreen);
            tvPrice = view.findViewById(R.id.tvPrice);
        }

        void bind(ShowTime showtime) {
            // Giờ: "13:45"
            String time = showtime.getStartAt().substring(11, 16);
            tvTime.setText(time);

            // Screen: "Screen 4"
            tvScreen.setText("Screen " + showtime.getScreenId());

            // Giá: "100,000đ"
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            tvPrice.setText(formatter.format(showtime.getBasePrice()) + "đ");

            // Click → SeatSelectionActivity
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, SeatSelectionActivity.class);
                intent.putExtra("SHOWTIME_ID", showtime.getId());
                intent.putExtra("SCREEN_ID", showtime.getScreenId());
                intent.putExtra("BASE_PRICE", showtime.getBasePrice());
                intent.putExtra("MOVIE_TITLE", movieTiltle);
                intent.putExtra("Screen",tvScreen.getText().toString());
                intent.putExtra("Time",tvTime.getText().toString());
                intent.putExtra("Price",tvPrice.getText().toString());
                context.startActivity(intent);
            });
        }
    }
}