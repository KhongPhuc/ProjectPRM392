package com.example.projectprm392.java.Interfaces;

import com.example.projectprm392.java.Entities.Order;
import com.example.projectprm392.java.Response.OrderResponse;
import com.example.projectprm392.java.Response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceOrder {
    @POST("create_order.php")
    Call<OrderResponse> createOrder(@Body Order order);

    @GET("getstatusbyorderID.php")
    Call<OrderResponse> getStatusOrder(@Query("orderID") String orderID);




}
