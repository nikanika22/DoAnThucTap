package com.example.ticketbookingappandroidstudioproject.view;

import android.content.Intent;
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
import com.example.ticketbookingappandroidstudioproject.data.RegisterData;
import com.example.ticketbookingappandroidstudioproject.data.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, phoneEditText, fullnameEditText;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        control();
        event();
    }
    private void event()
    {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRegisterDetails();
            }
        });
    }

    private  void control()
    {
        emailEditText=findViewById(R.id.editTextUsername);
        passwordEditText=findViewById(R.id.editTextPassword);
        phoneEditText =findViewById(R.id.editTextPhone);
        fullnameEditText=findViewById(R.id.editTextFullName);
        btnRegister=findViewById(R.id.buttonRegister);

    }

    private void sendRegisterDetails(){
        // Validate input
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String fullname = fullnameEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || phone.isEmpty() || fullname.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(email, password, phone, fullname);

        ApiService.apiService.sendRegisterRequest(registerRequest).enqueue(new Callback<RegisterData>() {
            @Override
            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                if (response.isSuccessful()) {
                    RegisterData registerData = response.body();

                    if (registerData != null && registerData.isSuccess()) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = registerData != null ? registerData.getMessage() : "Đăng ký thất bại";
                        Toast.makeText(RegisterActivity.this, "Lỗi: " + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterData> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}