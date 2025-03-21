package com.example.projectprm392.java.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Adap.HomeAdapter;
import com.example.projectprm392.java.Entities.Product;
import com.example.projectprm392.java.Interfaces.InterfaceProduct;
import com.example.projectprm392.java.Response.ProductResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends AppCompatActivity {
    RecyclerView lv ;

    ImageButton btnManageAccount,btnCart, btnSony, btnSamsung, btnXiaomi, btnToshiba, btnSearch, btnchat, btnMap ;
    EditText txtMa, txtTen, txtSl, search;
    Context context = this;
    HomeAdapter adapter;
    List<Product> list = new ArrayList<>() ;

    String url = "http://192.168.1.4/0api8/";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        lv = findViewById(R.id.home_rv);
        adapter = new HomeAdapter(context, list);
        if (list != null) {
            Log.d("CHECK_LIST", "Số lượng sản phẩm trong list: " + list.size());
            for (Product p : list) {
                Log.d("CHECK_LIST", "Sản phẩm: ID=" + p.getProductID() + ", Tên=" + p.getProductName());
            }
        } else {
            Log.e("CHECK_LIST", "List sản phẩm đang null!");
        }

        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(adapter);
        selectAllProduct();


        btnManageAccount = findViewById(R.id.btnManageAccount);
        btnCart = findViewById(R.id.btnCart);
        btnSony = findViewById(R.id.btnSony);
        btnSamsung = findViewById(R.id.btnSamsung);
        btnXiaomi = findViewById(R.id.btnXiaomi);
        btnToshiba = findViewById(R.id.btnToshiba);
        btnSearch = findViewById(R.id.btnSearch);
        search = findViewById(R.id.etSearch);
        btnchat = findViewById(R.id.btnChat);
        btnMap = findViewById(R.id.btnMap);

        btnchat.setOnClickListener(v ->{
            Intent intent = new Intent(HomePage.this, ChatActivity.class);
            startActivity(intent);
        });

        btnSearch.setOnClickListener(v->{
            String result = search.getText().toString();
            lv = findViewById(R.id.home_rv);
            adapter = new HomeAdapter(context, list);
            if (list != null) {
                Log.d("CHECK_LIST1", "Số lượng sản phẩm trong list: " + list.size());
                for (Product p : list) {
                    Log.d("CHECK_LIST1", "Sản phẩm: ID=" + p.getProductID() + ", Tên=" + p.getProductName());
                }
            } else {
                Log.e("CHECK_LIST1", "List sản phẩm đang null!");
            }

            lv.setLayoutManager(new LinearLayoutManager(this));
            lv.setAdapter(adapter);
            searchProduct(result);

        });

        btnSony.setOnClickListener(v->{
            lv = findViewById(R.id.home_rv);
            adapter = new HomeAdapter(context, list);
            if (list != null) {
                Log.d("CHECK_LIST1", "Số lượng sản phẩm trong list: " + list.size());
                for (Product p : list) {
                    Log.d("CHECK_LIST1", "Sản phẩm: ID=" + p.getProductID() + ", Tên=" + p.getProductName());
                }
            } else {
                Log.e("CHECK_LIST1", "List sản phẩm đang null!");
            }

            lv.setLayoutManager(new LinearLayoutManager(this));
            lv.setAdapter(adapter);
            getProByCate("1");

        });

        btnSamsung.setOnClickListener(v->{
            lv = findViewById(R.id.home_rv);
            adapter = new HomeAdapter(context, list);
            if (list != null) {
                Log.d("CHECK_LIST1", "Số lượng sản phẩm trong list: " + list.size());
                for (Product p : list) {
                    Log.d("CHECK_LIST1", "Sản phẩm: ID=" + p.getProductID() + ", Tên=" + p.getProductName());
                }
            } else {
                Log.e("CHECK_LIST1", "List sản phẩm đang null!");
            }

            lv.setLayoutManager(new LinearLayoutManager(this));
            lv.setAdapter(adapter);
            getProByCate("2");

        });

        btnXiaomi.setOnClickListener(v->{
            lv = findViewById(R.id.home_rv);
            adapter = new HomeAdapter(context, list);
            if (list != null) {
                Log.d("CHECK_LIST1", "Số lượng sản phẩm trong list: " + list.size());
                for (Product p : list) {
                    Log.d("CHECK_LIST1", "Sản phẩm: ID=" + p.getProductID() + ", Tên=" + p.getProductName());
                }
            } else {
                Log.e("CHECK_LIST1", "List sản phẩm đang null!");
            }

            lv.setLayoutManager(new LinearLayoutManager(this));
            lv.setAdapter(adapter);
            getProByCate("3");

        });

        btnToshiba.setOnClickListener(v->{
            lv = findViewById(R.id.home_rv);
            adapter = new HomeAdapter(context, list);
            if (list != null) {
                Log.d("CHECK_LIST1", "Số lượng sản phẩm trong list: " + list.size());
                for (Product p : list) {
                    Log.d("CHECK_LIST1", "Sản phẩm: ID=" + p.getProductID() + ", Tên=" + p.getProductName());
                }
            } else {
                Log.e("CHECK_LIST1", "List sản phẩm đang null!");
            }

            lv.setLayoutManager(new LinearLayoutManager(this));
            lv.setAdapter(adapter);
            getProByCate("4");
        });


        btnManageAccount.setOnClickListener(v->{
            Intent i = new Intent(HomePage.this, MyAccountActivity.class);
            startActivity(i);
        });

        btnCart.setOnClickListener(v->{
            Intent i = new Intent(HomePage.this, CartActivity.class);
            startActivity(i);
        });

        btnMap.setOnClickListener(v->{
            Intent i = new Intent(HomePage.this, GoogleMapActivity.class);
            startActivity(i);
        });

    }

    public void selectAllProduct(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceProduct interfaceProduct = retrofit.create(InterfaceProduct.class);
        Call<ProductResponse> call = interfaceProduct.getAllProduct();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = new ArrayList<>(Arrays.asList(response.body().getProducts()));

                    Log.d("API_RESPONSE", "Số lượng sản phẩm: " + products.size());

                    // In từng sản phẩm ra Logcat
                    for (Product product : products) {
                        Log.d("PRODUCT_INFO", "ID: " + product.getProductID() +
                                ", Tên: " + product.getProductName() +
                                ", Giá: " + product.getPrice() +
                                ", Ảnh: " + product.getImageURL());
                    }

                    updateUI(products);

                } else {
                    Log.e("API_RESPONSE", "Lỗi response: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void updateUI(List<Product> products) {
        list.clear();
        list.addAll(products);
        adapter.notifyDataSetChanged();
    }

    public void searchProduct(String query){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceProduct interfaceProduct = retrofit.create(InterfaceProduct.class);
        Call<ProductResponse> call = interfaceProduct.searchProduct(query);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = new ArrayList<>(Arrays.asList(response.body().getProducts()));

                    Log.d("API_RESPONSE", "Số lượng sản phẩm: " + products.size());

                    // In từng sản phẩm ra Logcat
                    for (Product product : products) {
                        Log.d("PRODUCT_INFO", "ID: " + product.getProductID() +
                                ", Tên: " + product.getProductName() +
                                ", Giá: " + product.getPrice() +
                                ", Ảnh: " + product.getImageURL());
                    }

                    updateUI(products);

                } else {
                    Log.e("API_RESPONSE", "Lỗi response: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    public void getProByCate(String query){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceProduct interfaceProduct = retrofit.create(InterfaceProduct.class);
        Call<ProductResponse> call = interfaceProduct.searchprobycate(query);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = new ArrayList<>(Arrays.asList(response.body().getProducts()));

                    Log.d("API_RESPONSE", "Số lượng sản phẩm: " + products.size());

                    // In từng sản phẩm ra Logcat
                    for (Product product : products) {
                        Log.d("PRODUCT_INFO", "ID: " + product.getProductID() +
                                ", Tên: " + product.getProductName() +
                                ", Giá: " + product.getPrice() +
                                ", Ảnh: " + product.getImageURL());
                    }

                    updateUI(products);

                } else {
                    Log.e("API_RESPONSE", "Lỗi response: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }


}