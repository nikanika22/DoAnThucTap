package com.example.ticketbookingappandroidstudioproject.fragment;

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
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.ProductsData;
import com.example.ticketbookingappandroidstudioproject.model.Product;
import com.example.ticketbookingappandroidstudioproject.view.AddProductActivity;
import com.example.ticketbookingappandroidstudioproject.view.MyProductAdapterAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_product_admin extends Fragment {

    ListView listView;
    MyProductAdapterAdmin adapter;
    List<Product> ProductList;

    ImageButton btnSearch;
    EditText edtSearch;
    

    FloatingActionButton btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_admin, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        listView = view.findViewById(R.id.listViewProducts);

        btnAdd = view.findViewById(R.id.btnAdd);

        ProductList = new ArrayList<>();
        adapter = new MyProductAdapterAdmin(getActivity(), R.layout.item_product_admin, ProductList);
        listView.setAdapter(adapter);

        fetchProducts(new HashMap<>());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameQuery = edtSearch.getText().toString().trim();
                Map<String, String> options = new HashMap<>();
                if (!nameQuery.isEmpty()) {
                    options.put("name", nameQuery);
                }
                fetchProducts(options);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchProducts(Map<String, String> options) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.getProducts(bearerToken, options).enqueue(new Callback<ProductsData>() {
                @Override
                public void onResponse(Call<ProductsData> call, Response<ProductsData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ProductsData ProductsData = response.body();
                        ProductList.clear();
                        if (ProductsData.isSuccess() && ProductsData.getData() != null && !ProductsData.getData().isEmpty()) {
                            ProductList.addAll(ProductsData.getData());
                        } else {
                            Toast.makeText(getActivity(), "No Products found.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load Products: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ProductsData> call, Throwable t) {
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
        fetchProducts(new HashMap<>());
    }
}
