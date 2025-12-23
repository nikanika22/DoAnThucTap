package com.example.ticketbookingappandroidstudioproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.ticketbookingappandroidstudioproject.view.AddAccountActivity;
import com.example.ticketbookingappandroidstudioproject.view.MyAccountAdapterAdmin;
import com.example.ticketbookingappandroidstudioproject.data.AccountsData;
import com.example.ticketbookingappandroidstudioproject.model.Account;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_account_admin extends Fragment {

    ListView listView;
    MyAccountAdapterAdmin adapter;
    List<Account> AccountList;

    ImageButton btnSearch;
    EditText edtSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_admin, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        listView = view.findViewById(R.id.listViewAccounts);

        AccountList = new ArrayList<>();
        adapter = new MyAccountAdapterAdmin(getActivity(), R.layout.item_account_admin, AccountList);
        listView.setAdapter(adapter);

        fetchAccounts("");

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();
                fetchAccounts(keyword);
            }
        });

        return view;
    }

    private void fetchAccounts(String keyword) {
        Map<String, String> options = new HashMap<>();

        if (!keyword.isEmpty()) {
            options.put("keyword", keyword);
        }

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
        fetchAccounts("");
    }
}
