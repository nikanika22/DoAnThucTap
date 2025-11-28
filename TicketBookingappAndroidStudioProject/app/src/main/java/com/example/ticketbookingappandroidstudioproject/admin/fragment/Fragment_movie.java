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
import com.example.ticketbookingappandroidstudioproject.admin.activity.AddMovieActivity;
import com.example.ticketbookingappandroidstudioproject.admin.adapter.MyMovieAdapter;
import com.example.ticketbookingappandroidstudioproject.admin.data.MoviesData;
import com.example.ticketbookingappandroidstudioproject.admin.model.Movie;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_movie extends Fragment {

    ListView listView;
    MyMovieAdapter adapter;
    List<Movie> movieList;

    ImageButton btnSearch;
    EditText edtSearch;

    Button btnHonor, btnAction, btnAdventure, btnSciFi;

    FloatingActionButton btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_admin, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        listView = view.findViewById(R.id.listViewMovies);

        btnHonor = view.findViewById(R.id.honor);
        btnAction = view.findViewById(R.id.action);
        btnAdventure = view.findViewById(R.id.adventur);
        btnSciFi = view.findViewById(R.id.sci_Fi);
        btnAdd = view.findViewById(R.id.btnAdd);

        movieList = new ArrayList<>();
        adapter = new MyMovieAdapter(getActivity(), R.layout.item_movie_admin, movieList);
        listView.setAdapter(adapter);

        fetchMovies(new HashMap<>());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleQuery = edtSearch.getText().toString().trim();
                Map<String, String> options = new HashMap<>();
                if (!titleQuery.isEmpty()) {
                    options.put("title", titleQuery);
                }
                fetchMovies(options);
            }
        });
        View.OnClickListener genreClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String genre = clickedButton.getText().toString();
                Map<String, String> options = new HashMap<>();
                options.put("genre", genre);
                fetchMovies(options);
            }
        };

        btnHonor.setOnClickListener(genreClickListener);
        btnAction.setOnClickListener(genreClickListener);
        btnAdventure.setOnClickListener(genreClickListener);
        btnSciFi.setOnClickListener(genreClickListener);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMovieActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchMovies(Map<String, String> options) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.getMovies(bearerToken, options).enqueue(new Callback<MoviesData>() {
                @Override
                public void onResponse(Call<MoviesData> call, Response<MoviesData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        MoviesData moviesData = response.body();
                        movieList.clear();
                        if (moviesData.isSuccess() && moviesData.getData() != null && !moviesData.getData().isEmpty()) {
                            movieList.addAll(moviesData.getData());
                        } else {
                            Toast.makeText(getActivity(), "No movies found.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load movies: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MoviesData> call, Throwable t) {
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
        fetchMovies(new HashMap<>());
    }
}
