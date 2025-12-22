package com.example.ticketbookingappandroidstudioproject.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.SeatMapResponse;
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

    private static final int SEATS_PER_ROW = 8; // Số ghế mỗi hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        initViews();
        setupToolbar();
        LoadSeatsFromAPI(); // Load dữ liệu mẫu
        setupRecyclerView();
        setupButtons();
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

    private void setupButtons() {
        btnContinue.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một ghế", Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý tiếp tục thanh toán
                Toast.makeText(this, "Tiếp tục thanh toán với " + selectedSeats.size() + " ghế", Toast.LENGTH_SHORT).show();
                // TODO: Chuyển sang màn hình thanh toán
            }
        });
    }

    private void LoadSeatsFromAPI() {
        allSeats = new ArrayList<>();

        // Nhận thông tin từ Intent
        Intent intent = getIntent();
        String showTime = intent.getStringExtra("Time");
        int movieId = intent.getIntExtra("MOVIE_ID", 0);
        int roomId = intent.getIntExtra("ROOM_ID", 0);
        int showTimeId = intent.getIntExtra("SHOWTIME_ID", 0);
        String roomName = intent.getStringExtra("Screen");
        String movieTitle=intent.getStringExtra("MOVIE_TITLE");
        // Set thông tin phim từ Intent hoặc dữ liệu mẫu
        tvMovieTitle.setText(movieTitle != null ? movieTitle : "Bad Boys: Ride or Die");
        tvShowTime.setText(showTime != null ? showTime : "Suất chiếu: 19:30 - 30/11/2025");
        tvCinemaRoom.setText(roomName != null ? "Phòng chiếu: " + roomName : "Phòng chiếu: A1");
        SharedPreferences prefs=getSharedPreferences("YourAppPrefs",MODE_PRIVATE);
        String token=prefs.getString("token",null);
        // Load dữ liệu ghế từ API hoặc dữ liệu mẫu
        String bearerToken="Bearer "+token;
        ApiService.apiService.getSeatMap(bearerToken,showTimeId).enqueue(new Callback<SeatMapResponse>() {
            @Override
            public void onResponse(Call<SeatMapResponse> call, Response<SeatMapResponse> response) {
                if(response.isSuccessful()&& response.body()!=null)
                {
                    SeatMapResponse seatMapResponse=response.body();
                    if(seatMapResponse.isSuccess()&&seatMapResponse.getData()!=null)
                    {
                        allSeats.addAll(seatMapResponse.getData());
                        seatAdapter.updateSeats(allSeats);
                    }
                }
                else
                {
                    Toast.makeText(SeatSelectionActivity.this, "lỗi load ghế", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SeatMapResponse> call, Throwable t) {
                Toast.makeText(SeatSelectionActivity.this, "lỗi kết nối "+ t.getMessage(), Toast.LENGTH_SHORT).show();
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

