package com.example.projectprm392.java;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.R;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    ListView lv;
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        lv = findViewById(R.id.listview_cart);

        CartManager cartManager = CartManager.getInstance();
        List<Product> cartItem = cartManager.getCartItems();
        adapter = new CartAdapter(this, cartItem);
        lv.setAdapter(adapter);


    }
}