package com.example.projectprm392.java;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceUser {
    @FormUrlEncoded
    @POST("register.php")
    Call<UserResponse> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );



}
