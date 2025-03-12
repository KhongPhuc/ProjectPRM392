package com.example.projectprm392.java.Interfaces;

import com.example.projectprm392.java.Response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceUser {
    @FormUrlEncoded
    @POST("register.php")
    Call<UserResponse> register(
            @Field("Username") String username,
            @Field("Password") String password,
            @Field("FullName") String name,
            @Field("Email") String email,
            @Field("Phone") String phone,
            @Field("Address") String address
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<UserResponse> updateUser(
            @Field("Username") String username,
            @Field("FullName") String name,
            @Field("Email") String email,
            @Field("Phone") String phone,
            @Field("Address") String address
    );

    @FormUrlEncoded
    @POST("updatepass.php")
    Call<UserResponse> updatePass(
            @Field("Username") String username,
            @Field("OldPassword") String oldPassword,
            @Field("NewPassword") String newPassword
    );




}
