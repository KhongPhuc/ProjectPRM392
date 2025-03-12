package com.example.projectprm392.java.Entities;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("ProductID")
    private int productID;

    @SerializedName("Quantity")
    private int quantity;

    @SerializedName("Price")
    private double price;

    public OrderDetail(int productID, int quantity, double price) {
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }
}



