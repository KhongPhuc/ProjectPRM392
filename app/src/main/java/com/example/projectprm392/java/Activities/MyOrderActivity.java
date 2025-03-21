package com.example.projectprm392.java.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Adap.NotificationHelper;
import com.example.projectprm392.java.Entities.CartManager;
import com.example.projectprm392.java.Entities.Product;
import com.example.projectprm392.java.Interfaces.InterfaceOrder;
import com.example.projectprm392.java.Interfaces.InterfaceProduct;
import com.example.projectprm392.java.Response.OrderResponse;
import com.example.projectprm392.java.Response.ProductResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.os.Handler;
import android.os.Looper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyOrderActivity extends AppCompatActivity {
    private TextView statusOrder;
    private Button btnHome;
    private final Handler handler = new Handler();
    private Runnable statusChecker;
    private int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        // Tạo kênh thông báo
        NotificationHelper.createNotificationChannel(this);

        // Xin quyền thông báo nếu cần
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }


        statusOrder = findViewById(R.id.tvOrderStatus);
        btnHome = findViewById(R.id.btnHome);
        orderID = getIntent().getIntExtra("orderID", -1);

        btnHome.setOnClickListener(v->{
            Intent intent = new Intent(MyOrderActivity.this, HomePage.class);
            startActivity(intent);
        });

        if (orderID != -1) {
            startCheckingStatus();
        } else {
            statusOrder.setText("Order ID không hợp lệ");
        }
    }

    private void startCheckingStatus() {
        statusChecker = new Runnable() {
            @Override
            public void run() {
                selectStatus();
                handler.postDelayed(this, 3000); // Kiểm tra mỗi 1 giây
            }
        };
        handler.post(statusChecker);
    }

    private void selectStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);
        Log.d("DEBUG_CALL", "Gọi API với orderID: " + orderID);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4/0api8/") // Kiểm tra lại base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceOrder interfaceOrder = retrofit.create(InterfaceOrder.class);
        Call<OrderResponse> call = interfaceOrder.getStatusOrder(String.valueOf(orderID));

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = new Gson().toJson(response.body());
                    Log.d("DEBUG_JSON", "JSON từ API: " + jsonResponse);
                    Log.d("DEBUG_RESPONSE", "Status: " + response.body().getStatus());

                    statusOrder.setText("Your Order status is: " + response.body().getStatus());

                    if ("Approve".equalsIgnoreCase(response.body().getStatus())) {

                        // Hiển thị thông báo
                        NotificationHelper.sendNotification(MyOrderActivity.this,
                                "Order approved",
                                "Order #" + orderID + " was approved!");

                        handler.removeCallbacks(statusChecker);
                        CartManager cartManager = CartManager.getInstance(MyOrderActivity.this, userID);
                        cartManager.clearCart();

                        // Quay về trang chủ
//                        Intent intent = new Intent(MyOrderActivity.this, HomePage.class);
//                        startActivity(intent);
//                        finish();
                    }
                } else if("Cancelled".equalsIgnoreCase(response.body().getStatus())){
                    NotificationHelper.sendNotification(MyOrderActivity.this,
                            "Order was cancelled",
                            "Order was #" + orderID + " Cancelled!");
                }
                else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("DEBUG_ERROR", "Lỗi response: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("DEBUG_FAILURE", "Lỗi kết nối API: " + t.getMessage());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(statusChecker); // Dừng kiểm tra khi thoát Activity
    }
}

