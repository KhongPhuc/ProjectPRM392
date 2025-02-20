package com.example.projectprm392.java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.R;
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
        cartManager = CartManager.getInstance();
        imgView = findViewById(R.id.detail_search_image);
        id = findViewById(R.id.detail_styleid);
        brand = findViewById(R.id.detail_brands_filter_facet);
        price = findViewById(R.id.detail_price);
        details = findViewById(R.id.detail_product_additional_info);
        btnAddToCart = findViewById(R.id.detail_btnAddToCart);

        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("Product");

        if(product != null){
            Picasso.get().load(product.getSearch_image()).into(imgView);
            id.setText(product.getId());
            brand.setText(product.getName());
            price.setText("500$");
            details.setText(product.getDetails());
        }
        btnAddToCart.setOnClickListener(v->{
            Intent intent1 = getIntent();
            Product product1 = intent1.getParcelableExtra("Product");
            if(product1!=null){
                //add product to cart
                cartManager.addProductToCart(product1);
                //open new activity
                Intent cartIntent = new Intent(this,CartActivity.class);
                startActivity(cartIntent);
            }
        });



    }
}