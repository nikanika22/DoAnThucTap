package com.example.ticketbookingappandroidstudioproject.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.LoginData;
import com.example.ticketbookingappandroidstudioproject.data.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup toolbar with back button
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Đăng Nhập");
            }
        }

        control();
        event();
    }

    private void event() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLoginDetails();
            }
        });
    }

    private void control() {
        emailEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
    }

    private void sendLoginDetails() {
        LoginRequest loginRequest = new LoginRequest(emailEditText.getText().toString(),
                passwordEditText.getText().toString());

        ApiService.apiService.sendLoginRequest(loginRequest).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    if (loginData != null && loginData.isSuccess() && loginData.getData() != null) {
                        if (loginData.getData().getAccount() != null && loginData.getData().getToken() != null) {
                            String userRole = loginData.getData().getAccount().getRole();

                            // Save the token and role
                            SharedPreferences sharedPreferences = getSharedPreferences("YourAppPrefs",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("auth_token", loginData.getData().getToken());
                            editor.putString("user_role", userRole);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("account", loginData.getData().getAccount());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed: Invalid data", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Login failed: " + (loginData != null ? loginData.getMessage() : "Invalid response"),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
