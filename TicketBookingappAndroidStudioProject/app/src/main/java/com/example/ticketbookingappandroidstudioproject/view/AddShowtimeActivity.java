package com.example.ticketbookingappandroidstudioproject.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.ApiResponse;
import com.example.ticketbookingappandroidstudioproject.data.MoviesData;
import com.example.ticketbookingappandroidstudioproject.data.ScreensData;
import com.example.ticketbookingappandroidstudioproject.model.Movie;
import com.example.ticketbookingappandroidstudioproject.model.Screen;
import com.example.ticketbookingappandroidstudioproject.model.ShowTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddShowtimeActivity extends AppCompatActivity {

    Spinner spinnerMovie, spinnerScreen;
    EditText edtDate, edtStartTime, edtEndTime, edtPrice;
    Button btnSave, btnCancel;

    List<Movie> movieList = new ArrayList<>();
    List<Screen> screenList = new ArrayList<>();

    private int selectedYear, selectedMonth, selectedDay;
    private int startHour, startMinute, endHour, endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_showtime_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        loadMovies();
        loadScreens();
        addEvents();
    }

    private void addControls() {
        spinnerMovie = findViewById(R.id.spinnerMovie);
        spinnerScreen = findViewById(R.id.spinnerScreen);
        edtDate = findViewById(R.id.edtDate);
        edtStartTime = findViewById(R.id.edtStartTime);
        edtEndTime = findViewById(R.id.edtEndTime);
        edtPrice = findViewById(R.id.edtPrice);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Khởi tạo giá trị mặc định cho ngày/giờ
        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        startHour = calendar.get(Calendar.HOUR_OF_DAY);
        startMinute = 0;
        endHour = startHour + 2;
        endMinute = 0;
    }

    private void addEvents() {
        edtDate.setOnClickListener(v -> showDatePicker());
        edtStartTime.setOnClickListener(v -> showTimePicker(true));
        edtEndTime.setOnClickListener(v -> showTimePicker(false));

        btnSave.setOnClickListener(v -> addShowtime());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedYear = year;
                    selectedMonth = month;
                    selectedDay = dayOfMonth;
                    String dateStr = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    edtDate.setText(dateStr);
                },
                selectedYear, selectedMonth, selectedDay
        );
        datePickerDialog.show();
    }

    private void showTimePicker(boolean isStartTime) {
        int hour = isStartTime ? startHour : endHour;
        int minute = isStartTime ? startMinute : endMinute;

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minuteOfHour) -> {
                    if (isStartTime) {
                        startHour = hourOfDay;
                        startMinute = minuteOfHour;
                        edtStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour));
                    } else {
                        endHour = hourOfDay;
                        endMinute = minuteOfHour;
                        edtEndTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour));
                    }
                },
                hour, minute, true
        );
        timePickerDialog.show();
    }

    private void loadMovies() {
        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;
            ApiService.apiService.getMovies(bearerToken, new HashMap<>()).enqueue(new Callback<MoviesData>() {
                @Override
                public void onResponse(Call<MoviesData> call, Response<MoviesData> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        movieList.clear();
                        movieList.addAll(response.body().getData());

                        List<String> movieNames = new ArrayList<>();
                        for (Movie movie : movieList) {
                            movieNames.add(movie.getTitle());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                AddShowtimeActivity.this,
                                android.R.layout.simple_spinner_item,
                                movieNames
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMovie.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<MoviesData> call, Throwable t) {
                    Toast.makeText(AddShowtimeActivity.this, "Error to load movies", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadScreens() {
        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;
            ApiService.apiService.getScreens(bearerToken, new HashMap<>()).enqueue(new Callback<ScreensData>() {
                @Override
                public void onResponse(Call<ScreensData> call, Response<ScreensData> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        screenList.clear();
                        screenList.addAll(response.body().getData());

                        List<String> screenNames = new ArrayList<>();
                        for (Screen screen : screenList) {
                            String displayName = screen.getName();
                            if (screen.getFormat() != null && !screen.getFormat().isEmpty()) {
                                displayName += " - " + screen.getFormat();
                            }
                            screenNames.add(displayName);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                AddShowtimeActivity.this,
                                android.R.layout.simple_spinner_item,
                                screenNames
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerScreen.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ScreensData> call, Throwable t) {
                    Toast.makeText(AddShowtimeActivity.this, "Error to load screens", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addShowtime() {
        if (edtDate.getText().toString().isEmpty() ||
                edtStartTime.getText().toString().isEmpty() ||
                edtEndTime.getText().toString().isEmpty() ||
                edtPrice.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Lỗi")
                    .setMessage("Vui lòng điền đầy đủ thông tin.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (movieList.isEmpty() || screenList.isEmpty()) {
            Toast.makeText(this, "No data about movies and screens", Toast.LENGTH_SHORT).show();
            return;
        }

        int movieIndex = spinnerMovie.getSelectedItemPosition();
        int screenIndex = spinnerScreen.getSelectedItemPosition();
        int movieId = movieList.get(movieIndex).getId();
        int screenId = screenList.get(screenIndex).getId();
        int basePrice = Integer.parseInt(edtPrice.getText().toString().trim());

        // Tạo Date objects
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(selectedYear, selectedMonth, selectedDay, startHour, startMinute, 0);
        Date startAt = startCalendar.getTime();

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(selectedYear, selectedMonth, selectedDay, endHour, endMinute, 0);
        Date endAt = endCalendar.getTime();

        // Tạo ShowTime object
        ShowTime showtime = new ShowTime();
        showtime.setMovieId(movieId);
        showtime.setScreenId(screenId);
        showtime.setStartAt(startAt);
        showtime.setEndAt(endAt);
        showtime.setBasePrice(basePrice);
        showtime.setStatus("SCHEDULED");

        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;
            ApiService.apiService.addShowtime(bearerToken, showtime).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(AddShowtimeActivity.this, "Showtime added successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddShowtimeActivity.this, "Failed to add screen: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(AddShowtimeActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}
