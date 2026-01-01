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
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.ShowtimesData;
import com.example.ticketbookingappandroidstudioproject.model.ShowTime;
import com.example.ticketbookingappandroidstudioproject.view.AddShowtimeActivity;
import com.example.ticketbookingappandroidstudioproject.view.MyShowtimeAdapterAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_showtime_admin extends Fragment {

    ListView listView;
    MyShowtimeAdapterAdmin adapter;
    List<ShowTime> showtimeList;

    ImageButton btnSearch;
    EditText edtSearch;

    Button btnScheduled, btnOpen, btnClosed;

    FloatingActionButton btnAdd;

    private String currentStatusFilter = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showtime_admin, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        listView = view.findViewById(R.id.listViewShowtimes);

        btnScheduled = view.findViewById(R.id.btnScheduled);
        btnOpen = view.findViewById(R.id.btnOpen);
        btnClosed = view.findViewById(R.id.btnClosed);

        btnAdd = view.findViewById(R.id.btnAdd);

        showtimeList = new ArrayList<>();
        adapter = new MyShowtimeAdapterAdmin(getActivity(), R.layout.item_showtime_admin, showtimeList);
        listView.setAdapter(adapter);

        fetchShowtimes("", "");

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();
                fetchShowtimes(keyword, currentStatusFilter);
            }
        });

        View.OnClickListener statusClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "";
                if (v.getId() == R.id.btnScheduled) {
                    status = "SCHEDULED";
                } else if (v.getId() == R.id.btnOpen) {
                    status = "OPEN";
                } else if (v.getId() == R.id.btnClosed) {
                    status = "CLOSED";
                }
                currentStatusFilter = status;
                fetchShowtimes(edtSearch.getText().toString().trim(), status);
            }
        };

        btnScheduled.setOnClickListener(statusClickListener);
        btnOpen.setOnClickListener(statusClickListener);
        btnClosed.setOnClickListener(statusClickListener);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddShowtimeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchShowtimes(String keyword, String status) {
        Map<String, String> options = new HashMap<>();

        if (!keyword.isEmpty()) {
            options.put("keyword", keyword);
        }
        if (!status.isEmpty()) {
            options.put("status", status);
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.getShowtimes(bearerToken, options).enqueue(new Callback<ShowtimesData>() {
                @Override
                public void onResponse(Call<ShowtimesData> call, Response<ShowtimesData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ShowtimesData showtimesData = response.body();
                        showtimeList.clear();
                        if (showtimesData.isSuccess() && showtimesData.getData() != null && !showtimesData.getData().isEmpty()) {
                            showtimeList.addAll(showtimesData.getData());
                        } else {
                            Toast.makeText(getActivity(), "No showtimes found.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load showtimes: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ShowtimesData> call, Throwable t) {
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
        fetchShowtimes("", "");
    }
}
