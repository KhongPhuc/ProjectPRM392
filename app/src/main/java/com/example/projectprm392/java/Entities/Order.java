package com.example.projectprm392.java.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("OrderID")
    private int OrderID;
    @SerializedName("UserID")
    private int userID;

    public int getOrderID() {
        return OrderID;
    }

    @SerializedName("TotalAmount")
    private double totalAmount;

    @SerializedName("orderDetails")
    private List<OrderDetail> orderDetails;

    public Order(int userID, double totalAmount, List<OrderDetail> orderDetails) {
        this.userID = userID;
        this.totalAmount = totalAmount;
        this.orderDetails = orderDetails;
    }
}




