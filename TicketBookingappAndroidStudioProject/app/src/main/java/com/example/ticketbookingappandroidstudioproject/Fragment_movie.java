package com.example.ticketbookingappandroidstudioproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_movie extends Fragment {

    ListView listView;
    MyMovieAdapter adapter;
    List<Movie> movieList; // Danh sách phim
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_movie,container,false);
        listView=view.findViewById(R.id.listViewMovies);
        movieList=new ArrayList<>();
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("bad_boys", R.drawable.bad_boys,"Hành động, Siêu anh hùng","125 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("bad_boys", R.drawable.bad_boys,"Hành động, Siêu anh hùng","125 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("bad_boys", R.drawable.bad_boys,"Hành động, Siêu anh hùng","125 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18"));
        movieList.add(new Movie("bad_boys", R.drawable.bad_boys,"Hành động, Siêu anh hùng","125 phút","C18"));
        adapter=new MyMovieAdapter(getActivity(),R.layout.item_movie,movieList);
        listView.setAdapter(adapter);
        return view;
    }
}