package com.example.projectprm392.java.Interfaces;

import com.example.projectprm392.java.Entities.Order;
import com.example.projectprm392.java.Response.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceOrder {
    @POST("create_order.php")
    Call<OrderResponse> createOrder(@Body Order order);
}
