# 📚 Hướng Dẫn Chi Tiết SeatAdapter - Phiên Bản Dễ Hiểu

## 🎯 Mục Đích Chính

`SeatAdapter` là cầu nối giữa **danh sách ghế (Data)** và **giao diện RecyclerView (UI)**

```
Danh sách ghế (List<Seat>)
        ↓
    SeatAdapter
        ↓
   RecyclerView
        ↓
   Màn hình (hiển thị ghế)
```

---

## 📖 Giải Thích Từng Phần

### **1️⃣ Khai Báo Biến (dòng 23-28)**

```java
private List<Seat> seatList;           // Danh sách tất cả ghế
private OnSeatClickListener listener;   // Callback khi ghế được click
```

**Ý nghĩa:**
- `seatList`: Chứa dữ liệu ghế từ `SeatSelectionActivity` (VD: 80 ghế)
- `listener`: Là Activity (nó implement interface này), để Adapter gọi callback

**Ví dụ:**
```
seatList = [Ghế A1, Ghế A2, Ghế A3, ..., Ghế J8]
listener = SeatSelectionActivity (Activity vừa mở màn hình)
```

---

### **2️⃣ Interface OnSeatClickListener (dòng 30-33)**

```java
public interface OnSeatClickListener {
    void onSeatClick(Seat seat, int position);
}
```

**Mục đích:** Là một "hợp đồng" (contract)

**Ví dụ:**
```
Activity nói: "Tôi implement interface này"
    ↓
Activity phải có phương thức: onSeatClick(Seat seat, int position)
    ↓
Khi ghế được click, Adapter gọi: listener.onSeatClick(ghế, vị trí)
    ↓
Activity nhận được và xử lý
```

**So sánh:** Giống như đặt hàng qua điện thoại
- Interface = "Yêu cầu nhân viên gọi lại"
- Adapter = Nhân viên quán
- Activity = Khách hàng
- `onSeatClick()` = Cuộc gọi lại từ nhân viên

---

### **3️⃣ Constructor - Khởi Tạo (dòng 37-42)**

```java
public SeatAdapter(List<Seat> seatList, OnSeatClickListener listener) {
    this.seatList = seatList != null ? seatList : new ArrayList<>();
    this.listener = listener;
}
```

**Công việc:**
1. Nhận danh sách ghế từ Activity
2. Lưu listener (Activity)
3. Nếu `seatList` null → Tạo list rỗng (tránh crash)

**Gọi từ Activity:**
```java
List<Seat> allSeats = [Ghế A1, A2, ..., J8];
SeatAdapter adapter = new SeatAdapter(allSeats, this);
//                                       ↑       ↑
//                          dữ liệu    Activity (listener)
```

---

### **4️⃣ onCreateViewHolder - Tạo View Item (dòng 47-59)**

```java
public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.item_seat, parent, false);
    return new SeatViewHolder(itemView);
}
```

**Công việc:**
1. Tạo `LayoutInflater` - công cụ chuyển XML → View
2. Load file `item_seat.xml` → Tạo TextView
3. Wrap vào `SeatViewHolder`
4. Trả về

**Flow:**
```
RecyclerView cần item mới
    ↓
Gọi onCreateViewHolder()
    ↓
Tạo 1 item_seat.xml (chứa 1 TextView)
    ↓
Wrap vào SeatViewHolder
    ↓
Trả về ViewHolder
```

**Ví dụ:**
```
item_seat.xml:
<TextView
    android:id="@+id/tvSeat"
    android:text="A1"
    android:background="@drawable/seat_available"
/>
```

---

### **5️⃣ onBindViewHolder - Gắn Dữ Liệu (dòng 64-71)**

```java
public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
    Seat seat = seatList.get(position);
    holder.bind(seat);
}
```

**Công việc:**
1. Lấy ghế tại vị trí `position`
2. Gọi `holder.bind(seat)` → ViewHolder cập nhật View

**Flow:**
```
RecyclerView cần cập nhật item thứ 0
    ↓
Gọi onBindViewHolder(holder, 0)
    ↓
Lấy seat = seatList.get(0) = Ghế A1
    ↓
Gọi holder.bind(A1)
    ↓
ViewHolder cập nhật TextView: hiển thị "A1", set màu xanh
```

**Ví dụ:**
```
position = 0 → Ghế A1 (available) → TextView: "A1" xanh lá
position = 1 → Ghế A2 (sold)      → TextView: "A2" đỏ
position = 2 → Ghế A3 (selected)  → TextView: "A3" xanh dương
```

---

### **6️⃣ getItemCount - Số Ghế (dòng 76-78)**

```java
public int getItemCount() {
    return seatList.size();
}
```

**Công việc:** Báo cho RecyclerView có bao nhiêu item cần tạo

**Ví dụ:**
```
seatList.size() = 80 → RecyclerView sẽ tạo 80 item
seatList.size() = 0  → RecyclerView sẽ không tạo item nào
```

---

### **7️⃣ updateSeats - Cập Nhật Danh Sách (dòng 82-89)**

```java
public void updateSeats(List<Seat> newSeats) {
    this.seatList = newSeats != null ? newSeats : new ArrayList<>();
    notifyDataSetChanged();
}
```

**Công việc:**
1. Thay thế danh sách ghế cũ bằng danh sách mới
2. Gọi `notifyDataSetChanged()` → RecyclerView làm mới UI

**Khi dùng:** Khi nhận dữ liệu từ API

**Ví dụ:**
```
// API trả về danh sách ghế mới
List<Seat> newSeats = apiClient.getSeats();

// Cập nhật Adapter
adapter.updateSeats(newSeats);

// RecyclerView sẽ gọi lại tất cả onBindViewHolder()
// → UI cập nhật tất cả ghế
```

---

## 🌟 SeatViewHolder - Phần Quan Trọng

### **ViewHolder là gì?**

ViewHolder = Container chứa các View của 1 item

```
ViewHolder
  ├─ TextView tvSeat (hiển thị "A1", "B5", v.v.)
  ├─ background (xanh/đỏ)
  └─ click listener
```

---

### **Constructor (dòng 124-129)**

```java
public SeatViewHolder(@NonNull View itemView) {
    super(itemView);
    tvSeat = itemView.findViewById(R.id.tvSeat);
}
```

**Công việc:**
1. `super(itemView)` - Lưu tham chiếu item View
2. `findViewById()` - Tìm TextView có ID `tvSeat` trong item_seat.xml

**Tương tự:** `findViewById()` trong Activity, nhưng đặc biệt cho ViewHolder

---

### **bind() - Cập Nhật UI Cho 1 Ghế (dòng 134-159)**

```java
public void bind(Seat seat) {
    // BƯỚC 1: Hiển thị text
    String seatLabel = seat.getSeatLabel();
    tvSeat.setText(seatLabel);
    
    // BƯỚC 2: Xác định màu
    int backgroundDrawable = getBackgroundBySeatStatus(seat.getStatus());
    boolean isClickable = !seat.getStatus().equalsIgnoreCase("sold");
    
    // BƯỚC 3: Áp dụng background
    tvSeat.setBackgroundResource(backgroundDrawable);
    
    // BƯỚC 4: Set màu chữ trắng
    int whiteColor = ContextCompat.getColor(itemView.getContext(), android.R.color.white);
    tvSeat.setTextColor(whiteColor);
    
    // BƯỚC 5: Enable/Disable click
    tvSeat.setEnabled(isClickable);
    
    // BƯỚC 6: Thêm click listener
    setupClickListener(seat, isClickable);
}
```

**BƯỚC 1 - Hiển thị text:**
```java
tvSeat.setText(seat.getSeatLabel());
// Ví dụ: "A1", "B5", "C10"
```

**BƯỚC 2 - Xác định màu:**
```java
int backgroundDrawable = getBackgroundBySeatStatus(seat.getStatus());
// Status: "available" → seat_available (xanh lá)
// Status: "selected"  → seat_selected  (xanh dương)
// Status: "sold"      → seat_sold      (đỏ)
```

**BƯỚC 3 - Áp dụng background:**
```java
tvSeat.setBackgroundResource(backgroundDrawable);
// TextView lúc này sẽ có màu
```

**BƯỚC 4 - Set màu chữ:**
```java
tvSeat.setTextColor(whiteColor);
// Chữ "A1" sẽ là trắng
```

**BƯỚC 5 - Enable/Disable click:**
```java
tvSeat.setEnabled(isClickable);
// Nếu ghế đã bán (sold) → Disable (không cho click)
// Nếu ghế còn trống (available) → Enable (cho click)
```

**BƯỚC 6 - Thêm click listener:**
```java
setupClickListener(seat, isClickable);
// Khi click, sẽ gọi: listener.onSeatClick(seat, position)
```

---

### **getBackgroundBySeatStatus() - Lấy Màu (dòng 164-187)**

```java
private int getBackgroundBySeatStatus(String status) {
    String statusLower = status.toLowerCase();
    
    if (statusLower.equals("available")) {
        return R.drawable.seat_available;  // Xanh lá
    } else if (statusLower.equals("selected")) {
        return R.drawable.seat_selected;   // Xanh dương
    } else if (statusLower.equals("sold")) {
        return R.drawable.seat_sold;       // Đỏ
    } else {
        return R.drawable.seat_available;  // Mặc định
    }
}
```

**Công việc:** Chuyển status thành drawable

**Ví dụ:**
```
status = "available" → return R.drawable.seat_available (ID file drawable)
status = "selected"  → return R.drawable.seat_selected
status = "SOLD" (viết hoa) → toLowerCase() → "sold" → return R.drawable.seat_sold
```

---

### **setupClickListener() - Xử Lý Click (dòng 192-210)**

```java
private void setupClickListener(Seat seat, boolean isClickable) {
    tvSeat.setOnClickListener(v -> {
        if (listener != null && isClickable) {
            listener.onSeatClick(seat, getAdapterPosition());
        }
    });
}
```

**Flow khi click:**
```
Người dùng nhấn ghế A1
    ↓
setOnClickListener trigger
    ↓
Kiểm tra: listener != null AND isClickable?
    ├─ Nếu ghế là "sold" → isClickable = false → Không làm gì
    └─ Nếu ghế là "available" → isClickable = true → Tiếp tục
    ↓
Gọi: listener.onSeatClick(seatA1, position)
    ↓
SeatSelectionActivity.onSeatClick() được gọi
    ↓
Activity xử lý:
    - Thay đổi status: "available" → "selected"
    - Thêm vào selectedSeats
    - Gọi notifyItemChanged(position)
    ↓
RecyclerView gọi lại bind()
    ↓
bind() cập nhật UI: background đổi thành xanh dương
```

---

## 📊 Biểu Diễn Trạng Thái Ghế

| Trạng thái | Drawable | Color | Click? | Ý nghĩa |
|-----------|----------|-------|--------|---------|
| available | seat_available | 🟢 Xanh lá | ✅ | Ghế còn trống |
| selected | seat_selected | 🔵 Xanh dương | ✅ | Đã chọn |
| sold | seat_sold | 🔴 Đỏ | ❌ | Đã bán |

---

## 🔄 Flow Hoàn Chỉnh - Từ Đầu Đến Cuối

```
1️⃣ Activity tạo danh sách 80 ghế
   └─ List<Seat> allSeats = [A1, A2, ..., J8]

2️⃣ Activity tạo Adapter
   └─ SeatAdapter adapter = new SeatAdapter(allSeats, this)
   └─ SeatAdapter lưu: seatList = allSeats, listener = Activity

3️⃣ Activity set Adapter cho RecyclerView
   └─ rvSeats.setAdapter(adapter)
   └─ RecyclerView kết nối với Adapter

4️⃣ RecyclerView cần hiển thị 80 item
   └─ Gọi getItemCount() → 80

5️⃣ Với mỗi item:
   
   ✏️ Lần đầu:
   └─ onCreateViewHolder() tạo 1 item_seat.xml (TextView)
   └─ Wrap vào SeatViewHolder
   
   🔗 Sau đó:
   └─ onBindViewHolder(holder, position) 
   └─ Lấy seat = seatList.get(position)
   └─ holder.bind(seat) cập nhật View

6️⃣ bind() làm những việc sau:
   └─ tvSeat.setText("A1")              // Hiển thị
   └─ Set background (xanh/đỏ)         // Màu
   └─ Set text color (trắng)           // Chữ
   └─ setOnClickListener()             // Click listener

7️⃣ Người dùng nhấn ghế A1
   └─ onClick() được trigger
   └─ listener.onSeatClick(A1, 0) được gọi
   └─ Activity.onSeatClick() nhận được

8️⃣ Activity xử lý:
   └─ Thay status A1: "available" → "selected"
   └─ Thêm A1 vào selectedSeats
   └─ adapter.notifyItemChanged(0)

9️⃣ RecyclerView gọi lại bind() cho item 0
   └─ bind() cập nhật background → Xanh dương

🔟 Người dùng thấy ghế A1 đổi thành xanh dương ✅
```

---

## 💡 Tóm Tắt

| Thành phần | Trách nhiệm |
|-----------|-----------|
| **seatList** | Chứa dữ liệu 80 ghế |
| **listener** | Là Activity, xử lý callback |
| **OnSeatClickListener** | Interface, "hợp đồng" callback |
| **Constructor** | Lưu seatList và listener |
| **onCreateViewHolder()** | Tạo 1 item_seat.xml (ViewHolder) |
| **onBindViewHolder()** | Gắn dữ liệu ghế vào View |
| **getItemCount()** | Báo có 80 item |
| **updateSeats()** | Cập nhật danh sách từ API |
| **SeatViewHolder** | Quản lý 1 item |
| **bind()** | Cập nhật text, màu, click listener |
| **getBackgroundBySeatStatus()** | Chuyển status thành drawable |
| **setupClickListener()** | Xử lý sự kiện click |

---

## 🎓 Key Concepts

### Adapter Pattern
- Adapter = Bộ chuyển đổi (data → UI)
- Nhận data (List), trả về UI (RecyclerView)

### RecyclerView
- Tái sử dụng View (tại sao gọi "Recycler")
- Khi scroll, View cũ được tái sử dụng cho item mới
- Adapter quản lý việc tái sử dụng này

### ViewHolder Pattern
- ViewHolder = Container chứa View của 1 item
- Cải thiện performance bằng cách cache findViewById()

### Callback Pattern
- Interface OnSeatClickListener = "Hợp đồng"
- Adapter gọi listener khi sự kiện xảy ra
- Activity implement interface, xử lý callback

