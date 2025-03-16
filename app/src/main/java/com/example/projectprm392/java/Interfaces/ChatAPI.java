package com.example.projectprm392.java.Interfaces;

import com.example.projectprm392.java.Entities.ChatMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatAPI {
    @GET("getUserId.php")
    Call<Integer> getUserIdByUsername(@Query("username") String username);

    @GET("getMessages.php")
    Call<List<ChatMessage>> getMessages(@Query("sender_id") int senderId, @Query("receiver_id") int receiverId);

    @FormUrlEncoded
    @POST("sendMessage.php")
    Call<Void> sendMessage(
            @Field("sender_id") int senderId,
            @Field("receiver_id") int receiverId,
            @Field("message") String message
    );
}
