package com.example.projectprm392.java.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Entities.CartManager;
import com.example.projectprm392.java.Entities.Product;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private CartManager cartManager;
    ImageView imgView;
    TextView id, brand, price, details;

    Button btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);
        cartManager = CartManager.getInstance(this, userID);
        imgView = findViewById(R.id.detail_search_image);
//        id = findViewById(R.id.detail_styleid);
        brand = findViewById(R.id.detail_brands_filter_facet);
        price = findViewById(R.id.detail_price);
        details = findViewById(R.id.detail_product_additional_info);
        btnAddToCart = findViewById(R.id.detail_btnAddToCart);

        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("Product");

        if(product != null){
            Picasso.get().load(product.getImageURL()).into(imgView);
//            id.setText(String.valueOf(product.getProductID()));
            brand.setText("Name: " + product.getProductName());
            price.setText("Price: " +String.valueOf(product.getPrice()) +"$");
            details.setText("DESCRIPTION: " +product.getDescription());
        }
        btnAddToCart.setOnClickListener(v -> {
            if (product != null) { // Dùng biến product thay vì lấy lại từ intent
                cartManager.addProductToCart(product); // Thêm vào giỏ hàng

                // Mở giỏ hàng (không cần tạo Intent mới từ getIntent)
                Intent cartIntent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(cartIntent);
            }
        });




    }
}