package com.example.ticketbookingappandroidstudioproject.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.data.ApiResponse;
import com.example.ticketbookingappandroidstudioproject.model.Screen;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditScreenActivity extends AppCompatActivity {
    Button btnSave;

    EditText edtCode, edtName, edtFormat, edtRowCount, edtColCount;

    CheckBox cbIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_screen_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();

        Screen screen = (Screen) getIntent().getSerializableExtra("screen");
        int screenId = getIntent().getIntExtra("id", -1);

        if (screen != null) {
            edtCode.setText(screen.getCode());
            edtName.setText(screen.getName());
            edtFormat.setText(screen.getFormat());
            edtRowCount.setText(String.valueOf(screen.getRowCount()));
            edtColCount.setText(String.valueOf(screen.getColCount()));
            cbIsActive.setChecked(screen.isActive());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScreen(screenId);
            }
        });
    }

    private void updateScreen(int screenId) {
        String code = edtCode.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String format = edtFormat.getText().toString().trim();
        int rowCount = Integer.parseInt(edtRowCount.getText().toString().trim());
        int colCount = Integer.parseInt(edtColCount.getText().toString().trim());
        boolean isActive = cbIsActive.isChecked();

        if (code.isEmpty() || name.isEmpty() || format.isEmpty() || rowCount == 0 || colCount == 0) {
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
            ApiService.apiService.updateScreen(bearerToken, screenId, new Screen(code, name, format, rowCount, colCount, isActive)).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(EditScreenActivity.this, "Screen updated successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditScreenActivity.this, "Failed to updated screen: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(EditScreenActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        btnSave = findViewById(R.id.save);

        edtCode = findViewById(R.id.code);
        edtName = findViewById(R.id.name);
        edtFormat = findViewById(R.id.format);
        edtRowCount = findViewById(R.id.rowcount);
        edtColCount = findViewById(R.id.columncount);

        cbIsActive = findViewById(R.id.isactive);
    }
}