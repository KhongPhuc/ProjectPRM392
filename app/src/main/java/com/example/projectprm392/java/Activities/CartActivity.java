package com.example.projectprm392.java.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Adap.CartAdapter;
import com.example.projectprm392.java.Entities.CartManager;
import com.example.projectprm392.java.Entities.Order;
import com.example.projectprm392.java.Entities.OrderDetail;
import com.example.projectprm392.java.Entities.Product;
import com.example.projectprm392.java.Interfaces.InterfaceOrder;
import com.example.projectprm392.java.Response.OrderResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {
    RecyclerView lv;
    CartAdapter adapter;
    TextView tvTotalPrice;
    Button btnPay, btnHome;
    List<Product> cartItem;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lv = findViewById(R.id.recyclerView_cart);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnPay = findViewById(R.id.btn_pay);
        btnHome = findViewById(R.id.btn_home);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);

        // Nếu user chưa đăng nhập, xử lý lỗi
        if (userID == -1) {
            Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng CartActivity nếu chưa đăng nhập
            return;
        }


        CartManager cartManager = CartManager.getInstance(this, userID);
        cartItem = cartManager.getCartItems();

        adapter = new CartAdapter(this, cartItem, tvTotalPrice);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(adapter);

        // Cập nhật tổng tiền khi tạo activity
        updateTotalPrice();

        // Khi nhấn nút Thanh toán
        btnPay.setOnClickListener(v -> {
            if (cartItem == null || cartItem.isEmpty()) {
                Toast.makeText(this, "Cart is empty! Please select a product.", Toast.LENGTH_SHORT).show();
            } else {
                placeOrder(cartItem);
            }
        });


        // Khi nhấn nút Home
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, HomePage.class);
            startActivity(intent);
        });
    }

    // Hàm tính tổng tiền
    private double calculateTotal() {
        double total = 0;
        for (Product p : cartItem) {
            total += p.getPrice() * p.getStockQuantity();
        }
        return total;
    }

    // Hàm cập nhật tổng tiền hiển thị
    private void updateTotalPrice() {
        double total = calculateTotal();
        tvTotalPrice.setText(String.format(Locale.getDefault(), "Total: $%.2f", total));
    }

    public void placeOrder(List<Product> cartItems) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6/0api8/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceOrder interfaceOrder = retrofit.create(InterfaceOrder.class);
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userID  = sharedPreferences.getInt("userID", -1);

        if (userID == -1) {
            Toast.makeText(CartActivity.this, "Lỗi: Không tìm thấy UserID", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalAmount = calculateTotal(); // Tính tổng tiền đơn hàng

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Product product : cartItems) {
            orderDetails.add(new OrderDetail(product.getProductID(), product.getStockQuantity(), (double) product.getPrice()));
        }

        Order order = new Order(userID, totalAmount, orderDetails);

        // ✅ Thêm log để kiểm tra dữ liệu gửi đi
//        Gson gson = new Gson();
//        String json = gson.toJson(order);
//        Log.d("OrderJSON", json);  // Xem log trong Logcat với tag "OrderJSON"

        Call<OrderResponse> call = interfaceOrder.createOrder(order);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                    int orderID = response.body().getOrderID(); // ✅ Lấy OrderID từ API trả về

                    Toast.makeText(CartActivity.this, "Order successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CartActivity.this, MyOrderActivity.class);
                    intent.putExtra("orderID", orderID); // ✅ Gửi OrderID đúng
                    startActivity(intent);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("OrderError", "Lỗi đặt hàng: " + errorBody);
                        Toast.makeText(CartActivity.this, "Lỗi đặt hàng: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("OrderFailure", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }

}
