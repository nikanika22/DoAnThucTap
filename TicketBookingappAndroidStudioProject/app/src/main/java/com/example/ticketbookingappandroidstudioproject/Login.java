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
import com.example.ticketbookingappandroidstudioproject.model.LoginData;
import com.example.ticketbookingappandroidstudioproject.model.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

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
        control();
        event();
    }
    private void event()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLoginDetails();
            }
        });
    }

    private  void control()
    {
        emailEditText=findViewById(R.id.editTextUsername);
        passwordEditText=findViewById(R.id.editTextPassword);
        btnLogin=findViewById(R.id.buttonLogin);

    }

    private void sendLoginDetails(){

        LoginRequest loginRequest = new LoginRequest(emailEditText.getText().toString(),
                passwordEditText.getText().toString());
        ApiService.apiService.sendLoginRequest(loginRequest).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                    Toast.makeText(Login.this,"Login successful!", Toast.LENGTH_SHORT).show();
                    LoginData loginData=response.body();
                    if(loginData.isSuccess()==true){
                        Intent intent= new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                    }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Toast.makeText(Login.this,"Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}