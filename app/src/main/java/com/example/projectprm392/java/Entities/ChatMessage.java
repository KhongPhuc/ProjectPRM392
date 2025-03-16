package com.example.projectprm392.java.Entities;

import com.google.gson.annotations.SerializedName;

public class ChatMessage {
    @SerializedName("id")
    private int id;

    @SerializedName("sender_id")
    private int senderId;

    @SerializedName("receiver_id")
    private int receiverId;

    @SerializedName("message")
    private String message;

    @SerializedName("timestamp")
    private String timestamp;
    public ChatMessage(int senderId, int receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public int getId() { return id; }
    public int getSenderId() { return senderId; }
    public int getReceiverId() { return receiverId; }
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
}

