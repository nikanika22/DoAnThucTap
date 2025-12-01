package com.example.ticketbookingappandroidstudioproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.ticketbookingappandroidstudioproject.admin.fragment.Fragment_movie;
import com.example.ticketbookingappandroidstudioproject.admin.fragment.Fragment_screen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupUIBasedOnRole();
    }

    private void setupUIBasedOnRole() {
        // Đọc vai trò đã lưu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("user_role", "CUSTOMER");

        View headerCustomer = findViewById(R.id.headerLayoutCustomer);
        View headerAdmin = findViewById(R.id.headerAdmin);
        View bottomBarCustomer = findViewById(R.id.bottomBarCustomer);
        View bottomBarAdmin = findViewById(R.id.bottomBarAdmin);
        TextView title = findViewById(R.id.header);
        EditText editSearch = findViewById(R.id.edtSearch);

        if ("ADMIN".equals(userRole)) {

            headerCustomer.setVisibility(View.GONE);
            headerAdmin.setVisibility(View.VISIBLE);
            bottomBarCustomer.setVisibility(View.GONE);
            bottomBarAdmin.setVisibility(View.VISIBLE);
            editSearch.setVisibility(View.GONE);

            findViewById(R.id.tabMovie).setOnClickListener(v -> loadFragment(new com.example.ticketbookingappandroidstudioproject.admin.fragment.Fragment_movie()));
            findViewById(R.id.tabScreen).setOnClickListener(v -> loadFragment(new com.example.ticketbookingappandroidstudioproject.admin.fragment.Fragment_screen()));


            loadFragment(new com.example.ticketbookingappandroidstudioproject.admin.fragment.Fragment_movie());

        } else {

            headerCustomer.setVisibility(View.VISIBLE);
            headerAdmin.setVisibility(View.GONE);
            bottomBarCustomer.setVisibility(View.VISIBLE);
            bottomBarAdmin.setVisibility(View.GONE);


            findViewById(R.id.tabHome).setOnClickListener(v -> loadFragment(new com.example.ticketbookingappandroidstudioproject.Fragment_movie()));


            loadFragment(new com.example.ticketbookingappandroidstudioproject.Fragment_movie());
        }
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
