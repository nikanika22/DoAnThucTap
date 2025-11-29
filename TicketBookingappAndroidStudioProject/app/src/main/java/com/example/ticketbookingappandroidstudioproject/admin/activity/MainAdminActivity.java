package com.example.ticketbookingappandroidstudioproject.admin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.admin.fragment.Fragment_movie;
import com.example.ticketbookingappandroidstudioproject.admin.fragment.Fragment_screen;

public class MainAdminActivity extends AppCompatActivity {
    TextView tvTitle;

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

        tvTitle = findViewById(R.id.header);
        View tabMovie = findViewById(R.id.tabMovie);
        View tabScreen = findViewById(R.id.tabScreen);
        View tabShowtime = findViewById(R.id.tabShowtime);
        View tabProduct = findViewById(R.id.tabProduct);
        View tabOrder = findViewById(R.id.tabOrder);
        View tabAccount = findViewById(R.id.tabAccount);

        tabMovie.setOnClickListener(v -> loadFragment(new Fragment_movie()));
        tabScreen.setOnClickListener(v -> loadFragment(new Fragment_screen()));
//        tabShowtime.setOnClickListener(v -> loadFragment(new Fragment_showtime()));
//        tabProduct.setOnClickListener(v -> loadFragment(new Fragment_product()));
//        tabOrder.setOnClickListener(v -> loadFragment(new Fragment_order()));
//        tabAccount.setOnClickListener(v -> loadFragment(new Fragment_account()));

        if (savedInstanceState == null) {
            loadFragment(new Fragment_movie());
            tvTitle.setText("Movie");
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
