package com.example.ticketbookingappandroidstudioproject.view;

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

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.fragment.Fragment_account;
import com.example.ticketbookingappandroidstudioproject.fragment.Fragment_account_admin;
import com.example.ticketbookingappandroidstudioproject.fragment.Fragment_movie;
import com.example.ticketbookingappandroidstudioproject.fragment.Fragment_movie_admin;
import com.example.ticketbookingappandroidstudioproject.fragment.Fragment_order_admin;
import com.example.ticketbookingappandroidstudioproject.fragment.Fragment_product_admin;
import com.example.ticketbookingappandroidstudioproject.fragment.Fragment_screen_admin;
import com.example.ticketbookingappandroidstudioproject.fragment.Fragment_showtime_admin;
import com.example.ticketbookingappandroidstudioproject.model.Account;

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

        Account account = (Account) getIntent().getSerializableExtra("account");

        View headerCustomer = findViewById(R.id.headerLayoutCustomer);
        View bottomBarCustomer = findViewById(R.id.bottomBarCustomer);
        View bottomBarAdmin = findViewById(R.id.bottomBarAdmin);
        EditText editSearch = findViewById(R.id.edtSearch);
        TextView txtName = findViewById(R.id.txtUsername);

        txtName.setText(account.getFullName());

        if ("ADMIN".equals(userRole)) {

            headerCustomer.setVisibility(View.GONE);
            bottomBarCustomer.setVisibility(View.GONE);
            bottomBarAdmin.setVisibility(View.VISIBLE);
            editSearch.setVisibility(View.GONE);

            findViewById(R.id.tabMovie).setOnClickListener(v -> loadFragment(new Fragment_movie_admin()));
            findViewById(R.id.tabScreen).setOnClickListener(v -> loadFragment(new Fragment_screen_admin()));
            findViewById(R.id.tabAccount).setOnClickListener(v -> loadFragment(new Fragment_account_admin()));
            findViewById(R.id.tabProduct).setOnClickListener(v -> loadFragment(new Fragment_product_admin()));
            findViewById(R.id.tabOrder).setOnClickListener(v -> loadFragment(new Fragment_order_admin()));
            findViewById(R.id.tabShowtime).setOnClickListener(v -> loadFragment(new Fragment_showtime_admin()));

            loadFragment(new Fragment_movie_admin());

        } else {

            headerCustomer.setVisibility(View.VISIBLE);
            bottomBarCustomer.setVisibility(View.VISIBLE);
            bottomBarAdmin.setVisibility(View.GONE);

            findViewById(R.id.tabHome).setOnClickListener(v -> loadFragment(new Fragment_movie()));
            findViewById(R.id.tabProfile).setOnClickListener(v -> loadFragment(new Fragment_account()));

            loadFragment(new Fragment_movie());
        }
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
