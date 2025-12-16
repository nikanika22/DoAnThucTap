package com.example.ticketbookingappandroidstudioproject.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.ticketbookingappandroidstudioproject.data.ApiResponse;
import com.example.ticketbookingappandroidstudioproject.model.Movie;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovieActivity extends AppCompatActivity {
    EditText edtTitle, edtDuration, edtPoster;
    Spinner actvGenre, actvRating;
    Button btnAdd, btnCancel;
    private final String[] genreOptions = {"Action", "Horror", "Adventur", "Sci-Fi"};

    private final String[] ratingOptions = {"P", "T13", "T16", "T18"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_movie_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        setupGenreDropdown();
        addEvents();
    }

    private void addControls() {
        edtTitle = findViewById(R.id.title);
        edtDuration = findViewById(R.id.duration);
        actvGenre = findViewById(R.id.genre);
        edtPoster = findViewById(R.id.poster);
        actvRating = findViewById(R.id.rating);
        btnAdd = findViewById(R.id.add);
        btnCancel = findViewById(R.id.cancel);
    }
    
    private void setupGenreDropdown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                genreOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actvGenre.setAdapter(adapter);
        actvGenre.setSelection(0);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                ratingOptions
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actvRating.setAdapter(adapter2);
        actvRating.setSelection(0);
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();
            }
        });
        
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addMovie() {
        String title = edtTitle.getText().toString().trim();
        String duration = edtDuration.getText().toString().trim();
        String genre = actvGenre.getSelectedItem().toString().trim();
        String poster = edtPoster.getText().toString().trim();
        String rating = actvRating.getSelectedItem().toString().trim();

        if (title.isEmpty() || duration.isEmpty() || genre.isEmpty() || poster.isEmpty() || rating.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Lỗi")
                    .setMessage("Vui lòng điền đầy đủ thông tin.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;
            ApiService.apiService.addMovie(bearerToken, new Movie(title, duration, genre, poster, rating, true)).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(AddMovieActivity.this, "Thêm phim thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddMovieActivity.this, "Thêm phim thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(AddMovieActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Bạn chưa đăng nhập.", Toast.LENGTH_SHORT).show();
        }
    }

}
