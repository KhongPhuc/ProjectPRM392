package com.example.projectprm392.java.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Adap.ChatAdapter;
import com.example.projectprm392.java.Entities.ChatMessage;
import com.example.projectprm392.java.Interfaces.ChatAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerChat;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private EditText etMessage, etReceiverUsername;
    private Button btnSend, btnStartChat;
    private int currentUserId, receiverId = -1;
    private ChatAPI chatAPI;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerChat = findViewById(R.id.recyclerViewChat);
        etMessage = findViewById(R.id.edtMessage);
        etReceiverUsername = findViewById(R.id.edtReceiverUsername);
        btnSend = findViewById(R.id.btnSend);
        btnStartChat = findViewById(R.id.btnStartChat);

        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatMessages, currentUserId);
        recyclerChat.setAdapter(chatAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6/0api8/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatAPI = retrofit.create(ChatAPI.class);

        // Lấy user ID từ SharedPreferences (giả sử đã đăng nhập)
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        currentUserId = sharedPreferences.getInt("userID", -1);

        if (currentUserId == -1) {
            Toast.makeText(this, "Lỗi! Chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnStartChat.setOnClickListener(v -> {
            String username = etReceiverUsername.getText().toString().trim();
            if (!username.isEmpty()) {
                fetchReceiverId(username);
            } else {
                Toast.makeText(this, "Nhập username người nhận!", Toast.LENGTH_SHORT).show();
            }
        });

        btnSend.setOnClickListener(v -> {
            if (receiverId == -1) {
                Toast.makeText(this, "Chọn người nhận trước!", Toast.LENGTH_SHORT).show();
                return;
            }
            sendMessage();
        });
    }

    private void fetchReceiverId(String username) {
        chatAPI.getUserIdByUsername(username).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("API_RESPONSE", "Code: " + response.code());
                Log.d("API_RESPONSE", "Body: " + response.body());

                if (response.isSuccessful() && response.body() != null) {
                    receiverId = response.body();
                    loadMessages();
                } else {
                    Toast.makeText(ChatActivity.this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("ChatActivity", "Lỗi lấy receiverId: " + t.getMessage());
                Toast.makeText(ChatActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMessages() {
        chatAPI.getMessages(currentUserId, receiverId).enqueue(new Callback<List<ChatMessage>>() {
            @Override
            public void onResponse(Call<List<ChatMessage>> call, Response<List<ChatMessage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatMessages.clear();
                    chatMessages.addAll(response.body());
                    chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ChatMessage>> call, Throwable t) {
                Log.e("ChatActivity", "Lỗi tải tin nhắn: " + t.getMessage());
                Toast.makeText(ChatActivity.this, "Lỗi tải tin nhắn!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String messageText = etMessage.getText().toString().trim();
        if (messageText.isEmpty()) return;

        chatAPI.sendMessage(currentUserId, receiverId, messageText).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    etMessage.setText("");
                    loadMessages(); // Load lại tin nhắn sau khi gửi
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ChatActivity", "Lỗi gửi tin nhắn: " + t.getMessage());
                Toast.makeText(ChatActivity.this, "Gửi tin nhắn thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
