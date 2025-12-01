package com.example.ticketbookingappandroidstudioproject.admin.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.admin.activity.AddAccountActivity;
import com.example.ticketbookingappandroidstudioproject.admin.adapter.MyAccountAdapter;
import com.example.ticketbookingappandroidstudioproject.admin.data.AccountsData;
import com.example.ticketbookingappandroidstudioproject.admin.model.Account;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_account extends Fragment {

    ListView listView;
    MyAccountAdapter adapter;
    List<Account> AccountList;

    ImageButton btnSearch;
    EditText edtSearch;

    Button btn2D, btn3D, btnImax, btnAccountX, btn4DX;

    FloatingActionButton btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_admin, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        listView = view.findViewById(R.id.listViewAccounts);
        btnAdd = view.findViewById(R.id.btnAdd);

        btn2D = view.findViewById(R.id.loai2d);
        btn3D = view.findViewById(R.id.loai3d);
        btnImax = view.findViewById(R.id.loaiimax);
        btnAccountX = view.findViewById(R.id.loaiAccountx);
        btn4DX = view.findViewById(R.id.loai4dx);

        AccountList = new ArrayList<>();
        adapter = new MyAccountAdapter(getActivity(), R.layout.item_account_admin, AccountList);
        listView.setAdapter(adapter);

        fetchAccounts(new HashMap<>());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Query = edtSearch.getText().toString().trim();
                Map<String, String> options = new HashMap<>();
                if (!Query.isEmpty()) {
                    options.put("full_name", Query);
                }
                fetchAccounts(options);
            }
        });
        View.OnClickListener genreClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String name = clickedButton.getText().toString();
                Map<String, String> options = new HashMap<>();
                options.put("full_name", name);
                fetchAccounts(options);
            }
        };
        btn2D.setOnClickListener(genreClickListener);
        btn3D.setOnClickListener(genreClickListener);
        btnImax.setOnClickListener(genreClickListener);
        btnAccountX.setOnClickListener(genreClickListener);
        btn4DX.setOnClickListener(genreClickListener);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddAccountActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchAccounts(Map<String, String> options) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.getAccounts(bearerToken, options).enqueue(new Callback<AccountsData>() {
                @Override
                public void onResponse(Call<AccountsData> call, Response<AccountsData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AccountsData AccountsData = response.body();
                        AccountList.clear();
                        if (AccountsData.isSuccess() && AccountsData.getData() != null && !AccountsData.getData().isEmpty()) {
                            AccountList.addAll(AccountsData.getData());
                        } else {
                            Toast.makeText(getActivity(), "No Accounts found.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load Accounts: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<AccountsData> call, Throwable t) {
                    Toast.makeText(getActivity(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAccounts(new HashMap<>());
    }
}
