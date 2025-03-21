package com.example.projectprm392.java.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Interfaces.InterfaceUser;
import com.example.projectprm392.java.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePassActivity extends AppCompatActivity {
    EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "");

        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(v->{
            if(validateInput()){
                updatePass(userName);
            }
        });

    }


    public void updatePass(String username){
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        String oldPass = edtOldPassword.getText().toString().trim();
        String newPass = edtNewPassword.getText().toString().trim();
        String confirmPass = edtConfirmPassword.getText().toString().trim();

        // Kiểm tra mật khẩu mới có trùng nhau không
        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "New password does not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4/0api8/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);
        Call<UserResponse> call = interfaceUser.updatePass(username, oldPass, newPass);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if ("Đổi mật khẩu thành công".equals(userResponse.getMessage())) { // Kiểm tra phản hồi từ API
                        Toast.makeText(ChangePassActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePassActivity.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ChangePassActivity.this, "Old password is incorrect!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePassActivity.this, "Lỗi từ server!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(ChangePassActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateInput() {
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        String password1 = edtNewPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();


        if (password1.length() < 8 || !password1.matches(".*[A-Z].*") || !password1.matches(".*\\d.*") || !password1.matches(".*[!@#$%^&*+=?-].*")) {
            edtNewPassword.setError("Password must be at least 8 characters, including uppercase letters, numbers and special characters");
            return false;
        }

        if (!password1.equals(confirmPassword)) {
            edtConfirmPassword.setError("Confirmation password does not match");
            return false;
        }

        return true;
    }
}