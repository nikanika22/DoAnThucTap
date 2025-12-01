package com.example.ticketbookingappandroidstudioproject.api;

import com.example.ticketbookingappandroidstudioproject.data.AccountsData;
import com.example.ticketbookingappandroidstudioproject.data.ApiResponse;
import com.example.ticketbookingappandroidstudioproject.data.MoviesData;
import com.example.ticketbookingappandroidstudioproject.data.ScreensData;
import com.example.ticketbookingappandroidstudioproject.model.Account;
import com.example.ticketbookingappandroidstudioproject.model.Movie;
import com.example.ticketbookingappandroidstudioproject.model.Screen;
import com.example.ticketbookingappandroidstudioproject.model.LoginData;
import com.example.ticketbookingappandroidstudioproject.model.LoginRequest;
import com.example.ticketbookingappandroidstudioproject.model.RegisterData;
import com.example.ticketbookingappandroidstudioproject.model.RegisterRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiService {

    //http://127.0.0.1:8000/api/login
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("login")
    Call<LoginData> sendLoginRequest(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegisterData> sendRegisterRequest(@Body RegisterRequest registerRequest);

    @GET("/info")
    Call<ApiResponse> getInfo(@Header("Authorization") String authToken);

    @GET("movies")
    Call<MoviesData> getMovies(@Header("Authorization") String authToken, @QueryMap Map<String, String> options);

    @POST("movies")
    Call<ApiResponse> addMovie(@Header("Authorization") String authToken, @Body Movie movie);

    @DELETE("movies/{id}")
    Call<Void> deleteMovie(@Header("Authorization") String authToken, @Path("id") int movieId);

    @PUT("movies/{id}")
    Call<ApiResponse> updateMovie(@Header("Authorization") String authToken, @Path("id") int movieId, @Body Movie movie);

    @DELETE("screens/{id}")
    Call<Void> deleteScreen(@Header("Authorization") String authToken, @Path("id") int screenId);

    @PUT("screens/{id}")
    Call<ApiResponse> updateScreen(@Header("Authorization") String authToken, @Path("id") int screenId, @Body Screen screen);

    @GET("screens")
    Call<ScreensData> getScreens(@Header("Authorization") String authToken, @QueryMap Map<String, String> options);

    @POST("screens")
    Call<ApiResponse> addScreen(@Header("Authorization") String authToken, @Body Screen screen);

    @DELETE("accounts/{id}")
    Call<Void> deleteAccount(@Header("Authorization") String authToken, @Path("id") int accountId);

    @PUT("accounts/{id}")
    Call<ApiResponse> updateAccount(@Header("Authorization") String authToken, @Path("id") int accountId, @Body Account account);

    @GET("accounts")
    Call<AccountsData> getAccounts(@Header("Authorization") String authToken, @QueryMap Map<String, String> options);

    @POST("accounts")
    Call<ApiResponse> addAccount(@Header("Authorization") String authToken, @Body Account account);

}
