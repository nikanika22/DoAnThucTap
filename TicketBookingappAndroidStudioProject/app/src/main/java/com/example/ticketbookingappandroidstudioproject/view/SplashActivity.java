package com.example.ticketbookingappandroidstudioproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticketbookingappandroidstudioproject.R;

public class SplashActivity extends AppCompatActivity {

    Button btnLogin,btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        control();
        event();
    }

    private void event() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void control() {
        btnLogin=findViewById(R.id.btnLogin);
        btnSignUp=findViewById(R.id.btnSignUp);
    }
}