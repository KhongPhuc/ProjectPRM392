package com.example.projectprm392.java.Response;

import com.example.projectprm392.java.Entities.Product;

public class ProductResponse {
    private Product[] products;
    private String message;

    public Product[] getProducts() {
        return products;
    }

    public String getMessage() {
        return message;
    }
}
