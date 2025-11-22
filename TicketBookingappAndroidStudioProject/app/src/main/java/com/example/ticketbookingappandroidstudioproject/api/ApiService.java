package com.example.ticketbookingappandroidstudioproject.api;

import com.example.ticketbookingappandroidstudioproject.admin.data.MoviesData;
import com.example.ticketbookingappandroidstudioproject.model.LoginData;
import com.example.ticketbookingappandroidstudioproject.model.LoginRequest;
import com.example.ticketbookingappandroidstudioproject.model.RegisterData;
import com.example.ticketbookingappandroidstudioproject.model.RegisterRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    //http://127.0.0.1:8000/api/login
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService= new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("login")
    Call<LoginData> sendLoginRequest(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegisterData> sendRegisterRequest(@Body RegisterRequest registerRequest);

    @GET("movies")
    Call<MoviesData> getMovies(@Header("Authorization") String authToken);
}
