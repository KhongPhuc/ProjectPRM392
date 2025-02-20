package com.example.projectprm392.java;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems;

    CartManager(){
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance(){
        if(instance == null){
            instance = new CartManager();
        }
        return instance;
    }

    public void addProductToCart(Product product){
        cartItems.add(product);
    }
    public List<Product> getCartItems(){
        return cartItems;
    }
}
