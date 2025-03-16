package com.example.projectprm392.java.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Entities.User;
import com.example.projectprm392.java.Interfaces.InterfaceUser;
import com.example.projectprm392.java.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin, btnSignUp ;
    CheckBox chkRememberMe;
    TextView loi;
    User user;

    SharedPreferences sharedPreferences = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        chkRememberMe = findViewById(R.id.chkRememberMe);
        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnRegister);
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        loadRememberedUser();

        btnSignUp.setOnClickListener(v->{
            Intent i = new Intent(LoginActivity.this, SignUp.class);
            startActivity(i);
        });

        checkUserSession();
        btnLogin.setOnClickListener(v->{
            findUser();
        });



    }
    public void findUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6/0api8/login.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceUser interfaceUser = retrofit.create(InterfaceUser.class);
        Call<UserResponse> call = interfaceUser.login(username.getText().toString(), password.getText().toString());

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    User user = userResponse.getUser();

                    if (user != null) {
                        // Đăng nhập thành công, chuyển sang HomePage
                        saveUserSession(user);
                        if (chkRememberMe.isChecked()) {
                            saveRememberMe(username.getText().toString(), password.getText().toString());
                        } else {
                            clearRememberMe(); // Nếu không chọn Remember Me, xóa dữ liệu đã lưu
                        }
                        Intent intent = new Intent(LoginActivity.this, HomePage.class);
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

    private void saveUserSession(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Log.d("UserSession", "UserID: " + user.getUserID());
        Log.d("UserSession", "Username: " + user.getUsername()); // Kiểm tra dữ liệu trước khi lưu

        editor.putInt("userID", user.getUserID());
        editor.putString("username", user.getUsername());
        editor.putString("fullname", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putString("address", user.getAddress());
        editor.apply();
    }

    // Kiểm tra xem đã đăng nhập chưa
    private void checkUserSession() {
        boolean isRemembered = sharedPreferences.getBoolean("isRemembered", false);
        int userID = sharedPreferences.getInt("userID", -1);

        Log.d("UserSession", "UserID: " + userID + ", isRemembered: " + isRemembered);

        if (userID != -1 && isRemembered) { // Chỉ tự động đăng nhập nếu có Remember Me
            Log.d("UserSession", "User đã chọn Remember Me, chuyển sang HomePage");
            Intent intent = new Intent(LoginActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        } else {
            Log.d("UserSession", "Không có phiên Remember Me, hiển thị màn hình Login");
        }
    }


    private void saveRememberMe(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("remembered_username", username);
        editor.putString("remembered_password", password);
        editor.putBoolean("isRemembered", true);
        editor.apply();
    }

    private void loadRememberedUser() {
        boolean isRemembered = sharedPreferences.getBoolean("isRemembered", false);
        if (isRemembered) {
            username.setText(sharedPreferences.getString("remembered_username", ""));
            password.setText(sharedPreferences.getString("remembered_password", ""));
            chkRememberMe.setChecked(true);
        }
    }

    private void clearRememberMe() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("remembered_username");
        editor.remove("remembered_password");
        editor.remove("isRemembered");
        editor.apply();
    }


}