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
import com.example.ticketbookingappandroidstudioproject.data.OrdersData;
import com.example.ticketbookingappandroidstudioproject.model.Order;
import com.example.ticketbookingappandroidstudioproject.view.MyOrderAdapterAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_order_admin extends Fragment {

    ListView listView;
    MyOrderAdapterAdmin adapter;
    List<Order> OrderList;

    ImageButton btnSearch;
    EditText edtSearch;

    Button init, paid, cancelled;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_admin, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        listView = view.findViewById(R.id.listViewOrders);

        init = view.findViewById(R.id.btnFilterInit);
        paid = view.findViewById(R.id.btnFilterPaid);
        cancelled = view.findViewById(R.id.btnFilterCancelled);

        OrderList = new ArrayList<>();
        adapter = new MyOrderAdapterAdmin(getActivity(), R.layout.item_order_admin, OrderList);
        listView.setAdapter(adapter);

        fetchOrders("");

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();
                fetchOrders(keyword);
            }
        });

        View.OnClickListener statusFilter = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String status = clickedButton.getText().toString();
                fetchOrders(status);
            }
        };

        init.setOnClickListener(statusFilter);
        paid.setOnClickListener(statusFilter);
        cancelled.setOnClickListener(statusFilter);

        return view;
    }

    private void fetchOrders(String keyword) {
        Map<String, String> options = new HashMap<>();

        if (!keyword.isEmpty()) {
            options.put("keyword", keyword);
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.getAllOrders(bearerToken, options).enqueue(new Callback<OrdersData>() {
                @Override
                public void onResponse(Call<OrdersData> call, Response<OrdersData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        OrdersData OrdersData = response.body();
                        OrderList.clear();
                        if (OrdersData.isSuccess() && OrdersData.getData() != null && !OrdersData.getData().isEmpty()) {
                            OrderList.addAll(OrdersData.getData());
                        } else {
                            Toast.makeText(getActivity(), "No Orders found.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load Orders: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<OrdersData> call, Throwable t) {
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
        fetchOrders("");
    }
}
