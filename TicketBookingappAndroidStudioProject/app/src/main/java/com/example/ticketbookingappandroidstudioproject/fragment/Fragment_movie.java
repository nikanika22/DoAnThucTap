package com.example.ticketbookingappandroidstudioproject.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ticketbookingappandroidstudioproject.view.MyMovieAdapter;
import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.data.MoviesData;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.model.Movie;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_movie,container,false);
        listView=view.findViewById(R.id.listViewMovies);
        movieList=new ArrayList<>();


        adapter=new MyMovieAdapter(getActivity(),R.layout.item_movie,movieList);
        listView.setAdapter(adapter);

        fetchMovies(new HashMap<>());

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