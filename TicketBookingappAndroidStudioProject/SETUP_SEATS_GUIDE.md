# 📖 Hướng dẫn Set Cứng Số Ghế & Kết nối API

## 🎯 Hiện tại: Set Cứng Số Ghế (Hardcoded)

Phần này giải thích cách số ghế được tạo cứng trong code hiện tại, và cách chỉnh sửa nếu cần.

---

## 1️⃣ Cấu trúc Seat (Ghế) hiện tại

### File: `model/Seat.java`
```
Seat {
  id: int                    // ID ghế duy nhất
  screenId: int              // ID màn chiếu (phòng chiếu)
  rowLabel: String           // Hàng ghế: "A", "B", "C", ... (max 10 hàng)
  seatNumber: int            // Số thứ tự: 1, 2, 3, ... (max 8 ghế/hàng)
  seatType: String           // Loại ghế: "STANDARD" | "VIP" | "COUPLE" | "ACCESSIBLE"
  isAisle: boolean           // Có phải ghế lối đi?
  isBlocked: boolean         // Ghế bị khóa (không được chọn)?
  
  // Dùng cho UI
  status: String             // "available" | "selected" | "sold" | "blocked"
  price: double              // Giá vé (VNĐ)
}
```

---

## 2️⃣ Cách Set Cứng Số Ghế hiện tại (loadSampleSeats)

### File: `SeatSelectionActivity.java` - Method `loadSampleSeats()`

**Cấu trúc ghế:**
- ✅ **10 hàng**: A, B, C, D, E, F, G, H, I, J
- ✅ **8 ghế/hàng**: 1, 2, 3, 4, 5, 6, 7, 8
- ✅ **Tổng cộng**: 80 ghế

**Chia giá:**
- 💰 Hàng A, B, C, G, H, I, J: **75,000 VNĐ** (Standard)
- 💰 Hàng D, E, F: **100,000 VNĐ** (VIP)

**Ghế đã bán:**
- ❌ Hàng E, ghế 4, 5
- ❌ Hàng F, ghế 3

---

## 3️⃣ Cách Chỉnh Sửa Số Ghế (Set Cứng)

### ✏️ Nếu muốn thay đổi số hàng & số ghế:

**File:** `SeatSelectionActivity.java`

```java
// Dòng 26
private static final int SEATS_PER_ROW = 8; // ← Thay đổi số ghế/hàng (vd: 10)
```

**Dòng 102-110** - Thay đổi mảng hàng:
```java
String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}; // ← Thêm/bớt hàng
```

**Ví dụ: Nếu muốn 6 hàng × 10 ghế/hàng:**
```java
private static final int SEATS_PER_ROW = 10;

// ...

String[] rows = {"A", "B", "C", "D", "E", "F"}; // 6 hàng
```

### ✏️ Nếu muốn thay đổi giá ghế VIP:

**Dòng 113-116:**
```java
// Ghế VIP (hàng D, E, F)
if (row.equals("D") || row.equals("E") || row.equals("F")) {
    price = 100000; // ← Thay đổi giá ở đây (hoặc thêm hàng khác)
}
```

**Ví dụ: Làm hàng G cũng là VIP:**
```java
if (row.equals("D") || row.equals("E") || row.equals("F") || row.equals("G")) {
    price = 100000;
}
```

### ✏️ Nếu muốn thay đổi ghế đã bán:

**Dòng 119-123:**
```java
// Một số ghế đã bán (mẫu)
if ((row.equals("E") && (number == 4 || number == 5)) ||
    (row.equals("F") && number == 3)) {
    status = "sold"; // ← Thêm/xóa các ghế đã bán ở đây
}
```

**Ví dụ: Thêm ghế đã bán:**
```java
if ((row.equals("E") && (number == 4 || number == 5)) ||
    (row.equals("F") && (number == 3 || number == 6)) ||
    (row.equals("D") && number == 1)) {
    status = "sold";
}
```

---

## 4️⃣ Cách Kết Nối API (Sau này)

### Khi nào dùng API?
Khi muốn **lấy dữ liệu ghế từ server** thay vì hardcoded.

### 📋 Cấu trúc API Response (mẫu):
```json
{
  "success": true,
  "data": {
    "showId": 1,
    "movieTitle": "Bad Boys: Ride or Die",
    "showTime": "19:30 - 30/11/2025",
    "roomName": "A1",
    "seats": [
      {
        "id": 1,
        "rowLabel": "A",
        "seatNumber": 1,
        "seatType": "STANDARD",
        "status": "available",
        "price": 75000
      },
      {
        "id": 2,
        "rowLabel": "A",
        "seatNumber": 2,
        "seatType": "STANDARD",
        "status": "sold",
        "price": 75000
      },
      // ... more seats
    ]
  }
}
```

### 🔄 Các bước thêm API:

#### **Bước 1:** Tạo Retrofit API Service
```java
public interface SeatApi {
    @GET("api/shows/{showId}/seats")
    Call<SeatResponse> getSeats(@Path("showId") int showId);
}
```

#### **Bước 2:** Thay thế `loadSampleSeats()` bằng `loadSeatsFromAPI()`
```java
private void loadSeatsFromAPI(int showId) {
    SeatApi api = RetrofitClient.getClient().create(SeatApi.class);
    Call<SeatResponse> call = api.getSeats(showId);
    
    call.enqueue(new Callback<SeatResponse>() {
        @Override
        public void onResponse(Call<SeatResponse> call, Response<SeatResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                allSeats = response.body().getSeats();
                seatAdapter.notifyDataSetChanged();
            }
        }
        
        @Override
        public void onFailure(Call<SeatResponse> call, Throwable t) {
            // Fallback: load sample seats nếu API fail
            loadSampleSeats();
        }
    });
}
```

#### **Bước 3:** Gọi API thay vì hardcode
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_seat_selection);
    
    initViews();
    setupToolbar();
    
    // Lấy showId từ Intent
    int showId = getIntent().getIntExtra("SHOW_ID", 1);
    loadSeatsFromAPI(showId); // ← Thay vì loadSampleSeats()
    
    setupRecyclerView();
    setupButtons();
}
```

---

## 5️⃣ So sánh: Set Cứng vs API

| Tính năng | Set Cứng | API |
|-----------|----------|-----|
| **Cập nhật dữ liệu** | ❌ Chỉnh code, rebuild | ✅ Server thay đổi, tự cập nhật |
| **Ghế bị bán** | ❌ Cần thay code | ✅ Server quản lý realtime |
| **Giá ghế** | ❌ Cứng trong code | ✅ Server linh hoạt |
| **Dev nhanh** | ✅ Nhanh test UI | ❌ Cần server sẵn sàng |
| **Production** | ❌ Không khả thi | ✅ Bắt buộc |

---

## 📌 Tóm tắt

✅ **Hiện tại (Set Cứng):**
- Ghế được tạo trong `loadSampleSeats()` 
- Cấu trúc: **10 hàng × 8 ghế**
- Giá: Standard 75k, VIP 100k
- Để chỉnh: Edit `SeatSelectionActivity.java` dòng 102-123

🚀 **Sau này (API):**
- Thay `loadSampleSeats()` bằng `loadSeatsFromAPI(showId)`
- Server trả về JSON với danh sách ghế
- Fallback về set cứng nếu API fail

---

## 🔗 File liên quan
- `SeatSelectionActivity.java` - Quản lý ghế
- `SeatAdapter.java` - Hiển thị ghế
- `model/Seat.java` - Model dữ liệu ghế
- `activity_seat_selection.xml` - Layout giao diện

