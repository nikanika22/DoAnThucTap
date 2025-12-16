package com.example.ticketbookingappandroidstudioproject.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class UpdateMovieActivity extends AppCompatActivity {
    EditText edtTitle, edtDuration, edtPoster;

    Spinner actvGenre, actvRating;
    Button btnUpdate, btnCancel;

    CheckBox isActive;

    private final String[] genreOptions = {"Action", "Honor", "Adventur", "Sci-Fi"};

    private final String[] ratingOptions = {"P", "T13", "T16", "T18"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_movie_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        setupGenreDropdown();

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        int movieId = getIntent().getIntExtra("id", -1);
        if (movie != null) {
            edtTitle.setText(movie.getTitle());
            edtDuration.setText(movie.getDuration_min());
            actvGenre.setSelection(getIndex(actvGenre, movie.getGenre()));
            edtPoster.setText(movie.getPoster());
            actvRating.setSelection(getIndex(actvRating, movie.getRating_code()));
            isActive.setChecked(movie.isIs_active());
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMovie(movieId);
            }
        });
        
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public int getIndex(Spinner spinner, String selection) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(selection)) {
                return i;
            }
        }
        return 0;
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

    private void addControls() {
        edtTitle = findViewById(R.id.title);
        edtDuration = findViewById(R.id.duration);
        actvGenre = findViewById(R.id.genre);
        edtPoster = findViewById(R.id.poster);
        actvRating = findViewById(R.id.rating);
        btnUpdate = findViewById(R.id.add);
        btnCancel = findViewById(R.id.cancel);
        isActive = findViewById(R.id.isactive);
    }

    private void updateMovie(int movieId) {
        String title = edtTitle.getText().toString().trim();
        String duration = edtDuration.getText().toString().trim();
        String genre = actvGenre.getSelectedItem().toString().trim();
        String poster = edtPoster.getText().toString().trim();
        String rating = actvRating.getSelectedItem().toString().trim();
        boolean isActive = this.isActive.isChecked();

        if (title.isEmpty() || duration.isEmpty() || genre.isEmpty() || poster.isEmpty() || rating.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please fill in all fields.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;
            ApiService.apiService.updateMovie(bearerToken, movieId, new Movie(title, duration, genre, poster, rating, isActive)).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(UpdateMovieActivity.this, "Movie updated successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateMovieActivity.this, "Failed to updated movie: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(UpdateMovieActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}