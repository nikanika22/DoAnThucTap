package com.example.ticketbookingappandroidstudioproject.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.CreateOrderRequest;
import com.example.ticketbookingappandroidstudioproject.data.OrderResponse;
import com.example.ticketbookingappandroidstudioproject.data.SeatMapResponse;
import com.example.ticketbookingappandroidstudioproject.data.SeatOrderRequest;
import com.example.ticketbookingappandroidstudioproject.model.Seat;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatSelectionActivity extends AppCompatActivity implements SeatAdapter.OnSeatClickListener {

    private RecyclerView rvSeats;
    private TextView tvMovieTitle, tvShowTime, tvCinemaRoom;
    private TextView tvSelectedSeats, tvTotalPrice;
    private Button btnContinue;

    private SeatAdapter seatAdapter;
    private List<Seat> allSeats;
    private List<Seat> selectedSeats;
    private int showTimeId;

    private static final int SEATS_PER_ROW = 8; // Số ghế mỗi hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        initViews();
        setupToolbar();
        setupRecyclerView(); // Setup adapter trước
        LoadSeatsFromAPI(); // Sau đó load data
        setupButtons();
    }

    private void setupButtons() {
        btnContinue.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một ghế", Toast.LENGTH_SHORT).show();
            } else {
                createOrder();
            }
        });
    }

    private void initViews() {
        rvSeats = findViewById(R.id.rvSeats);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvShowTime = findViewById(R.id.tvShowTime);
        tvCinemaRoom = findViewById(R.id.tvCinemaRoom);
        tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnContinue = findViewById(R.id.btnContinue);

        selectedSeats = new ArrayList<>();
        allSeats = new ArrayList<>(); // Khởi tạo sớm để adapter có thể dùng
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, SEATS_PER_ROW);
        rvSeats.setLayoutManager(layoutManager);

        seatAdapter = new SeatAdapter(allSeats, this);
        rvSeats.setAdapter(seatAdapter);
    }

    private void createOrder() {
        // Convert selected seats to simplified format (only id and price)
        List<SeatOrderRequest> seatOrderRequests = new ArrayList<>();
        for (Seat seat : selectedSeats) {
            seatOrderRequests.add(new SeatOrderRequest(seat.getId(), seat.getPrice()));
        }

        CreateOrderRequest request = new CreateOrderRequest(showTimeId, seatOrderRequests);

        SharedPreferences prefs = getSharedPreferences("YourAppPrefs", MODE_PRIVATE);
        String token = prefs.getString("auth_token", null);

        if (token == null) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_LONG).show();
            return;
        }

        // Debug log
        android.util.Log.d("SeatSelection",
                "Creating order with showtime_id: " + showTimeId + ", seats count: " + seatOrderRequests.size());

        Toast.makeText(this, "Đang đặt vé...", Toast.LENGTH_SHORT).show();

        ApiService.apiService.createOrder("Bearer " + token, request).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                android.util.Log.d("SeatSelection", "Response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    android.util.Log.d("SeatSelection", "Response successful, body exists");
                    if (response.body().isSuccess()) {
                        int orderId = response.body().getData().getId();
                        android.util.Log.d("SeatSelection", "Order created successfully with ID: " + orderId);
                        showSuccessDialog(orderId);
                    } else {
                        String errorMsg = response.body().getMessage();
                        android.util.Log.e("SeatSelection", "Order failed: " + errorMsg);
                        Toast.makeText(SeatSelectionActivity.this,
                                "Đặt vé thất bại: " + (errorMsg != null ? errorMsg : "Lỗi không xác định"),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
                        android.util.Log.e("SeatSelection", "HTTP Error " + response.code() + ": " + errorBody);
                        Toast.makeText(SeatSelectionActivity.this,
                                "Lỗi " + response.code() + ": "
                                        + (errorBody.length() > 100 ? errorBody.substring(0, 100) + "..." : errorBody),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        android.util.Log.e("SeatSelection", "Error reading error body", e);
                        Toast.makeText(SeatSelectionActivity.this,
                                "Lỗi: " + response.code(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                android.util.Log.e("SeatSelection", "Network failure", t);
                Toast.makeText(SeatSelectionActivity.this,
                        "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSuccessDialog(int orderId) {
        new AlertDialog.Builder(this)
                .setTitle("Đặt vé thành công!")
                .setMessage("Mã đơn hàng: #" + orderId + "\n\nVé đã được gửi về email.")
                .setPositiveButton("OK", (dialog, which) -> {
                    selectedSeats.clear();
                    reloadSeatMap();
                })
                .setCancelable(false)
                .show();
    }

    private void reloadSeatMap() {
        allSeats.clear();
        seatAdapter.notifyDataSetChanged();

        SharedPreferences prefs = getSharedPreferences("YourAppPrefs", MODE_PRIVATE);
        String tokenValue = prefs.getString("auth_token", null);

        // Kiểm tra token trước khi reload
        if (tokenValue == null || tokenValue.isEmpty()) {
            Toast.makeText(this, "Token không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        String token = "Bearer " + tokenValue;
        ApiService.apiService.getSeatMap(token, showTimeId).enqueue(new Callback<SeatMapResponse>() {
            @Override
            public void onResponse(Call<SeatMapResponse> call, Response<SeatMapResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SeatMapResponse seatMapResponse = response.body();
                    if (seatMapResponse.isSuccess() && seatMapResponse.getData() != null) {
                        allSeats.addAll(seatMapResponse.getData());
                        seatAdapter.updateSeats(allSeats);
                        updateBottomBar();
                    }
                }
            }

            @Override
            public void onFailure(Call<SeatMapResponse> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this,
                        "Không thể reload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LoadSeatsFromAPI() {
        // Nhận thông tin từ Intent
        Intent intent = getIntent();
        String showTime = intent.getStringExtra("Time");
        int movieId = intent.getIntExtra("MOVIE_ID", 0);
        int roomId = intent.getIntExtra("ROOM_ID", 0);
        showTimeId = intent.getIntExtra("SHOWTIME_ID", 0);
        String roomName = intent.getStringExtra("Screen");
        String movieTitle = intent.getStringExtra("MOVIE_TITLE");
        // Set thông tin phim từ Intent hoặc dữ liệu mẫu
        tvMovieTitle.setText(movieTitle != null ? movieTitle : "Bad Boys: Ride or Die");
        tvShowTime.setText(showTime != null ? showTime : "Suất chiếu: 19:30 - 30/11/2025");
        tvCinemaRoom.setText(roomName != null ? "Phòng chiếu: " + roomName : "Phòng chiếu: A1");
        SharedPreferences prefs = getSharedPreferences("YourAppPrefs", MODE_PRIVATE);
        String token = prefs.getString("auth_token", null);

        // Kiểm tra token trước khi gọi API
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Load dữ liệu ghế từ API
        String bearerToken = "Bearer " + token;
        ApiService.apiService.getSeatMap(bearerToken, showTimeId).enqueue(new Callback<SeatMapResponse>() {
            @Override
            public void onResponse(Call<SeatMapResponse> call, Response<SeatMapResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SeatMapResponse seatMapResponse = response.body();
                    if (seatMapResponse.isSuccess() && seatMapResponse.getData() != null) {
                        allSeats.addAll(seatMapResponse.getData());
                        seatAdapter.updateSeats(allSeats);
                    } else {
                        Toast.makeText(SeatSelectionActivity.this,
                                "Không có dữ liệu ghế", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string()
                                : "Unknown error";
                        Toast.makeText(SeatSelectionActivity.this,
                                "Lỗi load ghế - Code: " + response.code() + "\n" + errorBody,
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(SeatSelectionActivity.this,
                                "Lỗi load ghế - Code: " + response.code(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SeatMapResponse> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this,
                        "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onSeatClick(Seat seat, int position) {
        if (seat.getStatus().equalsIgnoreCase("sold")) {
            Toast.makeText(this, "Ghế này đã được đặt", Toast.LENGTH_SHORT).show();
            return;
        }

        // Toggle trạng thái ghế
        if (seat.getStatus().equalsIgnoreCase("selected")) {
            seat.setStatus("available");
            selectedSeats.remove(seat);
        } else {
            seat.setStatus("selected");
            selectedSeats.add(seat);
        }

        seatAdapter.notifyItemChanged(position);
        updateBottomBar();
    }

    private void updateBottomBar() {
        // Cập nhật danh sách ghế đã chọn
        if (selectedSeats.isEmpty()) {
            tvSelectedSeats.setText("Chưa chọn");
            btnContinue.setEnabled(false);
        } else {
            StringBuilder seatLabels = new StringBuilder();
            for (int i = 0; i < selectedSeats.size(); i++) {
                seatLabels.append(selectedSeats.get(i).getSeatLabel());
                if (i < selectedSeats.size() - 1) {
                    seatLabels.append(", ");
                }
            }
            tvSelectedSeats.setText(seatLabels.toString());
            btnContinue.setEnabled(true);
        }

        // Cập nhật tổng tiền
        double totalPrice = 0;
        for (Seat seat : selectedSeats) {
            totalPrice += seat.getPrice();
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTotalPrice.setText(formatter.format(totalPrice));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Kiểm tra nếu có ghế đã chọn, hiển thị cảnh báo
        if (!selectedSeats.isEmpty()) {
            // TODO: Hiển thị dialog xác nhận
            Toast.makeText(this, "Bạn có chắc muốn thoát? Ghế đã chọn sẽ bị hủy.", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
    }
}
