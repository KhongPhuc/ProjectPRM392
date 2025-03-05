package com.example.projectprm392.java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin, btnSignUp ;

    TextView loi;
    User user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnRegister);

        btnSignUp.setOnClickListener(v->{
            Intent i = new Intent(LoginActivity.this, SignUp.class);
            startActivity(i);
        });

        btnLogin.setOnClickListener(v->{
            findUser();
        });


    }
    public void findUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.33.43.165/0api8/login.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);
        Call<UserResponse> call = interfaceUser.login(username.getText().toString(), password.getText().toString());

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    user = userResponse.getUser();

                    if (user != null) {
                        // Đăng nhập thành công, chuyển sang HomePage
                        Intent intent = new Intent(LoginActivity.this, HomePage.class);
//                        intent.putExtra("USERNAME", user.getUsername()); // Gửi dữ liệu nếu cần
                        startActivity(intent);
                        finish(); // Đóng MainActivity để không quay lại màn hình login khi nhấn back
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this,  t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}