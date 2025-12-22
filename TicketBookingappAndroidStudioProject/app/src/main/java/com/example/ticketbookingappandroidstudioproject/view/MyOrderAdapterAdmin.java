package com.example.ticketbookingappandroidstudioproject.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.model.Order;
import com.example.ticketbookingappandroidstudioproject.utils.DateTimeUtils;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderAdapterAdmin extends ArrayAdapter<Order> {
    Activity context;
    int resource;
    List<Order> DSOrder;

    public MyOrderAdapterAdmin(Activity context, int resource, List<Order> DSOrder) {
        super(context, resource, DSOrder);
        this.context = context;
        this.resource = resource;
        this.DSOrder = DSOrder;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();

        View item = inflater.inflate(this.resource, null);

        TextView orderId = item.findViewById(R.id.tvOrderId);
        TextView customerName = item.findViewById(R.id.tvCustomerName);
        TextView channel = item.findViewById(R.id.tvChannel);
        TextView movieName = item.findViewById(R.id.tvMovieTitle);
        TextView showtimeInfo = item.findViewById(R.id.tvShowtimeInfo);
        TextView totalPrice = item.findViewById(R.id.tvTotalAmount);
        TextView status = item.findViewById(R.id.tvStatus);
        ImageView detail = item.findViewById(R.id.btnViewDetail);

        Order order = this.DSOrder.get(position);

        orderId.setText("#" + order.getId());
        customerName.setText(order.getAccount().getFullName());
        channel.setText(order.getChannel());
        movieName.setText(order.getShowtime().getMovie().getTitle());
        
        // Format showtime: "09:00 - 11:30 16/12/2025 - Screen 1"
        String screenName = order.getShowtime().getScreen() != null ? order.getShowtime().getScreen().getName() : "";
        String formattedShowtime = DateTimeUtils.formatShowtimeInfo(
            order.getShowtime().getStartAt(),
            order.getShowtime().getEndAt(),
            screenName
        );
        showtimeInfo.setText(formattedShowtime);
        
        String totalAmountFormatted = String.valueOf(order.getTotalAmount());
        totalPrice.setText(totalAmountFormatted + " VND");
        status.setText(order.getStatus());

        if (order.getStatus().equals("INIT")) {
            status.setTextColor(Color.parseColor("#FFA500")); // Orange color
        } else if (order.getStatus().equals("PAID")) {
            status.setTextColor(Color.parseColor("#008000")); // Green color
        } else if (order.getStatus().equals("CANCELLED")) {
            status.setTextColor(Color.parseColor("#FF0000")); // Red color
        }

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailAdminActivity.class);
                intent.putExtra("order", order);
                context.startActivity(intent);
            }
        });

        return item;
    }
}
