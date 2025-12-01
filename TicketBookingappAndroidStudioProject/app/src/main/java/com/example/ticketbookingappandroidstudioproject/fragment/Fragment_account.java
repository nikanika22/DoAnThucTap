package com.example.ticketbookingappandroidstudioproject.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ticketbookingappandroidstudioproject.view.MyMovieAdapter;
import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.data.MoviesData;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_account extends Fragment {

    ListView listView;
    TextView txtAccountName, txtAccountEmail, txtAccountPhone, txtAccountRole;
    Account account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        txtAccountName = view.findViewById(R.id.txtAccountName);
        txtAccountEmail = view.findViewById(R.id.txtAccountEmail);
        txtAccountPhone = view.findViewById(R.id.txtAccountPhone);
        txtAccountRole = view.findViewById(R.id.txtAccountRole);
        listView = view.findViewById(R.id.listViewOrders);

        if (getActivity() != null) {
            account = (Account) getActivity().getIntent().getSerializableExtra("account");
            displayAccountInfo(account);
        }

        return view;
    }

    private void displayAccountInfo(Account account) {
        if (account != null) {
            txtAccountName.setText(account.getFullName() != null ? account.getFullName() : "N/A");
            txtAccountEmail.setText(account.getEmail() != null ? account.getEmail() : "N/A");
            txtAccountPhone.setText(account.getPhone() != null ? account.getPhone() : "N/A");
            txtAccountRole.setText(account.getRole() != null ? account.getRole() : "CUSTOMER");
        }
    }

}