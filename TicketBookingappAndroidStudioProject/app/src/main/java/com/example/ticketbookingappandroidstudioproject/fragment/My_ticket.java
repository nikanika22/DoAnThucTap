package com.example.ticketbookingappandroidstudioproject.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.MyTicketsResponse;
import com.example.ticketbookingappandroidstudioproject.model.MyTicket;
import com.example.ticketbookingappandroidstudioproject.view.TicketAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_ticket extends Fragment {

    ListView listView;
    TicketAdapter adapter;
    List<MyTicket> ticketList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_my_ticket, container, false);

        // Setup ListView
        listView = view.findViewById(R.id.ListViewTicket);
        ticketList = new ArrayList<>();

        // Khởi tạo adapter
        adapter = new TicketAdapter(getActivity(), R.layout.item_ticket, ticketList);
        listView.setAdapter(adapter);

        // Load data
        loadMyTickets();

        return view;
    }

    private void loadMyTickets() {
        // 1. Lấy token từ SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String token = "Bearer " + prefs.getString("auth_token", "");

        // 2. Gọi API
        ApiService.apiService.getMyTickets(token).enqueue(new Callback<MyTicketsResponse>() {
            @Override
            public void onResponse(Call<MyTicketsResponse> call, Response<MyTicketsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MyTicketsResponse ticketsResponse = response.body();

                    if (ticketsResponse.isSuccess()) {
                        // 3. Clear và add data
                        ticketList.clear();
                        if (ticketsResponse.getData() != null && !ticketsResponse.getData().isEmpty()) {
                            // Tách thành từng ghế riêng
                            List<MyTicket> flattened = flattenTicketsBySeats(ticketsResponse.getData());
                            ticketList.addAll(flattened);
                        } else {
                            Toast.makeText(getActivity(), "Không có vé", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Lỗi khi tải vé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyTicketsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private List<MyTicket> flattenTicketsBySeats(List<MyTicket> orders) {
        List<MyTicket> result = new ArrayList<>();

        for (MyTicket order : orders) {
            // Với mỗi ghế, tạo 1 MyTicket mới
            for (MyTicket.TicketLine line : order.getOrderLines()) {
                if (line.getItemType().equals("TICKET") && line.getSeat() != null) {
                    // Clone order nhưng chỉ giữ 1 ghế
                    MyTicket singleSeatTicket = new MyTicket();
                    singleSeatTicket.setShowtime(order.getShowtime());
                    singleSeatTicket.setStatus(order.getStatus());

                    // Tạo list chỉ có 1 ghế này
                    List<MyTicket.TicketLine> singleLine = new ArrayList<>();
                    singleLine.add(line);
                    singleSeatTicket.setOrderLines(singleLine);

                    result.add(singleSeatTicket);
                }
            }
        }

        return result;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Refresh data khi quay lại fragment
        loadMyTickets();
    }
}