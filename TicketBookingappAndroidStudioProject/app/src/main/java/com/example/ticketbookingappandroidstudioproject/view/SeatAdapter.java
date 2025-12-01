package com.example.ticketbookingappandroidstudioproject.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.model.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * 🎬 SeatAdapter - Quản lý hiển thị danh sách ghế trong RecyclerView
 *
 * Trách nhiệm:
 * 1. Chuyển đổi dữ liệu ghế thành các View trên màn hình
 * 2. Xử lý sự kiện khi người dùng click vào ghế
 * 3. Cập nhật giao diện dựa trên trạng thái ghế
 */
public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private List<Seat> seatList;              // Danh sách tất cả ghế
    private OnSeatClickListener listener;     // Callback để xử lý click

    /**
     * 📍 Interface OnSeatClickListener
     * Dùng để báo cho Activity khi ghế được click
     * Activity phải implement interface này
     */
    public interface OnSeatClickListener {
        void onSeatClick(Seat seat, int position);
    }

    /**
     * Constructor - Khởi tạo Adapter
     * @param seatList Danh sách ghế (có thể null)
     * @param listener Activity/Fragment xử lý sự kiện
     */
    public SeatAdapter(List<Seat> seatList, OnSeatClickListener listener) {
        // Nếu seatList null, tạo ArrayList rỗng để tránh crash
        this.seatList = seatList != null ? seatList : new ArrayList<>();
        this.listener = listener;
    }

    /**
     * ✏️ onCreateViewHolder - Tạo 1 item View (1 ghế)
     *
     * Gọi lần đầu khi RecyclerView cần hiển thị item mới
     * Nó sẽ:
     * 1. Load file XML (item_seat.xml) → Tạo View
     * 2. Wrap View vào SeatViewHolder
     * 3. Trả về SeatViewHolder
     */
    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo LayoutInflater từ Context
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Chuyển file XML thành Java Object (View)
        // R.layout.item_seat = file XML mô tả giao diện 1 ghế
        View itemView = inflater.inflate(R.layout.item_seat, parent, false);

        // Wrap View vào ViewHolder và trả về
        return new SeatViewHolder(itemView);
    }

    /**
     * 🔗 onBindViewHolder - Liên kết dữ liệu ghế với View
     *
     * Gọi khi cần cập nhật dữ liệu hiển thị trên item
     * Nó sẽ gọi bind() để cập nhật TextView, background, click listener
     */
    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        // Lấy ghế tại vị trí position
        Seat seat = seatList.get(position);

        // Gọi bind() để cập nhật View với dữ liệu ghế
        holder.bind(seat);
    }

    /**
     * 📊 getItemCount - Báo cho RecyclerView có bao nhiêu ghế
     * @return Số lượng ghế trong danh sách
     */
    @Override
    public int getItemCount() {
        return seatList.size();
    }

    /**
     * 🔄 updateSeats - Cập nhật danh sách ghế
     *
     * Dùng khi nhận dữ liệu mới từ API
     * Nó sẽ thay thế danh sách cũ và refresh UI
     */
    public void updateSeats(List<Seat> newSeats) {
        // Cập nhật danh sách
        this.seatList = newSeats != null ? newSeats : new ArrayList<>();

        // Thông báo cho RecyclerView rằng dữ liệu đã thay đổi
        // → RecyclerView sẽ refresh UI (gọi lại onBindViewHolder())
        notifyDataSetChanged();
    }

    // ================================ 🌟 ViewHolder ================================

    /**
     * 🎨 SeatViewHolder - Quản lý 1 item ghế
     *
     * Trách nhiệm:
     * 1. Giữ tham chiếu đến TextView
     * 2. Cập nhật giao diện (text, background, màu chữ)
     * 3. Xử lý sự kiện click
     */
    class SeatViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSeat;  // TextView hiển thị ghế (VD: "A1", "B5")

        /**
         * Constructor - Khởi tạo ViewHolder
         * @param itemView Layout của 1 item ghế
         */
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);

            // Tìm TextView trong item_seat.xml bằng ID
            tvSeat = itemView.findViewById(R.id.tvSeat);
        }

        /**
         * 📍 bind - Cập nhật UI cho 1 ghế (PHẦN QUAN TRỌNG NHẤT)
         *
         * Phương thức này sẽ:
         * 1. Hiển thị text ghế (VD: "A1")
         * 2. Set màu background dựa trên trạng thái
         * 3. Set màu chữ
         * 4. Thêm click listener để xử lý sự kiện
         */
        public void bind(Seat seat) {

            // ========== BƯỚC 1: Hiển thị text ghế ==========
            String seatLabel = seat.getSeatLabel();  // VD: "A1", "B5"
            tvSeat.setText(seatLabel);

            // ========== BƯỚC 2: Xác định màu dựa trên trạng thái ==========
            int backgroundDrawable = getBackgroundBySeatStatus(seat.getStatus());
            boolean isClickable = !seat.getStatus().equalsIgnoreCase("sold");

            // ========== BƯỚC 3: Áp dụng background ==========
            tvSeat.setBackgroundResource(backgroundDrawable);

            // ========== BƯỚC 4: Set màu chữ trắng ==========
            int whiteColor = ContextCompat.getColor(itemView.getContext(), android.R.color.white);
            tvSeat.setTextColor(whiteColor);

            // ========== BƯỚC 5: Enable/Disable click ==========
            tvSeat.setEnabled(isClickable);

            // ========== BƯỚC 6: Thêm click listener ==========
            setupClickListener(seat, isClickable);
        }

        /**
         * 🎨 getBackgroundBySeatStatus - Lấy drawable dựa trên trạng thái ghế
         *
         * @param status Trạng thái: "available", "selected", "sold"
         * @return ID của drawable tương ứng
         */
        private int getBackgroundBySeatStatus(String status) {
            // Chuyển status thành chữ thường để so sánh (VD: "Available" → "available")
            String statusLower = status.toLowerCase();

            // Kiểm tra trạng thái và trả về drawable tương ứng
            if (statusLower.equals("available")) {
                // Ghế còn trống → Xanh lá
                return R.drawable.seat_available;

            } else if (statusLower.equals("selected")) {
                // Đã được chọn → Xanh dương
                return R.drawable.seat_selected;

            } else if (statusLower.equals("sold")) {
                // Đã bán → Đỏ
                return R.drawable.seat_sold;

            } else {
                // Mặc định → Xanh lá
                return R.drawable.seat_available;
            }
        }

        /**
         * 🖱️ setupClickListener - Thiết lập sự kiện click
         *
         * Khi người dùng nhấn ghế:
         * 1. Kiểm tra ghế có phải "sold" không
         * 2. Nếu không phải "sold" → Gọi callback cho Activity
         */
        private void setupClickListener(Seat seat, boolean isClickable) {
            tvSeat.setOnClickListener(v -> {
                // Kiểm tra điều kiện:
                // 1. listener không null (Activity phải implement)
                // 2. Ghế không phải "sold" (ghế đã bán không được click)
                if (listener != null && isClickable) {

                    // Gọi callback → Activity xử lý
                    listener.onSeatClick(seat, getAdapterPosition());
                }
            });
        }
    }
}

