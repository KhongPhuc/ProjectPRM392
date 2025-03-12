package com.example.projectprm392.java.Entities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems;
    private SharedPreferences sharedPreferences;
    private int userID;
    private Context context;

    private CartManager(Context context, int userID) {
        this.context = context;
        this.userID = userID;
        this.sharedPreferences = context.getSharedPreferences("CartData", Context.MODE_PRIVATE);
        this.cartItems = loadCart();
    }

    public static synchronized CartManager getInstance(Context context, int userID) {
        if (instance == null || instance.userID != userID) {
            instance = new CartManager(context, userID);
        }
        return instance;
    }

    public void addProductToCart(Product product) {
        boolean exists = false;
        for (Product p : cartItems) {
            if (p.getProductID() == product.getProductID()) {
                p.setStockQuantity(p.getStockQuantity() + 1);
                exists = true;
                break;
            }
        }
        if (!exists) {
            product.setStockQuantity(1);
            cartItems.add(product);
        }
        saveCart(); // Lưu lại giỏ hàng
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
        saveCart();
    }

    private void saveCart() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString("cart_" + userID, json); // Lưu theo userID
        editor.apply();
    }

    private List<Product> loadCart() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cart_" + userID, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return (json != null) ? gson.fromJson(json, type) : new ArrayList<>();
    }
}
