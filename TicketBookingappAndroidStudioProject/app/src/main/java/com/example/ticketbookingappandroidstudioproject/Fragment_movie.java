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

        // Thêm phim với ID để có thể truyền sang màn hình chọn ghế
        Movie movie1 = new Movie("Atlas", R.drawable.atlas,"Hành động, Phiêu lưu","150 phút","C18");
        movie1.setId(1);
        movieList.add(movie1);

        Movie movie2 = new Movie("Bad Boys: Ride or Die", R.drawable.bad_boys,"Hành động, Hài","125 phút","C16");
        movie2.setId(2);
        movieList.add(movie2);

        Movie movie3 = new Movie("Dune: Part Two", R.drawable.dune_part_two,"Khoa học viễn tưởng","166 phút","C13");
        movie3.setId(3);
        movieList.add(movie3);

        Movie movie4 = new Movie("Mad Max", R.drawable.madmax,"Hành động, Phiêu lưu","120 phút","C18");
        movie4.setId(4);
        movieList.add(movie4);

        Movie movie5 = new Movie("Ordinary Angels", R.drawable.ordinary_angels,"Chính kịch","118 phút","C13");
        movie5.setId(5);
        movieList.add(movie5);

        Movie movie6 = new Movie("Fly Me to the Moon", R.drawable.fly_me_to_the_moon,"Hài, Lãng mạn","132 phút","C13");
        movie6.setId(6);
        movieList.add(movie6);

        adapter=new MyMovieAdapter(getActivity(),R.layout.item_movie,movieList);
        listView.setAdapter(adapter);
        return view;
    }
}