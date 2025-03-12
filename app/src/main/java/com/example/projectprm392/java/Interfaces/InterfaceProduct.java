package com.example.projectprm392.java.Interfaces;

import com.example.projectprm392.java.Response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InterfaceProduct {

    @GET("allproduct.php")
    Call<ProductResponse> getAllProduct();

    @FormUrlEncoded
    @POST("searchproduct.php")
    Call<ProductResponse> searchProduct(
            @Field("query") String query
    );

    @FormUrlEncoded
    @POST("searchprobycate.php")
    Call<ProductResponse> searchprobycate(
            @Field("CategoryID") String CategoryID
    );



}
