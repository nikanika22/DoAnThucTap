# 🎯 GIẢI THÍCH CHI TIẾT: CÁCH HOẠT ĐỘNG CODE CHỌN GHẾ

## 📚 MỤC LỤC
1. [Luồng hoạt động tổng thể](#1-luồng-hoạt-động-tổng-thể)
2. [MyMovieAdapter - Nút "Đặt vé"](#2-mymovieadapter---nút-đặt-vé)
3. [SeatSelectionActivity - Màn hình chính](#3-seatselectionactivity---màn-hình-chính)
4. [SeatAdapter - Hiển thị ghế](#4-seatadapter---hiển-thị-ghế)
5. [Cơ chế chọn ghế](#5-cơ-chế-chọn-ghế)
6. [Tính năng suất chiếu](#6-tính-năng-suất-chiếu)
7. [Tính tổng tiền](#7-tính-tổng-tiền)

---

## 1. LUỒNG HOẠT ĐỘNG TỔNG THỂ

```
┌─────────────────────┐
│  Fragment_movie     │  Danh sách phim (6 phim hardcode)
│  (ListView)         │
└──────────┬──────────┘
           │
           │ Click nút "Đặt vé"
           ▼
┌─────────────────────┐
│  MyMovieAdapter     │  Xử lý sự kiện click
│  (getView)          │  → Tạo Intent với thông tin phim
└──────────┬──────────┘
           │
           │ startActivity(intent)
           ▼
┌─────────────────────┐
│ SeatSelection       │  Nhận Intent
│ Activity            │  → Load 80 ghế (generate)
│ (onCreate)          │  → Setup RecyclerView
└──────────┬──────────┘
           │
           │ Hiển thị lưới ghế
           ▼
┌─────────────────────┐
│  SeatAdapter        │  Render từng ghế
│  (RecyclerView)     │  → Màu theo trạng thái
│                     │  → Xử lý click
└──────────┬──────────┘
           │
           │ Click vào ghế
           ▼
┌─────────────────────┐
│ onSeatClick()       │  Toggle trạng thái
│                     │  → Update UI
│                     │  → Tính tổng tiền
└─────────────────────┘
```

---

## 2. MyMovieAdapter - NÚT "ĐẶT VÉ"

### 📍 File: `MyMovieAdapter.java`

### 🎯 Nhiệm vụ:
Hiển thị danh sách phim và xử lý khi user click "Đặt vé"

### 💻 Code giải thích:

```java
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    // BƯỚC 1: Tạo view cho 1 item phim
    LayoutInflater inflater = this.context.getLayoutInflater();
    View item = inflater.inflate(this.resource, null);  // Load layout item_movie.xml
    
    // BƯỚC 2: Tìm các view con
    ImageView imageView = item.findViewById(R.id.imgPoster);     // Poster phim
    TextView txtTitle = item.findViewById(R.id.txtTitle);        // Tên phim
    TextView txtGenre = item.findViewById(R.id.txtGenre);        // Thể loại
    TextView txtDuration = item.findViewById(R.id.txtDuration);  // Thời lượng
    Button btnDatVe = item.findViewById(R.id.btnDatVe);          // NÚT ĐẶT VÉ ⭐
    
    // BƯỚC 3: Lấy data phim tại vị trí này
    Movie movie = this.DSMovie.get(position);  // Lấy phim thứ position trong list
    
    // BƯỚC 4: Gán data vào view
    imageView.setImageResource(movie.getPosterId());  // Set poster
    txtTitle.setText(movie.getTitle());               // Set tên
    txtGenre.setText(movie.getGenre());               // Set thể loại
    txtDuration.setText(movie.getDuration());         // Set thời lượng
    
    // BƯỚC 5: XỬ LÝ CLICK NÚT "ĐẶT VÉ" ⭐⭐⭐
    btnDatVe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // ═══════════════════════════════════════════
            // KHI USER CLICK "ĐẶT VÉ", CODE NÀY CHẠY
            // ═══════════════════════════════════════════
            
            // Tạo Intent để chuyển màn hình
            Intent intent = new Intent(context, SeatSelectionActivity.class);
            
            // Đóng gói thông tin phim vào Intent
            intent.putExtra("MOVIE_ID", movie.getId());           // ID: 1, 2, 3...
            intent.putExtra("MOVIE_TITLE", movie.getTitle());     // Tên: "Bad Boys"
            intent.putExtra("SHOW_TIME", "19:30 - 30/11/2025");  // ⚠️ HARDCODE
            intent.putExtra("ROOM_ID", 1);                        // ⚠️ HARDCODE
            intent.putExtra("ROOM_NAME", "A1");                   // ⚠️ HARDCODE
            
            // Chuyển màn hình (mở SeatSelectionActivity)
            context.startActivity(intent);
        }
    });
    
    return item;  // Trả về view đã setup xong
}
```

### 🔍 Phân tích:

**Input:**
- `position`: Vị trí phim trong list (0, 1, 2, 3, 4, 5)
- `DSMovie`: ArrayList chứa 6 phim

**Process:**
1. Inflate layout `item_movie.xml`
2. Gán dữ liệu phim vào các TextView/ImageView
3. Gắn listener cho button "Đặt vé"
4. Khi click → Tạo Intent với data → Mở màn hình mới

**Output:**
- View item hiển thị thông tin 1 phim
- Button có thể click để mở màn hình chọn ghế

**⚠️ Vấn đề:**
- Suất chiếu **HARDCODE** = "19:30 - 30/11/2025" (giống nhau cho tất cả phim)
- Room ID **HARDCODE** = 1 (luôn phòng A1)
- Không có màn hình chọn suất chiếu

---

## 3. SeatSelectionActivity - MÀN HÌNH CHÍNH

### 📍 File: `SeatSelectionActivity.java`

### 🎯 Nhiệm vụ:
- Nhận thông tin phim từ Intent
- Generate 80 ghế (8x10)
- Hiển thị lưới ghế
- Xử lý chọn/bỏ chọn ghế
- Tính tổng tiền

### 💻 Code giải thích từng phần:

---

### 3.1. Khởi tạo Activity

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_seat_selection);  // Load layout
    
    // BƯỚC 1: Tìm tất cả view
    initViews();
    
    // BƯỚC 2: Setup toolbar (nút back)
    setupToolbar();
    
    // BƯỚC 3: ⭐ TẠO DỮ LIỆU GHẾ GIẢ
    loadSampleSeats();
    
    // BƯỚC 4: Setup RecyclerView để hiển thị ghế
    setupRecyclerView();
    
    // BƯỚC 5: Setup nút "Tiếp tục"
    setupButtons();
}
```

**Thứ tự thực thi:**
```
onCreate → initViews → setupToolbar → loadSampleSeats 
         → setupRecyclerView → setupButtons
```

---

### 3.2. Tìm các View

```java
private void initViews() {
    // Tìm RecyclerView hiển thị ghế
    rvSeats = findViewById(R.id.rvSeats);
    
    // Tìm các TextView hiển thị info
    tvMovieTitle = findViewById(R.id.tvMovieTitle);      // Tên phim
    tvShowTime = findViewById(R.id.tvShowTime);          // Suất chiếu
    tvCinemaRoom = findViewById(R.id.tvCinemaRoom);      // Phòng chiếu
    tvSelectedSeats = findViewById(R.id.tvSelectedSeats); // Ghế đã chọn
    tvTotalPrice = findViewById(R.id.tvTotalPrice);      // Tổng tiền
    
    // Tìm nút tiếp tục
    btnContinue = findViewById(R.id.btnContinue);
    
    // ⭐ Tạo list rỗng để chứa ghế đã chọn
    selectedSeats = new ArrayList<>();
}
```

**Kết quả:**
- Tất cả view được reference
- `selectedSeats` = danh sách rỗng (chưa chọn ghế nào)

---

### 3.3. ⭐ TẠO DỮ LIỆU GHẾ (Phần quan trọng nhất)

```java
private void loadSampleSeats() {
    allSeats = new ArrayList<>();  // List chứa TẤT CẢ ghế
    
    // ═══════════════════════════════════════════
    // BƯỚC 1: NHẬN THÔNG TIN TỪ INTENT
    // ═══════════════════════════════════════════
    Intent intent = getIntent();
    String movieTitle = intent.getStringExtra("MOVIE_TITLE");  // "Bad Boys"
    String showTime = intent.getStringExtra("SHOW_TIME");      // "19:30 - 30/11/2025"
    String roomName = intent.getStringExtra("ROOM_NAME");      // "A1"
    int movieId = intent.getIntExtra("MOVIE_ID", 0);           // 2
    int roomId = intent.getIntExtra("ROOM_ID", 0);             // 1
    
    // ═══════════════════════════════════════════
    // BƯỚC 2: GENERATE 80 GHẾ (8 CỘT x 10 HÀNG)
    // ═══════════════════════════════════════════
    
    // Định nghĩa 10 hàng: A, B, C, D, E, F, G, H, I, J
    String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    
    int id = 1;  // ID ghế bắt đầu từ 1
    
    // VÒNG LẶP 1: Duyệt qua từng HÀNG
    for (String row : rows) {
        
        // VÒNG LẶP 2: Duyệt qua từng CỘT (1 → 8)
        for (int number = 1; number <= SEATS_PER_ROW; number++) {
            
            // ───────────────────────────────
            // XÁC ĐỊNH TRẠNG THÁI GHẾ
            // ───────────────────────────────
            String status = "available";  // Mặc định = trống
            double price = 75000;         // Giá mặc định = 75k
            
            // ⭐ LOGIC PHÂN LOẠI GHẾ VIP
            if (row.equals("D") || row.equals("E") || row.equals("F")) {
                price = 100000;  // Hàng D, E, F = VIP = 100k
            }
            
            // ⭐ LOGIC GHẾ ĐÃ BÁN (GIẢ LẬP)
            // Giả lập ghế E4, E5, F3 đã được người khác đặt
            if ((row.equals("E") && (number == 4 || number == 5)) ||
                (row.equals("F") && number == 3)) {
                status = "sold";  // Đánh dấu đã bán
            }
            
            // ───────────────────────────────
            // TẠO OBJECT GHẾ
            // ───────────────────────────────
            Seat seat = new Seat(
                id++,           // ID tự động tăng: 1, 2, 3...
                row,            // Hàng: "A", "B", "C"...
                number,         // Số: 1, 2, 3...
                "standard",     // Type (không dùng)
                status,         // "available" hoặc "sold"
                price           // 75000 hoặc 100000
            );
            
            seat.setRoomId(roomId);  // Gắn room ID
            allSeats.add(seat);       // Thêm vào list
        }
    }
    
    // ═══════════════════════════════════════════
    // BƯỚC 3: HIỂN THỊ THÔNG TIN LÊN UI
    // ═══════════════════════════════════════════
    tvMovieTitle.setText(movieTitle != null ? movieTitle : "Bad Boys: Ride or Die");
    tvShowTime.setText(showTime != null ? showTime : "Suất chiếu: 19:30 - 30/11/2025");
    tvCinemaRoom.setText(roomName != null ? "Phòng chiếu: " + roomName : "Phòng chiếu: A1");
}
```

**🔍 Phân tích Generate Ghế:**

```
Hàng A: A1(75k) A2(75k) A3(75k) A4(75k) A5(75k) A6(75k) A7(75k) A8(75k)
Hàng B: B1(75k) B2(75k) B3(75k) B4(75k) B5(75k) B6(75k) B7(75k) B8(75k)
Hàng C: C1(75k) C2(75k) C3(75k) C4(75k) C5(75k) C6(75k) C7(75k) C8(75k)
Hàng D: D1(100k VIP) D2(100k VIP) ... D8(100k VIP)
Hàng E: E1(100k VIP) E2(100k VIP) E3(100k VIP) E4(SOLD❌) E5(SOLD❌) E6(100k VIP) E7(100k VIP) E8(100k VIP)
Hàng F: F1(100k VIP) F2(100k VIP) F3(SOLD❌) F4(100k VIP) ... F8(100k VIP)
Hàng G: G1(75k) G2(75k) ... G8(75k)
Hàng H: H1(75k) H2(75k) ... H8(75k)
Hàng I: I1(75k) I2(75k) ... I8(75k)
Hàng J: J1(75k) J2(75k) ... J8(75k)

Tổng: 80 ghế
- Ghế thường (75k): 56 ghế
- Ghế VIP (100k): 21 ghế
- Ghế đã bán: 3 ghế (E4, E5, F3)
```

**⚠️ Lưu ý:**
- Dữ liệu ghế được **generate mỗi lần mở màn hình**
- Không load từ database/API
- Ghế "sold" là giả lập cố định (luôn E4, E5, F3)

---

### 3.4. Setup RecyclerView

```java
private void setupRecyclerView() {
    // Tạo GridLayoutManager với 8 cột
    GridLayoutManager layoutManager = new GridLayoutManager(this, SEATS_PER_ROW);
    rvSeats.setLayoutManager(layoutManager);
    
    // Tạo adapter với danh sách ghế và listener
    seatAdapter = new SeatAdapter(allSeats, this);
    rvSeats.setAdapter(seatAdapter);
}
```

**Kết quả:**
```
RecyclerView hiển thị lưới ghế 8 cột:

┌────┬────┬────┬────┬────┬────┬────┬────┐
│ A1 │ A2 │ A3 │ A4 │ A5 │ A6 │ A7 │ A8 │
├────┼────┼────┼────┼────┼────┼────┼────┤
│ B1 │ B2 │ B3 │ B4 │ B5 │ B6 │ B7 │ B8 │
├────┼────┼────┼────┼────┼────┼────┼────┤
│ ... (10 hàng)                        │
└────┴────┴────┴────┴────┴────┴────┴────┘
```

---

## 4. SeatAdapter - HIỂN THỊ GHẾ

### 📍 File: `SeatAdapter.java`

### 🎯 Nhiệm vụ:
- Render từng ô ghế trong RecyclerView
- Đổi màu theo trạng thái
- Xử lý click ghế

### 💻 Code giải thích:

---

### 4.1. Tạo ViewHolder

```java
@Override
public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // Inflate layout item_seat.xml cho MỖI GHẾ
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_seat, parent, false);
    return new SeatViewHolder(view);
}
```

**Gọi:** 80 lần (1 lần cho mỗi ghế)  
**Kết quả:** Tạo 80 TextView để hiển thị ghế

---

### 4.2. Bind Data (Quan trọng nhất)

```java
@Override
public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
    Seat seat = seatList.get(position);  // Lấy ghế tại vị trí này
    holder.bind(seat);                    // Gắn data vào view
}

// Method bind trong ViewHolder:
public void bind(Seat seat) {
    // ═══════════════════════════════════════════
    // BƯỚC 1: HIỂN THỊ TÊN GHẾ
    // ═══════════════════════════════════════════
    tvSeat.setText(seat.getSeatLabel());  // "A1", "B2", "C3"...
    
    // ═══════════════════════════════════════════
    // BƯỚC 2: ⭐ ĐỔI MÀU THEO TRẠNG THÁI
    // ═══════════════════════════════════════════
    int backgroundRes;  // Biến chứa resource drawable
    
    switch (seat.getStatus().toLowerCase()) {
        case "available":
            // ✅ GHẾ TRỐNG = MÀU XANH LÁ
            backgroundRes = R.drawable.seat_available;
            tvSeat.setEnabled(true);  // Cho phép click
            break;
            
        case "selected":
            // 🟠 GHẾ ĐANG CHỌN = MÀU CAM
            backgroundRes = R.drawable.seat_selected;
            tvSeat.setEnabled(true);  // Cho phép click (để bỏ chọn)
            break;
            
        case "sold":
            // ⚫ GHẾ ĐÃ BÁN = MÀU XÁM
            backgroundRes = R.drawable.seat_sold;
            tvSeat.setEnabled(false);  // KHÔNG cho phép click
            break;
            
        default:
            backgroundRes = R.drawable.seat_available;
            tvSeat.setEnabled(true);
            break;
    }
    
    // Áp dụng background
    tvSeat.setBackgroundResource(backgroundRes);
    
    // Set màu chữ trắng
    tvSeat.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
    
    // ═══════════════════════════════════════════
    // BƯỚC 3: ⭐ XỬ LÝ CLICK GHẾ
    // ═══════════════════════════════════════════
    tvSeat.setOnClickListener(v -> {
        // Kiểm tra: Có listener không? Ghế đã bán chưa?
        if (listener != null && !seat.getStatus().equalsIgnoreCase("sold")) {
            // Gọi callback về Activity
            listener.onSeatClick(seat, getAdapterPosition());
        }
    });
}
```

**🎨 Quy tắc màu:**
```
┌─────────────────────────────────────────────┐
│ Trạng thái     Màu          File drawable   │
├─────────────────────────────────────────────┤
│ available    → 🟢 Xanh lá → seat_available  │
│ selected     → 🟠 Cam     → seat_selected   │
│ sold         → ⚫ Xám     → seat_sold       │
└─────────────────────────────────────────────┘
```

**🖱️ Quy tắc click:**
```
┌────────────────────────────────────────┐
│ Trạng thái    Click được?   Hành động  │
├────────────────────────────────────────┤
│ available  →  ✅ Có      → Chọn ghế    │
│ selected   →  ✅ Có      → Bỏ chọn    │
│ sold       →  ❌ KHÔNG   → Không làm gì │
└────────────────────────────────────────┘
```

---

## 5. CƠ CHẾ CHỌN GHẾ

### 🖱️ Khi user click vào ghế:

```java
@Override
public void onSeatClick(Seat seat, int position) {
    // ═══════════════════════════════════════════
    // KIỂM TRA: Ghế đã bán chưa?
    // ═══════════════════════════════════════════
    if (seat.getStatus().equalsIgnoreCase("sold")) {
        Toast.makeText(this, "Ghế này đã được đặt", Toast.LENGTH_SHORT).show();
        return;  // Dừng lại, không làm gì
    }
    
    // ═══════════════════════════════════════════
    // TOGGLE TRẠNG THÁI (CHỌN ↔ BỎ CHỌN)
    // ═══════════════════════════════════════════
    
    if (seat.getStatus().equalsIgnoreCase("selected")) {
        // ━━━ TRƯỜNG HỢP 1: GHẾ ĐANG CHỌN → BỎ CHỌN ━━━
        seat.setStatus("available");      // Đổi status về "available"
        selectedSeats.remove(seat);        // Xóa khỏi danh sách đã chọn
    } else {
        // ━━━ TRƯỜNG HỢP 2: GHẾ TRỐNG → CHỌN ━━━
        seat.setStatus("selected");        // Đổi status sang "selected"
        selectedSeats.add(seat);           // Thêm vào danh sách đã chọn
    }
    
    // ═══════════════════════════════════════════
    // CẬP NHẬT UI
    // ═══════════════════════════════════════════
    
    // 1. Cập nhật màu ghế (gọi bind lại)
    seatAdapter.notifyItemChanged(position);
    
    // 2. Cập nhật thanh bottom (tổng tiền, danh sách ghế)
    updateBottomBar();
}
```

**📊 Luồng xử lý:**

```
User click ghế A5
    ↓
onSeatClick(seatA5, position=4) được gọi
    ↓
Kiểm tra: sold? → Không
    ↓
Kiểm tra: selected? → Không
    ↓
→ Chọn ghế: status = "selected"
→ Thêm vào selectedSeats
    ↓
notifyItemChanged(4) → Adapter bind lại ghế A5
    ↓
bind() thấy status = "selected" → Đổi màu cam
    ↓
updateBottomBar() → Cập nhật tổng tiền
```

**🔄 Ví dụ cụ thể:**

```
TRẠNG THÁI BAN ĐẦU:
┌────────────────────────────────────────────┐
│ Ghế   Status       selectedSeats   Màu    │
├────────────────────────────────────────────┤
│ A1    available    []             🟢 Xanh  │
│ A2    available    []             🟢 Xanh  │
│ E4    sold         []             ⚫ Xám   │
└────────────────────────────────────────────┘

USER CLICK A1:
┌────────────────────────────────────────────┐
│ Ghế   Status       selectedSeats   Màu    │
├────────────────────────────────────────────┤
│ A1    selected     [A1]           🟠 Cam   │ ← Đổi màu
│ A2    available    [A1]           🟢 Xanh  │
│ E4    sold         [A1]           ⚫ Xám   │
└────────────────────────────────────────────┘
Tổng tiền: 75,000 VNĐ

USER CLICK A2:
┌────────────────────────────────────────────┐
│ Ghế   Status       selectedSeats   Màu    │
├────────────────────────────────────────────┤
│ A1    selected     [A1, A2]       🟠 Cam   │
│ A2    selected     [A1, A2]       🟠 Cam   │ ← Đổi màu
│ E4    sold         [A1, A2]       ⚫ Xám   │
└────────────────────────────────────────────┘
Tổng tiền: 150,000 VNĐ

USER CLICK A1 LẦN 2 (BỎ CHỌN):
┌────────────────────────────────────────────┐
│ Ghế   Status       selectedSeats   Màu    │
├────────────────────────────────────────────┤
│ A1    available    [A2]           🟢 Xanh  │ ← Đổi lại xanh
│ A2    selected     [A2]           🟠 Cam   │
│ E4    sold         [A2]           ⚫ Xám   │
└────────────────────────────────────────────┘
Tổng tiền: 75,000 VNĐ

USER CLICK E4:
→ Toast: "Ghế này đã được đặt"
→ Không làm gì
```

---

## 6. TÍNH NĂNG SUẤT CHIẾU

### ⚠️ HIỆN TRẠNG: HARDCODE

```java
// Trong MyMovieAdapter.java
intent.putExtra("SHOW_TIME", "19:30 - 30/11/2025");  // ← CỐ ĐỊNH
intent.putExtra("ROOM_NAME", "A1");                   // ← CỐ ĐỊNH
```

**Vấn đề:**
- ❌ Không có màn hình chọn suất chiếu
- ❌ Luôn luôn là "19:30 - 30/11/2025" cho mọi phim
- ❌ Luôn luôn phòng A1
- ❌ Không biết phim có suất chiếu nào

### 🎯 Cách hoạt động ĐÚNG (khi có API):

```
┌─────────────────────────────────────────────┐
│ LUỒNG CHỌN SUẤT CHIẾU ĐÚNG                  │
└─────────────────────────────────────────────┘

1. User click "Đặt vé" trên phim "Bad Boys"
    ↓
2. API: GET /showtimes?movie_id=2
    ↓
3. Màn hình "Chọn suất chiếu" hiển thị:
    ┌──────────────────────────────┐
    │ 30/11/2025                   │
    │ ○ 09:00 - Phòng A1 (2D)     │
    │ ○ 12:30 - Phòng B2 (3D)     │
    │ ● 19:30 - Phòng A1 (2D) ✓   │ ← User chọn
    │ ○ 22:00 - Phòng C1 (IMAX)   │
    │                              │
    │ 01/12/2025                   │
    │ ○ 10:00 - Phòng A1 (2D)     │
    │ ...                          │
    └──────────────────────────────┘
    ↓
4. User chọn "19:30 - Phòng A1"
    ↓
5. Intent chuyển sang SeatSelectionActivity:
   - MOVIE_ID: 2
   - SHOWTIME_ID: 145  ← Quan trọng!
   - SHOW_TIME: "19:30 - 30/11/2025"
   - SCREEN_ID: 1
   - ROOM_NAME: "A1"
    ↓
6. API: GET /seats?showtime_id=145
    ↓
7. Hiển thị ghế THỰC TẾ của suất chiếu đó
```

### 📊 ShowTime Model (đã có)

```java
public class ShowTime {
    private int id;              // ID suất chiếu
    private int movieId;         // ID phim
    private int screenId;        // ID phòng chiếu
    private Date startAt;        // Thời gian bắt đầu
    private Date endAt;          // Thời gian kết thúc
    private int basePrice;       // Giá vé cơ bản
    private String status;       // OPEN, CLOSED, SCHEDULED
    
    // ... getters/setters
}
```

**Database có:**
```sql
CREATE TABLE showtime (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id     BIGINT NOT NULL,
    screen_id    BIGINT NOT NULL,
    start_at     DATETIME NOT NULL,
    end_at       DATETIME NOT NULL,
    base_price   INT NOT NULL,
    status       ENUM('SCHEDULED', 'OPEN', 'CLOSED', 'CANCELLED')
);
```

**⚠️ Nhưng code CHƯA dùng:**
- Không có Activity để chọn suất chiếu
- Không gọi API lấy danh sách suất chiếu
- Hardcode trực tiếp trong adapter

---

## 7. TÍNH TỔNG TIỀN

```java
private void updateBottomBar() {
    // ═══════════════════════════════════════════
    // PHẦN 1: CẬP NHẬT DANH SÁCH GHẾ ĐÃ CHỌN
    // ═══════════════════════════════════════════
    
    if (selectedSeats.isEmpty()) {
        // Chưa chọn ghế nào
        tvSelectedSeats.setText("Chưa chọn");
        btnContinue.setEnabled(false);  // Vô hiệu hóa nút
    } else {
        // Đã chọn ghế → Tạo chuỗi "A1, B2, C3"
        StringBuilder seatLabels = new StringBuilder();
        
        for (int i = 0; i < selectedSeats.size(); i++) {
            seatLabels.append(selectedSeats.get(i).getSeatLabel());
            
            // Thêm dấu phẩy (trừ ghế cuối)
            if (i < selectedSeats.size() - 1) {
                seatLabels.append(", ");
            }
        }
        
        tvSelectedSeats.setText(seatLabels.toString());  // "A1, B2, D3"
        btnContinue.setEnabled(true);  // Kích hoạt nút
    }
    
    // ═══════════════════════════════════════════
    // PHẦN 2: TÍNH TỔNG TIỀN
    // ═══════════════════════════════════════════
    
    double totalPrice = 0;
    
    // Cộng dồn giá của từng ghế
    for (Seat seat : selectedSeats) {
        totalPrice += seat.getPrice();
    }
    
    // Format theo kiểu Việt Nam: 150,000 VNĐ
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    tvTotalPrice.setText(formatter.format(totalPrice));
}
```

**🧮 Ví dụ tính toán:**

```
Ghế đã chọn: [A1, B2, D3]

A1: 75,000 (ghế thường)
B2: 75,000 (ghế thường)
D3: 100,000 (ghế VIP - hàng D)
────────────────────────
Tổng: 250,000 VNĐ

→ Hiển thị: "250.000 ₫"
→ Ghế: "A1, B2, D3"
→ Nút "Tiếp tục": Enabled ✅
```

---

## 📊 TÓM TẮT TOÀN BỘ LUỒNG

```
┌──────────────────────────────────────────────────────────┐
│                  LUỒNG CHỌN GHẾ ĐẦY ĐỦ                   │
└──────────────────────────────────────────────────────────┘

1️⃣  Fragment_movie.java
    └─ Hiển thị 6 phim (hardcode)
        └─ User click "Đặt vé" trên phim "Bad Boys"

2️⃣  MyMovieAdapter.java
    └─ onClickListener:
        ├─ Tạo Intent
        ├─ putExtra("MOVIE_ID", 2)
        ├─ putExtra("MOVIE_TITLE", "Bad Boys")
        ├─ putExtra("SHOW_TIME", "19:30...") ← Hardcode
        ├─ putExtra("ROOM_ID", 1)            ← Hardcode
        └─ startActivity()

3️⃣  SeatSelectionActivity.java
    └─ onCreate():
        ├─ initViews()
        │   └─ selectedSeats = []
        │
        ├─ loadSampleSeats()
        │   ├─ getIntent().getStringExtra(...)
        │   ├─ for row in [A..J]:
        │   │   └─ for number in [1..8]:
        │   │       ├─ if row in [D,E,F]: price=100k
        │   │       ├─ if E4 or E5 or F3: status="sold"
        │   │       └─ allSeats.add(new Seat(...))
        │   └─ Total: 80 ghế
        │
        └─ setupRecyclerView()
            └─ GridLayoutManager(8 cột)

4️⃣  SeatAdapter.java
    └─ onBindViewHolder():
        ├─ getText(seat.getSeatLabel())  → "A1"
        ├─ switch(seat.getStatus()):
        │   ├─ "available"  → 🟢 seat_available.xml
        │   ├─ "selected"   → 🟠 seat_selected.xml
        │   └─ "sold"       → ⚫ seat_sold.xml
        └─ setOnClickListener() → callback Activity

5️⃣  User click ghế A1
    └─ SeatAdapter → listener.onSeatClick(seatA1, 0)

6️⃣  SeatSelectionActivity.onSeatClick()
    ├─ Check: sold? → No
    ├─ Check: selected? → No
    ├─ → seat.setStatus("selected")
    ├─ → selectedSeats.add(seatA1)
    ├─ → adapter.notifyItemChanged(0)
    └─ → updateBottomBar()

7️⃣  updateBottomBar()
    ├─ seatLabels = "A1"
    ├─ totalPrice = 75,000
    ├─ tvSelectedSeats.setText("A1")
    ├─ tvTotalPrice.setText("75.000 ₫")
    └─ btnContinue.setEnabled(true)

8️⃣  User click nút "Tiếp tục"
    └─ Toast: "Tiếp tục thanh toán với 1 ghế"
        └─ TODO: Chuyển sang màn hình thanh toán
```

---

## 🎯 CÁC ĐIỂM QUAN TRỌNG CẦN NHỚ

### 1. Dữ liệu ghế
```
❌ KHÔNG load từ database
❌ KHÔNG gọi API
✅ Generate mỗi lần mở màn hình
✅ 80 ghế cố định (10 hàng x 8 cột)
✅ E4, E5, F3 luôn "sold"
```

### 2. Suất chiếu
```
❌ KHÔNG có màn hình chọn
❌ KHÔNG load từ API
✅ Hardcode "19:30 - 30/11/2025"
✅ Hardcode phòng "A1"
```

### 3. Trạng thái ghế
```
available  → Trống  → 🟢 Xanh → Click được
selected   → Chọn   → 🟠 Cam   → Click được
sold       → Đã bán → ⚫ Xám   → KHÔNG click
```

### 4. Tính tiền
```
Ghế thường (A,B,C,G,H,I,J):  75,000 VNĐ
Ghế VIP (D,E,F):             100,000 VNĐ
Tổng = Σ(price của ghế đã chọn)
```

### 5. RecyclerView
```
GridLayoutManager → 8 cột
80 item → 10 hàng
Mỗi item = 1 TextView (item_seat.xml)
```

### 6. Click handling
```
Click ghế → Adapter.bind() gọi callback
         → Activity.onSeatClick()
         → Toggle status
         → notifyItemChanged()
         → bind() lại → Đổi màu
         → updateBottomBar()
```

---

## 🔮 KHI CÓ API (Tương lai)

### Thay đổi cần làm:

```java
// THAY ĐỔI 1: Load ghế từ API
private void loadSeatsFromAPI(int showtimeId) {
    apiService.getSeats(showtimeId).enqueue(new Callback<List<Seat>>() {
        @Override
        public void onResponse(Call<List<Seat>> call, Response<List<Seat>> response) {
            allSeats = response.body();
            seatAdapter.updateSeats(allSeats);
        }
    });
}

// THAY ĐỔI 2: Lock ghế khi chọn
private void lockSeat(int seatId) {
    apiService.lockSeat(seatId).enqueue(callback);
    // Server giữ ghế trong 10 phút
}

// THAY ĐỔI 3: Create order khi thanh toán
private void createOrder() {
    Order order = new Order();
    order.setShowtimeId(showtimeId);
    order.setSeatIds(selectedSeatIds);
    
    apiService.createOrder(order).enqueue(callback);
}
```

---

**📚 File này giải thích hoàn chỉnh cách code hoạt động!**  
**🎯 Đọc kỹ để hiểu rõ từng bước!**

*Ngày tạo: 30/11/2025*  
*Mục đích: Giải thích code cho người mới*

