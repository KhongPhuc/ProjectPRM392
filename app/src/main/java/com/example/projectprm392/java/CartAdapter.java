package com.example.projectprm392.java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectprm392.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Product> {

    private Context context;

    public CartAdapter(Context context, List<Product> products){
        super(context,0, products);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CartManager cartManager = CartManager.getInstance();
        List<Product> products = cartManager.getCartItems();
       View listItem = convertView;
        if(listItem==null){
            listItem= LayoutInflater.from(context).inflate(R.layout.cartitemview,parent,false);
        }
        //get current product
        Product currentProduct = getItem(position);
        //display info of product in cart
        ImageView img = listItem.findViewById(R.id.cartitem_img);
        Picasso.get().load(currentProduct.getSearch_image()).into(img);
        TextView productBrand = listItem.findViewById(R.id.cartitem_tv_name);
        productBrand.setText(currentProduct.getName());
        TextView productId = listItem.findViewById(R.id.cartitem_tv_price);
        productId.setText("55$");


        // Xử lý tăng giảm số lượng
        Button btnIncrease = listItem.findViewById(R.id.btn_increase);
        Button btnDecrease = listItem.findViewById(R.id.btn_decrease);
        TextView tvQuantity = listItem.findViewById(R.id.tv_quantity);
        ImageButton btnDelete = listItem.findViewById(R.id.btn_remove);
        // Gán giá trị số lượng hiện tại
        tvQuantity.setText(String.valueOf(currentProduct.getQuanlity()));

        // Sự kiện tăng số lượng
        btnIncrease.setOnClickListener(v -> {
            currentProduct.setQuanlity(currentProduct.getQuanlity()+1);
            tvQuantity.setText(String.valueOf(currentProduct.getQuanlity()));
        });

        // Sự kiện giảm số lượng
        btnDecrease.setOnClickListener(v -> {
            if (currentProduct.getQuanlity() > 1) {
                currentProduct.setQuanlity(currentProduct.getQuanlity() - 1);
                tvQuantity.setText(String.valueOf(currentProduct.getQuanlity()));
            }
        });

        btnDelete.setOnClickListener(v->{
            products.remove(currentProduct);
            notifyDataSetChanged();
        });

        return listItem;
    }
}
