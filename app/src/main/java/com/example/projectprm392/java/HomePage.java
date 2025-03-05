package com.example.projectprm392.java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    RecyclerView lv ;

    ImageButton btnManageAccount,btnCart ;
    Button btnInsert, btnUpdate, btnDelete, btnSelect;
    EditText txtMa, txtTen, txtSl;
    Context context = this;
    HomeAdapter adapter;
    List<Product> list = new ArrayList<>();
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
        lv=findViewById(R.id.home_rv);
        adapter = new HomeAdapter(context,list);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(adapter);
        new FetchProductTask().execute();

        btnManageAccount = findViewById(R.id.btnManageAccount);
        btnCart = findViewById(R.id.btnCart);

        btnManageAccount.setOnClickListener(v->{
            Intent i = new Intent(HomePage.this, MyAccountActivity.class);
            startActivity(i);
        });

        btnCart.setOnClickListener(v->{
            Intent i = new Intent(HomePage.this, CartActivity.class);
            startActivity(i);
        });


    }

    @SuppressLint("StaticFieldLeak")
    private class FetchProductTask extends AsyncTask<Void,Void,String>{
        //doc du lieu tu server
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder response = new StringBuilder();
            try {
                //duong dan doc du lieu
                URL url = new URL("https://khongphuc.github.io/ProjectPRM392/product.json");
                //ket noi
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                //tao buffer
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //doc theo tung dong du lieu
                String line = "";
                while ((line = reader.readLine())!=null){
                    response.append(line);
                }
                reader.close();

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return response.toString();
        }
        //tra ket qua ve cho client
        @Override
        protected void onPostExecute(String result) {
            if(result != null && !result.isEmpty()){
                try {
                    JSONObject json = new JSONObject(result);
                    JSONArray productArray = json.getJSONArray("products");

                    for(int i=0; i < productArray.length(); i++){
                        JSONObject prdObject = productArray.getJSONObject(i);
                        String styleid = prdObject.getString("styleid");
                        String brands_filter_facet = prdObject.getString("brands_filter_facet");
                        String price = prdObject.getString("price");
                        String search_img = prdObject.getString("search_image");
                        String product_additional_info = prdObject.getString("product_additional_info");
                        Product product = new Product(styleid,brands_filter_facet,product_additional_info,4,search_img);
                        list.add(product);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Loi doc du lieu",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}