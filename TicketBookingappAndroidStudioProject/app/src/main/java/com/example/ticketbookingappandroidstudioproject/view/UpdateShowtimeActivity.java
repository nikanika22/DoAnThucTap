package com.example.ticketbookingappandroidstudioproject.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class UpdateShowtimeActivity extends AppCompatActivity {

    Spinner spinnerMovie, spinnerScreen, spinnerStatus;
    EditText edtDate, edtStartTime, edtEndTime, edtPrice;
    Button btnSave, btnCancel;

    List<Movie> movieList = new ArrayList<>();
    List<Screen> screenList = new ArrayList<>();

    private final String[] statusOptions = {"SCHEDULED", "OPEN", "CLOSED", "CANCELLED"};

    // Lưu trữ ngày/giờ đã chọn
    private int selectedYear, selectedMonth, selectedDay;
    private int startHour, startMinute, endHour, endMinute;

    private ShowTime currentShowtime;
    private int showtimeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_showtime_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentShowtime = (ShowTime) getIntent().getSerializableExtra("showtime");
        if (currentShowtime == null) {
            Toast.makeText(this, "No showtime could be found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        showtimeId = currentShowtime.getId();

        addControls();
        setupStatusSpinner();
        loadMovies();
        loadScreens();
        populateCurrentData();
        addEvents();
    }

    private void addControls() {
        spinnerMovie = findViewById(R.id.spinnerMovie);
        spinnerScreen = findViewById(R.id.spinnerScreen);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        edtDate = findViewById(R.id.edtDate);
        edtStartTime = findViewById(R.id.edtStartTime);
        edtEndTime = findViewById(R.id.edtEndTime);
        edtPrice = findViewById(R.id.edtPrice);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupStatusSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                statusOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void populateCurrentData() {
        // Set giá
        edtPrice.setText(String.valueOf(currentShowtime.getBasePrice()));

        // Set trạng thái
        String status = currentShowtime.getStatus();
        for (int i = 0; i < statusOptions.length; i++) {
            if (statusOptions[i].equals(status)) {
                spinnerStatus.setSelection(i);
                break;
            }
        }

        // Set ngày/giờ từ showtime hiện tại
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        if (currentShowtime.getStartAt() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentShowtime.getStartAt());
            selectedYear = cal.get(Calendar.YEAR);
            selectedMonth = cal.get(Calendar.MONTH);
            selectedDay = cal.get(Calendar.DAY_OF_MONTH);
            startHour = cal.get(Calendar.HOUR_OF_DAY);
            startMinute = cal.get(Calendar.MINUTE);

            edtDate.setText(dateFormat.format(currentShowtime.getStartAt()));
            edtStartTime.setText(timeFormat.format(currentShowtime.getStartAt()));
        } else {
            Calendar cal = Calendar.getInstance();
            selectedYear = cal.get(Calendar.YEAR);
            selectedMonth = cal.get(Calendar.MONTH);
            selectedDay = cal.get(Calendar.DAY_OF_MONTH);
            startHour = cal.get(Calendar.HOUR_OF_DAY);
            startMinute = 0;
        }

        if (currentShowtime.getEndAt() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentShowtime.getEndAt());
            endHour = cal.get(Calendar.HOUR_OF_DAY);
            endMinute = cal.get(Calendar.MINUTE);
            edtEndTime.setText(timeFormat.format(currentShowtime.getEndAt()));
        } else {
            endHour = startHour + 2;
            endMinute = 0;
        }
    }

    private void addEvents() {
        edtDate.setOnClickListener(v -> showDatePicker());
        edtStartTime.setOnClickListener(v -> showTimePicker(true));
        edtEndTime.setOnClickListener(v -> showTimePicker(false));

        btnSave.setOnClickListener(v -> updateShowtime());
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
                        int selectedIndex = 0;
                        for (int i = 0; i < movieList.size(); i++) {
                            Movie movie = movieList.get(i);
                            movieNames.add(movie.getTitle());
                            // Chọn phim hiện tại
                            if (currentShowtime.getMovieId() == movie.getId()) {
                                selectedIndex = i;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                UpdateShowtimeActivity.this,
                                android.R.layout.simple_spinner_item,
                                movieNames
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMovie.setAdapter(adapter);
                        spinnerMovie.setSelection(selectedIndex);
                    }
                }

                @Override
                public void onFailure(Call<MoviesData> call, Throwable t) {
                    Toast.makeText(UpdateShowtimeActivity.this, "Error to load movies", Toast.LENGTH_SHORT).show();
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
                        int selectedIndex = 0;
                        for (int i = 0; i < screenList.size(); i++) {
                            Screen screen = screenList.get(i);
                            String displayName = screen.getName();
                            if (screen.getFormat() != null && !screen.getFormat().isEmpty()) {
                                displayName += " - " + screen.getFormat();
                            }
                            screenNames.add(displayName);
                            // Chọn phòng hiện tại
                            if (currentShowtime.getScreenId() == screen.getId()) {
                                selectedIndex = i;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                UpdateShowtimeActivity.this,
                                android.R.layout.simple_spinner_item,
                                screenNames
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerScreen.setAdapter(adapter);
                        spinnerScreen.setSelection(selectedIndex);
                    }
                }

                @Override
                public void onFailure(Call<ScreensData> call, Throwable t) {
                    Toast.makeText(UpdateShowtimeActivity.this, "Error to load screens", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateShowtime() {
        // Validate
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

        // Lấy giá trị
        int movieIndex = spinnerMovie.getSelectedItemPosition();
        int screenIndex = spinnerScreen.getSelectedItemPosition();
        int movieId = movieList.get(movieIndex).getId();
        int screenId = screenList.get(screenIndex).getId();
        int basePrice = Integer.parseInt(edtPrice.getText().toString().trim());
        String status = spinnerStatus.getSelectedItem().toString();

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
        showtime.setStatus(status);

        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;
            ApiService.apiService.updateShowtime(bearerToken, showtimeId, showtime).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(UpdateShowtimeActivity.this, "Showtime updated successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateShowtimeActivity.this, "Failed to add showtime: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(UpdateShowtimeActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}
