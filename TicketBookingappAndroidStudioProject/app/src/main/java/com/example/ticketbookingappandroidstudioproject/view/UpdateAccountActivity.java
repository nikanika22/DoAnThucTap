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
import com.example.ticketbookingappandroidstudioproject.model.Account;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAccountActivity extends AppCompatActivity {
    Button btnUpdate;
    EditText fullname, email, phone, password, role;

    CheckBox isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_account_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();

        Account account = (Account) getIntent().getSerializableExtra("account");
        int accountId = getIntent().getIntExtra("id", -1);

        if (account != null) {
            fullname.setText(account.getFullName());
            email.setText(account.getEmail());
            phone.setText(account.getPhone());
            password.setText(account.getPasswordHash());
            role.setText(account.getRole());
            isActive.setChecked(account.isActive());
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount(accountId);
            }
        });
    }

    private void addControls() {
        btnUpdate = findViewById(R.id.add);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.Phone);
        password = findViewById(R.id.password);
        role = findViewById(R.id.role);
    }

    private void updateAccount(int accountId) {
        String fullname = this.fullname.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String phone = this.phone.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String role = this.role.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
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
            ApiService.apiService.updateAccount(bearerToken, accountId, new Account(email, phone, fullname, password, role, isActive.isChecked())).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(UpdateAccountActivity.this, "Account updated successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateAccountActivity.this, "Failed to update account: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(UpdateAccountActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
    }


}