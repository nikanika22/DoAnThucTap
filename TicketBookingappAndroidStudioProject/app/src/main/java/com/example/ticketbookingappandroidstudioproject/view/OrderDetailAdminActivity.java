package com.example.ticketbookingappandroidstudioproject.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.ApiResponse;
import com.example.ticketbookingappandroidstudioproject.model.Movie;
import com.example.ticketbookingappandroidstudioproject.model.Order;
import com.example.ticketbookingappandroidstudioproject.utils.DateTimeUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailAdminActivity extends AppCompatActivity {
    TextView orderTitle, customerName, customerEmail, channel, movieTitle, showtime, screen, total, method, ticket, product;
    Spinner status;
    Button btnCancel, btnSave;
    ImageView btnClose;
    String[] statusOptions = {"INIT", "PAID", "CANCELLED"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dialog_order_detail_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.order_detail_dialog), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        setupGenreDropdown();
        addEvents();
        showData();
    }

    private void showData() {
        Order order = (Order) getIntent().getSerializableExtra("order");
        if (order != null) {
            orderTitle.setText("Order #" + order.getId());

            if (order.getAccount() != null) {
                customerName.setText(order.getAccount().getFullName() != null ? order.getAccount().getFullName() : "N/A");
                customerEmail.setText(order.getAccount().getEmail() != null ? order.getAccount().getEmail() : "N/A");
            }
            
            channel.setText(order.getChannel() != null ? order.getChannel() : "N/A");

            if (order.getShowtime() != null) {
                if (order.getShowtime().getMovie() != null) {
                    movieTitle.setText(order.getShowtime().getMovie().getTitle() != null ? order.getShowtime().getMovie().getTitle() : "N/A");
                } else {
                    movieTitle.setText("N/A");
                }
                
                // Format showtime: "16/12/2025 - 09:00"
                String formattedShowtime = DateTimeUtils.formatDateTime(order.getShowtime().getStartAt());
                showtime.setText(formattedShowtime.isEmpty() ? "N/A" : formattedShowtime);
                
                if (order.getShowtime().getScreen() != null) {
                    screen.setText(order.getShowtime().getScreen().getName() != null ? order.getShowtime().getScreen().getName() : "N/A");
                } else {
                    screen.setText("N/A");
                }
            } else {
                movieTitle.setText("N/A");
                showtime.setText("N/A");
                screen.setText("N/A");
            }
            
            total.setText(String.format("%,d VND", order.getTotalAmount()));
            method.setText(order.getPaymentMethod() != null ? order.getPaymentMethod() : "N/A");
            ticket.setText("Không có");
            product.setText("Không có");

            int statusIndex = 0;
            for (int i = 0; i < statusOptions.length; i++) {
                if (statusOptions[i].equals(order.getStatus())) {
                    statusIndex = i;
                    break;
                }
            }
            status.setSelection(statusIndex);
        }
    }

    private void addControls() {
        orderTitle = findViewById(R.id.tvDialogTitle);
        customerName = findViewById(R.id.tvCustomerName);
        customerEmail = findViewById(R.id.tvCustomerEmail);
        channel = findViewById(R.id.tvChannel);
        movieTitle = findViewById(R.id.tvMovieTitle);
        showtime = findViewById(R.id.tvShowtime);
        screen = findViewById(R.id.tvScreen);
        total = findViewById(R.id.tvTotalAmount);
        method = findViewById(R.id.tvPaymentMethod);
        ticket = findViewById(R.id.ticket);
        product = findViewById(R.id.product);
        status = findViewById(R.id.spinnerStatus);
        btnClose = findViewById(R.id.btnClose);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupGenreDropdown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                statusOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);
        status.setSelection(0);
    }

    private void addEvents() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStatus();
            }
        });
    }

    private void saveStatus() {
    }
}