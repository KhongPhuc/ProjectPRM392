package com.example.projectprm392.java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392.R;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView lv;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lv = findViewById(R.id.recyclerView_cart);
        TextView tvTotalPrice = findViewById(R.id.tv_total_price);
        Button btnPay = findViewById(R.id.btn_pay);

        CartManager cartManager = CartManager.getInstance();
        List<Product> cartItem = cartManager.getCartItems();
        adapter = new CartAdapter(this, cartItem, tvTotalPrice);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(adapter);

        Button  btnHome = findViewById(R.id.btn_home);
        // Cập nhật tổng tiền
        updateTotalPrice(tvTotalPrice, cartItem);

        // Sự kiện khi nhấn nút Pay
    //        btnPay.setOnClickListener(v -> {
    //            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
    //            intent.putExtra("total_price", calculateTotal(cartItem)); // Truyền tổng tiền sang PaymentActivity
    //            startActivity(intent);
    //        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, HomePage.class);
            startActivity(intent);
        });

    }

    // Hàm tính tổng tiền
    private double
    calculateTotal(List<Product> cartItems) {
        double total = 0;
        for (Product p : cartItems) {
            total += 5 * p.getQuanlity();
        }
        return total;
    }

    // Hàm cập nhật tổng tiền hiển thị
    private void updateTotalPrice(TextView tvTotalPrice, List<Product> cartItems) {
        double total = calculateTotal(cartItems);
        tvTotalPrice.setText("Total: $" + total);
    }

}