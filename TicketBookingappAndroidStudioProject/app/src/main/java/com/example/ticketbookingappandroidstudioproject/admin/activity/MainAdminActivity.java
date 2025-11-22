package com.example.ticketbookingappandroidstudioproject.admin.activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.admin.fragment.Fragment_movie;

public class MainAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View tabMovie = findViewById(R.id.tabMovie);
        View tabShowtime = findViewById(R.id.tabShowtime);
        View tabProduct = findViewById(R.id.tabProduct);
        View tabOrder = findViewById(R.id.tabOrder);
        View tabAccount = findViewById(R.id.tabAccount);

        tabMovie.setOnClickListener(v -> loadFragment(new Fragment_movie()));
//        tabShowtime.setOnClickListener(v -> loadFragment(new Fragment_showtime()));
//        tabProduct.setOnClickListener(v -> loadFragment(new Fragment_product()));
//        tabOrder.setOnClickListener(v -> loadFragment(new Fragment_order()));
//        tabAccount.setOnClickListener(v -> loadFragment(new Fragment_account()));

        loadFragment(new Fragment_movie());
    }

    private void loadFragment(Fragment_movie fragmentMovie){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new Fragment_movie())
                .commit();
    }
}