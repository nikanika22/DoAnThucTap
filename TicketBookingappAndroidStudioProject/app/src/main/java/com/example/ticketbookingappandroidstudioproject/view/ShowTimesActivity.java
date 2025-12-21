package com.example.ticketbookingappandroidstudioproject.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticketbookingappandroidstudioproject.adapter.ShowtimeAdapter;
import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.data.ShowTimesData;
import com.example.ticketbookingappandroidstudioproject.model.ShowTime;
import com.example.ticketbookingappandroidstudioproject.model.ShowTimeItems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowTimesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ShowtimeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_times);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewShowtimes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShowtimeAdapter(this);
        recyclerView.setAdapter(adapter);
        loadShowTimes(movieId);
    }

    private void loadShowTimes(int movieId) {
        Toast.makeText(this, "Dang tải...", Toast.LENGTH_SHORT).show();
        ApiService.apiService.getShowtimesByMovieId(movieId).enqueue(new Callback<ShowTimesData>() {
            @Override
            public void onResponse(Call<ShowTimesData> call, Response<ShowTimesData> response) {
                 if(response.isSuccessful() && response.body()!=null)
                 {
                     List<ShowTime> showTimes =response.body().getShowtimes();
                      if(showTimes!=null && !showTimes.isEmpty())
                      {
                          List<ShowTimeItems> items = convertShowTimesWithHeader(showTimes);
                          adapter.updateData(items);
                      }
                      else
                      {
                          Toast.makeText(ShowTimesActivity.this, "No showtimes found.", Toast.LENGTH_SHORT).show();
                      }
                 }
                 else
                 {
                     Toast.makeText(ShowTimesActivity.this, "Failed to load showtimes: " + response.message(), Toast.LENGTH_SHORT);
                 }
            }

            @Override
            public void onFailure(Call<ShowTimesData> call, Throwable t) {
                Toast.makeText(ShowTimesActivity.this,"Lỗi kết nối mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * Chuyển đổi List<ShowTime> → List<ShowtimeItem> (có header)
     * Logic đơn giản: Nhóm theo ngày, chèn header
     */
    private List<ShowTimeItems> convertShowTimesWithHeader(List<ShowTime> showTimes) {
        List<ShowTimeItems> items=new ArrayList<>();
        //map để nhóm theo ngày
        Map<String,List<ShowTime>> grouped=new LinkedHashMap<>();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        //Nhom theo ngày
        for(ShowTime showTime:showTimes)
        {
            String date=showTime.getStartAt().substring(0,10);
            if(!grouped.containsKey(date))
            {
                grouped.put(date,new ArrayList<>());
            }
            grouped.get(date).add(showTime);

        }
        SimpleDateFormat headerFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));

        for (Map.Entry<String, List<ShowTime>> entry : grouped.entrySet()) {
            String date = entry.getKey();
            List<ShowTime> showtimesInDate = entry.getValue();

            // Thêm HEADER
            try {
                Date dateObj = dateFormat.parse(date);
                String headerText = headerFormat.format(dateObj).toLowerCase();
                items.add(ShowTimeItems.createHeader(headerText));
            } catch (Exception e) {
                items.add(ShowTimeItems.createHeader(date));
            }

            // Thêm SHOWTIMES
            for (ShowTime showtime : showtimesInDate) {
                items.add(ShowTimeItems.createShowtime(showtime));
            }
        }

        return items;

    }
}