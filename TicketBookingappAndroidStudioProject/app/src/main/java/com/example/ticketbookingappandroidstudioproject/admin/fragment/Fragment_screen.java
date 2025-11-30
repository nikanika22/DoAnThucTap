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
import com.example.ticketbookingappandroidstudioproject.admin.activity.AddScreenActivity;
import com.example.ticketbookingappandroidstudioproject.admin.adapter.MyScreenAdapter;
import com.example.ticketbookingappandroidstudioproject.admin.data.ScreensData;
import com.example.ticketbookingappandroidstudioproject.admin.model.Screen;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_screen extends Fragment {

    ListView listView;
    MyScreenAdapter adapter;
    List<Screen> ScreenList;

    ImageButton btnSearch;
    EditText edtSearch;

    Button btn2D, btn3D, btnImax, btnScreenX, btn4DX;

    FloatingActionButton btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen_admin, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        listView = view.findViewById(R.id.listViewScreens);
        btnAdd = view.findViewById(R.id.btnAdd);

        btn2D = view.findViewById(R.id.loai2d);
        btn3D = view.findViewById(R.id.loai3d);
        btnImax = view.findViewById(R.id.loaiimax);
        btnScreenX = view.findViewById(R.id.loaiscreenx);
        btn4DX = view.findViewById(R.id.loai4dx);

        ScreenList = new ArrayList<>();
        adapter = new MyScreenAdapter(getActivity(), R.layout.item_screen_admin, ScreenList);
        listView.setAdapter(adapter);

        fetchScreens(new HashMap<>());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Query = edtSearch.getText().toString().trim();
                Map<String, String> options = new HashMap<>();
                if (!Query.isEmpty()) {
                    options.put("name", Query);
                }
                fetchScreens(options);
            }
        });
        View.OnClickListener genreClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String format = clickedButton.getText().toString();
                Map<String, String> options = new HashMap<>();
                options.put("format", format);
                fetchScreens(options);
            }
        };
        btn2D.setOnClickListener(genreClickListener);
        btn3D.setOnClickListener(genreClickListener);
        btnImax.setOnClickListener(genreClickListener);
        btnScreenX.setOnClickListener(genreClickListener);
        btn4DX.setOnClickListener(genreClickListener);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddScreenActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchScreens(Map<String, String> options) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.getScreens(bearerToken, options).enqueue(new Callback<ScreensData>() {
                @Override
                public void onResponse(Call<ScreensData> call, Response<ScreensData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ScreensData ScreensData = response.body();
                        ScreenList.clear();
                        if (ScreensData.isSuccess() && ScreensData.getData() != null && !ScreensData.getData().isEmpty()) {
                            ScreenList.addAll(ScreensData.getData());
                        } else {
                            Toast.makeText(getActivity(), "No Screens found.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load Screens: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ScreensData> call, Throwable t) {
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
        fetchScreens(new HashMap<>());
    }
}
