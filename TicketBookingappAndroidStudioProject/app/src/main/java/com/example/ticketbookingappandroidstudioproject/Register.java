package com.example.ticketbookingappandroidstudioproject;

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

import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.model.RegisterData;
import com.example.ticketbookingappandroidstudioproject.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

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

        RegisterRequest registerRequest = new RegisterRequest(emailEditText.getText().toString(),
                passwordEditText.getText().toString(), phoneEditText.getText().toString(), fullnameEditText.getText().toString());
        ApiService.apiService.sendRegisterRequest(registerRequest).enqueue(new Callback<RegisterData>() {
            @Override
            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                    Toast.makeText(Register.this,"Register successful!", Toast.LENGTH_SHORT).show();
                    RegisterData registerData = response.body();
                    if( registerData.isSuccess() == true ) {
                        Intent intent= new Intent(Register.this,MainActivity.class);
                        startActivity(intent);
                    }
            }

            @Override
            public void onFailure(Call<RegisterData> call, Throwable t) {
                Toast.makeText(Register.this,"Register failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}